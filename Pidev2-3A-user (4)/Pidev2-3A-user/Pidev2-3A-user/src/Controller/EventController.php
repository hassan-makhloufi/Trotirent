<?php

namespace App\Controller;
use App\Repository\EventRepository;
use App\Repository\CategorieeventRepository;
use App\Entity\Event;
use App\Entity\Categorieevent;
use App\Form\EventType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use TCPDF;



#[Route('/event')]
class EventController extends AbstractController
{

/**
     * @Route("/cal/cale", name="calendar_index", methods={"GET"})
     */
    public function calander(EntityManagerInterface $entityManager)
    {
    
     //   $entityManager = $this->getDoctrine()->getManager();
    // $userId = 1;
    // $events = $entityManager->createQuery(
    //     'SELECT r, e
    //     FROM App\Entity\Reservation r
    //     JOIN r.idEvent e
    //     WHERE r.idUser1 = :userId'
    // )->setParameter('userId', $userId)
    // ->getResult();
    $events = $entityManager->getRepository(Event::class)->findAll();

        $rdvs = [];

        foreach($events as $event){
            $rdvs[] = [
                'id' => $event->getIdevent(),
                'start' => $event->getDatedebut()->format('Y-m-d'),
                'end' => $event->getDatefin()->format('Y-m-d '),
                'title' => $event->getNomevent(),
                'description' => $event->getDescription(),
                // 'backgroundColor' => $event->getRandom()
            ];
        }

        $data = json_encode($rdvs);

        return $this->render('calendrier.html.twig', compact('data'));
    }


    #[Route('/', name: 'app_event_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager, PaginatorInterface $paginator, Request $request): Response
    {
        $queryBuilder = $entityManager->createQueryBuilder()
        ->select('e')
        ->from(Event::class, 'e');
        $categorieevents = $entityManager
            ->getRepository(Categorieevent::class)
            ->findAll();
        
// Sorting
$sort = $request->query->get('sort');
if ($sort) {
    $queryBuilder->orderBy('e.' . $sort, 'ASC');
}

        $events = $entityManager->getRepository(Event::class)->findAll();
        $pagination = $paginator->paginate(
            $events,
            $request->query->getInt('page', 1), 3

        );
        
        return $this->render('event/index.html.twig', [
            'pagination' => $pagination,
            'categorieevents' => $categorieevents,
        ]);
        
    
    }
  
     #[Route('/search', name :'event_search')]
    public function search(EntityManagerInterface $entityManager,Request $request, EventRepository $eventRepository, PaginatorInterface $paginator)
    {
        $searchTerm = $request->query->get('search'); // récupérer le paramètre de recherche depuis la requête
        $results = $eventRepository->advancedSearch(null, $searchTerm, null); // effectuer la recherche
        $categorieevents = $entityManager
        ->getRepository(Categorieevent::class)
        ->findAll();
    
        $pagination = $paginator->paginate(
            $results,
            $request->query->getInt('page', 1), 3);

        return $this->render('event/index.html.twig', [
            'pagination' => $pagination,
            'categorieevents' => $categorieevents,
        ]);
    }
    #[Route('/category/{id}', name: 'event_category')]
public function category(EntityManagerInterface $entityManager,EventRepository $eventRepository, CategorieeventRepository $categorieeventRepository, $id, PaginatorInterface $paginator, Request $request)
{
   
    $categorieevent = $categorieeventRepository->find($id);
    $events = $eventRepository->findByCategoryId($categorieevent->getIdcat());

    $categorieevents = $entityManager
    ->getRepository(Categorieevent::class)
    ->findAll();

    $pagination = $paginator->paginate(
        $events,
        $request->query->getInt('page', 1), 3);

    return $this->render('event/index.html.twig', [
         'pagination' => $pagination,
         'categorieevents' => $categorieevents,
    ]);
}




    #[Route('/map', name: 'street', methods: ['GET'])]
    public function mapAction(): Response
    {
        
        return $this->render('event/map.html.twig');
    }

    #[Route('/home', name: 'app_event_home', methods: ['GET'])]
    public function indexHome(EntityManagerInterface $entityManager): Response
    {
        $events = $entityManager
            ->getRepository(Event::class)
            ->findAll();

        return $this->render('event/Home.html.twig', [
            'events' => $events,
        ]);
    }


