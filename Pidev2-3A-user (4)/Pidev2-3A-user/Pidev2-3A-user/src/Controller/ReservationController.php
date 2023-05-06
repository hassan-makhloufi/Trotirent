<?php

namespace App\Controller;
use App\Entity\Reservation;


use Symfony\Component\Security\Core\Security;

use App\Form\ReservationType;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\EventRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\User;
use App\Entity\Event;

#[Route('/reservation')]
class ReservationController extends AbstractController
{
    #[Route('/stats', name: 'app_reservation_stats', methods: ['GET'])]
    public function stats(EntityManagerInterface $entityManager): Response
{
    $results = $this->getReservationStats($entityManager);

    $eventNames = [];
    $numReservations = [];

    foreach ($results as $result) {
        $eventNames[] = $result['NomEvent'];
        $numReservations[] = $result['numReservations'];
    }

    return $this->render('reservation/stat.html.twig', [
        'eventNames' => $eventNames,
        'numReservations' => $numReservations
    ]);
}

public function getReservationStats(EntityManagerInterface $entityManager)
{
    // Créez la requête
    $requete = "SELECT e.NomEvent, COUNT(r.idRes) AS numReservations FROM reservation r " .
               "JOIN event e ON r.idEvent=e.idEvent " .
               "GROUP BY r.idEvent";
    
    // Exécutez la requête
    $resultats = $entityManager->getConnection()->query($requete)->fetchAll();
    
    // Retournez les résultats
    return $resultats;
}
     

 // Injecter le composant Security dans le constructeur
 public function __construct(Security $security)
 {
     $this->security = $security;
 }





    #[Route('/', name: 'app_reservation_back', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $reservations = $entityManager
            ->getRepository(Reservation::class)
            ->findAll();

        return $this->render('reservation/index.html.twig', [
            'reservations' => $reservations,
        ]);
    }

    #[Route('/new/{idevent}', name: 'app_reservation_new', methods: ['GET','POST'])]
    public function new($idevent,ManagerRegistry $entityManager,): Response
    {
     
        

         // Récupérer l'objet Event correspondant à l'identifiant $eventId
      $event = $entityManager->getRepository(event::class)->find($idevent);
        // Récupérer l'utilisateur connecté
        $user = $this->security->getUser();
        
        // Vérifier que l'utilisateur est connecté
        if (!$user) {
            throw $this->createAccessDeniedException('You must be logged in to access this page.');
        }
        // Vérifier que l'objet Event existe
        if (!$event) {
            throw $this->createNotFoundException('Event not found');
        }
    
        // Créer une nouvelle réservation et la remplir avec les données
        $reservation = new Reservation();
        $reservation->setIdevent($event);
        $reservation->setIduser($user);
    
        
        // Enregistrer la nouvelle réservation
        $em=$this->getDoctrine()->getManager();
        $em->persist($reservation);//mettre a jour la base
        $em->flush();//save base
        $this->get('session')->getFlashBag()->add('notice','Reservation added successfully');


        
    // Rediriger vers la même page pour éviter les re-postages de formulaire
   return $this->redirectToRoute('app_event_show', [
    'idevent' => $event->getIdevent(),
    ]);
      
    }
    
    
    #[Route('/{idres}', name: 'app_reservation_show', methods: ['GET'])]
    public function show(Reservation $reservation): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,
        ]);
    }

    #[Route('/{idres}/edit', name: 'app_reservation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);  //analyse des données

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/edit.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }

    #[Route('/{idres}', name: 'app_reservation_delete', methods: ['POST'])]
    public function delete(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservation->getIdres(), $request->request->get('_token'))) {
            $entityManager->remove($reservation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reservation_back', [], Response::HTTP_SEE_OTHER);
    }
  
   




}
