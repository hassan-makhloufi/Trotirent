<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\User;
use App\Form\CommandeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Snappy\Pdf;
use App\Entity\PdfGeneratorService;
use Dompdf\Dompdf;
use Dompdf\Options;
use App\Repository\CommandeRepository;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use App\Service\MailerService;
use Symfony\Component\Mime\Email;
use Symfony\Component\Security\Core\Security;



#[Route('/commande')]
class CommandeController extends AbstractController
{ private $mailer;

    public function __construct(MailerService $mailer)
    {
        $this->mailer = $mailer;
    }
    #[Route('/', name: 'app_commande_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $commandes = $entityManager
            ->getRepository(Commande::class)
            ->findAll();

        return $this->render('commande/index.html.twig', [
            'commandes' => $commandes,
        ]);
    }
    #[Route('/newFront', name: 'app_commande_new_front', methods: ['GET', 'POST'])]
    public function newFront(Request $request, EntityManagerInterface $entityManager, MailerService $mailer, Security $security): Response
    {
        // créer une instance de votre entité Commande
        $commande = new Commande();
    
        // Récupérer l'utilisateur connecté
        $user = $this->getUser(); // récupérer l'utilisateur connecté
$commande->setIduser($user); // passer l'utilisateur connecté à setIduser()

        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Définir l'utilisateur connecté comme propriétaire de la commande
            $commande->setIduser($user);
    
            $entityManager->persist($commande);
    
            $to = $user->getEmail();
            $mailer->sendEmail($to);
    
            $entityManager->flush();
    
            return $this->redirectToRoute('app_panier_index_front', [], Response::HTTP_SEE_OTHER);
        }
            
        return $this->renderForm('commande/newFront.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }
    

    #[Route('/new', name: 'app_commande_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $commande = new Commande();
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($commande);
            $entityManager->flush();

            return $this->redirectToRoute('app_commande_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande/new.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }

    #[Route('/rechercheCB', name:'commande_rechercheCB')]
    public function rechercheCB(Request $request, CommandeRepository $repository)
    {
        $data= $request->get('search');
        $commandes=$repository->findBy(['adresse'=>$data]);


        return $this->render('commande/searchBCO.html.twig', [
            'commandes'=>$commandes]);

    }

    #[Route('/order_By_PrixC', name:'order_By_prixC' ,methods:['GET'])]
    public function order_By_PrixC(Request $request,CommandeRepository $CommandeRepository): Response
    {

        $CommandeByPrix = $CommandeRepository->order_By_PrixTotale();

        return $this->render('commande/index.html.twig', [
            'commandes' => $CommandeByPrix,
        ]);
       
   }

    /////////////////////////////////////---PDF---///////////////////////////////////////////////
    #[Route('/pdf/trotinette', name: 'generator_serviceC')]
    public function pdfService(): Response
    { 
        $commande= $this->getDoctrine()
        ->getRepository(Commande::class)
        ->findAll();



        $html =$this->renderView('pdf/indexCommande.html.twig', ['commandes' => $commande]);
        $pdfGeneratorService=new PdfGeneratorService();
        $pdf = $pdfGeneratorService->generatePdf($html);

        return new Response($pdf, 200, [
            'Content-Type' => 'application/pdf',
            'Content-Disposition' => 'inline; filename="document.pdf"',
        ]);
        
    }



    #[Route('/statistiqueC', name: 'statsC')]
    public function statC()
        {
    
            $repository = $this->getDoctrine()->getRepository(Commande::class);
            $commande= $repository->findAll();
    
            $em = $this->getDoctrine()->getManager();
    
    
            $ev1 = 0;
            $ev2 = 0;
            $ev3=0;
    
    
            foreach ($commande as $commande) 
            {
               if ($commande->getAdresse() =='tunisie')  
                {
                    $ev1 += 1;
                }
                elseif ($commande->getAdresse() =='sousse')
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
                    ['Tunisie ', $ev1],
                    ['Sousse', $ev2],
                    ['autres', $ev3]
                ]
            );
            $pieChart->getOptions()->setTitle('statistique a partir des Lieus de Commandes');
            $pieChart->getOptions()->setHeight(1000);
            $pieChart->getOptions()->setWidth(1400);
            $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
            $pieChart->getOptions()->getTitleTextStyle()->setColor('green');
            $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
            $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
            $pieChart->getOptions()->getTitleTextStyle()->setFontSize(30);
    
           
    
            return $this->render('commande/stat.html.twig', array('piechart' => $pieChart));

        }

          ///////////////////////////////////--MAP--////////////////////////////////////////////////
    #[Route('/show_in_map/{idCommande}', name: 'app_commande_map', methods: ['GET'])]
    public function Map( Commande $idCommande,EntityManagerInterface $entityManager ): Response
    {

        $commande = $entityManager
            ->getRepository(Commande::class)->findBy( 
                ['idCommande'=>$idCommande ]
            );
        return $this->render('commande/api_arcgis.html.twig', [
            'commandes' => $commande,
        ]);

    }


    #[Route('/{idCommande}', name: 'app_commande_show', methods: ['GET'])]
    public function show(Commande $commande): Response
    {
        return $this->render('commande/show.html.twig', [
            'commande' => $commande,
        ]);
    }

    #[Route('/{idCommande}/edit', name: 'app_commande_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Commande $commande, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_commande_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande/edit.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }

    #[Route('/{idCommande}', name: 'app_commande_delete', methods: ['POST'])]
    public function delete(Request $request, Commande $commande, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$commande->getIdCommande(), $request->request->get('_token'))) {
            $entityManager->remove($commande);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_commande_index', [], Response::HTTP_SEE_OTHER);
    }
}
