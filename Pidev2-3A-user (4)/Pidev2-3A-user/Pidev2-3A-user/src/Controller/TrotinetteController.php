<?php

namespace App\Controller;

use App\Entity\Trotinette;
use App\Entity\Categorie;
use App\Entity\Panier;
use App\Form\CategorieType;
use App\Form\TrotinetteType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Snappy\Pdf;
use App\Entity\PdfGeneratorService;
use Dompdf\Dompdf;
use Dompdf\Options;
use App\Repository\TrotinetteRepository;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Knp\Component\Pager\PaginatorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;


#[Route('/trotinette')]
class TrotinetteController extends AbstractController
{
    #[Route('/', name: 'app_trotinette_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $trotinettes = $entityManager
            ->getRepository(Trotinette::class)
            ->findAll();

        return $this->render('trotinette/index.html.twig', [
            'trotinettes' => $trotinettes,
        ]);
    }
    /*#[Route('/front', name: 'app_trotinette_index_front', methods: ['GET'])]
    public function indexFront(EntityManagerInterface $entityManager): Response
    {
        $trotinettes = $entityManager
            ->getRepository(Trotinette::class)
            ->findAll();

        return $this->render('trotinette/TrotFront.html.twig', [
            'trotinettes' => $trotinettes,
        ]);
    }*/

    #[Route('/new', name: 'app_trotinette_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,FlashyNotifier $flashy): Response
    {
        $trotinette = new Trotinette();
        $form = $this->createForm(TrotinetteType::class, $trotinette);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $file = $trotinette->getImage();
            $filename = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('uploads'),$filename);
            $trotinette->setImage($filename);
            $entityManager->persist($trotinette);
            $entityManager->flush();
            $flashy->success('Addedd  succefully', 'http://your-awesome-link.com');

            return $this->redirectToRoute('app_trotinette_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('trotinette/new.html.twig', [
            'trotinette' => $trotinette,
            'form' => $form,
        ]);
    }


     /////////////////////////////////////---PDF---///////////////////////////////////////////////
     #[Route('/pdf/trotinette', name: 'generator_serviceT')]
     public function pdfService(): Response
     { 
         $trotinette= $this->getDoctrine()
         ->getRepository(Trotinette::class)
         ->findAll();
 
 
 
         $html =$this->renderView('pdf/indexTrotinette.html.twig', ['trotinettes' => $trotinette]);
         $pdfGeneratorService=new PdfGeneratorService();
         $pdf = $pdfGeneratorService->generatePdf($html);
 
         return new Response($pdf, 200, [
             'Content-Type' => 'application/pdf',
             'Content-Disposition' => 'inline; filename="document.pdf"',
         ]);
         
     }
     
     #[Route('/rechercheCB', name:'trotinette_rechercheCB')]
     public function rechercheCB(Request $request, TrotinetteRepository $repository)
     {
         $data= $request->get('search');
         $trotinettes=$repository->findBy(['marque'=>$data]);
 
 
         return $this->render('trotinette/searchBT.html.twig', [
             'trotinettes'=>$trotinettes]);
 
     }
 
     #[Route('/order_By_Prix', name:'order_By_prix' ,methods:['GET'])]
     public function order_By_Prix(Request $request,TrotinetteRepository $TrotinetteRepository): Response
     {
 
         $TrotinetteByPrix = $TrotinetteRepository->order_By_Prix();
 
         return $this->render('trotinette/index.html.twig', [
             'trotinettes' => $TrotinetteByPrix,
         ]);
        
    }

    #[Route('/statistiqueE', name: 'statsE')]
    public function stat()
        {
    
            $repository = $this->getDoctrine()->getRepository(Trotinette::class);   
            $trotinette= $repository->findAll();
    
            $em = $this->getDoctrine()->getManager();
    
    
            $ev1 = 0;
            $ev2 = 0;
            $ev3=0;
    
    
            foreach ($trotinette as $trotinette) 
            {
               if ($trotinette->getMarque() =='TWOW')  
                {
                    $ev1 += 1;
                }
                elseif ($trotinette->getMarque() =='Blasters')
                 {
                    $ev2 += 1;
                 }
                else 
                 {
                    $ev3 +=1;
                 }
               
    
            }
    
            $pieChart = new PieChart();
            $pieChart->getData()->setArrayToDataTable(
                [['Prix', 'Nom'],
                    ['TWOW ', $ev1],
                    ['Blasters', $ev2],
                    ['autres', $ev3]
                ]
            );
            $pieChart->getOptions()->setTitle('statistique a partir des Marques trotinettes');
            $pieChart->getOptions()->setHeight(1000);
            $pieChart->getOptions()->setWidth(1400);
            $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
            $pieChart->getOptions()->getTitleTextStyle()->setColor('green');
            $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
            $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
            $pieChart->getOptions()->getTitleTextStyle()->setFontSize(30);
    
           
    
            return $this->render('trotinette/stat.html.twig', array('piechart' => $pieChart));

        }


                /**
         * @Route("/add-to-cart", name="add_to_cart", methods={"POST"})
         */
        public function addToCart(Request $request, EntityManagerInterface $entityManager,FlashyNotifier $flashy)
        {
            $trotinetteId = $request->request->get('trotinette_id');
            $trotinette = $entityManager->getRepository(Trotinette::class)->find($trotinetteId);

            // Créer une nouvelle entrée Panier pour la trotinette sélectionnée
            $panier = new Panier();
            $panier->setNom($trotinette->getNom());
            $panier->setPrix($trotinette->getPrix());
            $panier->setQty(1); // une seule trotinette
            $panier->setImage($trotinette->getImage()); // set the image filename
            $panier->setIdProduit($trotinette);
            $panier->setIdUser($this->getUser()); // utilisateur courant
            $entityManager->persist($panier);
            $entityManager->flush();
            
            $flashy->success('Addedd AU Panier succefully', 'http://your-awesome-link.com');

            // Rediriger l'utilisateur vers la page du panier
            return $this->redirectToRoute('app_panier_index_front');
        }

 
    #[Route('/front/{id}', name: 'app_trotinette_index_front', methods: ['GET'])]
    public function indexFront(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request,  int $id): Response
    {
        $categorie = $entityManager->getRepository(Categorie::class)->find($id);
        if (!$categorie) {
            throw $this->createNotFoundException('La catégorie n\'existe pas.');
        }
        $trotinettesQuery = $entityManager
        ->getRepository(Trotinette::class)
        ->createQueryBuilder('t')
        ->where('t.idCategorie = :categorieId')
        ->setParameter('categorieId', $categorie->getId())
        ->getQuery();
    
    $trotinettes = $paginator->paginate(
        $trotinettesQuery,
        $request->query->getInt('page', 1),
        3 // items per page
    );
    
    return $this->render('trotinette/TrotFront.html.twig', [
        'trotinettes' => $trotinettes,
        'categorie' => $categorie,
    ]);
        }
        #[Route('/{idTrot}', name: 'app_trotinette_showF', methods: ['GET'])]
        public function showF(Trotinette $trotinette): Response
        {
            return $this->render('trotinette/showF.html.twig', [
                'trotinette' => $trotinette,
            ]);
        }
        #[Route('/{idTrot}', name: 'app_trotinette_show', methods: ['GET'])]
        public function show(Trotinette $trotinette): Response
        {
            return $this->render('trotinette/show.html.twig', [
                'trotinette' => $trotinette,
            ]);
        }
        
    #[Route('/{idTrot}/edit', name: 'app_trotinette_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Trotinette $trotinette, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TrotinetteType::class, $trotinette);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $trotinette->getImage();
            $filename = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('uploads'),$filename);
            $trotinette->setImage($filename);
            $entityManager->flush();

            return $this->redirectToRoute('app_trotinette_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('trotinette/edit.html.twig', [
            'trotinette' => $trotinette,
            'form' => $form,
        ]);
    }

    #[Route('/{idTrot}', name: 'app_trotinette_delete', methods: ['POST'])]
    public function delete(Request $request, Trotinette $trotinette, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$trotinette->getIdTrot(), $request->request->get('_token'))) {
            $entityManager->remove($trotinette);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_trotinette_index', [], Response::HTTP_SEE_OTHER);
    }
}