    #[Route('/backEvent', name: 'app_event_back', methods: ['GET'])]
    public function EventB(EntityManagerInterface $entityManager): Response
    {
        $events = $entityManager
            ->getRepository(Event::class)
            ->findAll();

        return $this->render('event/BackEvent.html.twig', [
            'events' => $events,
        ]);
    }

    #[Route('/new', name: 'app_event_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $event = new Event();
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

           
             // handle image upload
        $photoe = $form->get('photoe')->getData();
        if ($photoe) {
            $originalFilename = pathinfo($photoe->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = $originalFilename.'.'.$photoe->guessExtension();
        
            $photoe->move(
                $this->getParameter('event_images_directory'),
                $newFilename
            );
        
            $event->setPhotoe($newFilename);


             // Ajouter la localisation à l'objet Event
            $localisation = $form->get('localisation')->getData();
         if ($localisation) {
             $event->setLocalisation($localisation);
         }
        }
        
         

            $entityManager->persist($event);
            $entityManager->flush();

            return $this->redirectToRoute('app_event_back', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event/new.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }

    #[Route('/{idevent}', name: 'app_event_show', methods: ['GET'])]
    public function show(Event $event): Response
    {
        return $this->render('event/show.html.twig', [
            'event' => $event,
        ]);
    }
    

    #[Route('/{idevent}/edit', name: 'app_event_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Event $event, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $uploadedFile = $form['photoe']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads/events';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFile = $originalFilename.'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFile
               );
               $event->setPhotoe($newFile);


             // Ajouter la localisation à l'objet Event
            $localisation = $form->get('localisation')->getData();
         if ($localisation) {
             $event->setLocalisation($localisation);
         }


               $em=$this->getDoctrine()->getManager();
               
            $em->persist($event);
            $em->flush();
            return $this->redirectToRoute('app_event_back', [], Response::HTTP_SEE_OTHER);
            }
        return $this->renderForm('event/edit.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }
    
    #[Route('/{idevent}', name: 'app_event_delete', methods: ['POST'])]
    public function delete(Request $request, Event $event, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getIdevent(), $request->request->get('_token'))) {
            $entityManager->remove($event);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_event_back', [], Response::HTTP_SEE_OTHER);
    }

    
    #[Route('/{idevent}/Contrat', name: 'generateEvent_pdf')]
    public function generatePdf($idevent) {
     // Récupérer les détails de l'événement depuis la base de données
     $event = $this->getDoctrine()->getRepository(Event::class)->find($idevent);

     // Générer le fichier PDF du contrat
     $pdf = new TCPDF();
     $pdf->AddPage();
     $pdf->SetFont('Helvetica', 'B', 16);
     $pdf->Write(10, 'Contrat pour l\'événement ' . $event->getNomevent());
     
     
     
 // Ajouter les détails de l'événement
 $pdf->SetFont('Helvetica', '', 12);
 $pdf->Ln(60);
 $pdf->Write(5, 'Nom de l\'événement : ' . $event->getNomevent());
 $pdf->Ln();
 $pdf->Write(5, 'Date de début : ' . $event->getDatedebut()->format('d/m/Y'));
 $pdf->Ln();
 $pdf->Write(5, 'Date de fin : ' . $event->getDatefin()->format('d/m/Y'));
 $pdf->Ln();
 $pdf->Write(5, 'Localisation : ' . $event->getLocalisation());
 $pdf->Ln();
 $pdf->Write(5, 'Description : ' . $event->getDescription());
 $pdf->Ln();
 $pdf->Write(5, 'Heure de l\'événement : ' . $event->getHeureevent());
 $pdf->Ln();
 $pdf->Write(5, 'Prix : ' . $event->getPrix());
 $pdf->Ln();
 $pdf->Write(5, 'Catégorie : ' . $event->getIdcat()->getNomcat());
 

 // ... ajouter le contenu du contrat ici ...
 $imageName = $event->getPhotoe();

 // Construire le chemin complet vers l'image
 $imagePath = $this->getParameter('kernel.project_dir').'/public/uploads/events/'.$imageName;
     // Ajouter l'image au PDF
     $pdf->Image($imagePath, 10, 30, 50, 0, 'JPG');

     // Envoyer le fichier PDF au navigateur
     $response = new BinaryFileResponse($pdf->Output('contrat.pdf', 'I'));
     $response->setContentDisposition(ResponseHeaderBag::DISPOSITION_INLINE, 'contrat.pdf');
     return $response;
}
 
    
}
