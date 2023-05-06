<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Form\PanierType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;
#[Route('/panier')]
class PanierController extends AbstractController
{
    #[Route('/', name: 'app_panier_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $paniers = $entityManager
            ->getRepository(Panier::class)
            ->findAll();

        return $this->render('panier/index.html.twig', [
            'paniers' => $paniers,
        ]);
    }

    #[Route('/front', name: 'app_panier_index_front', methods: ['GET'])]
    public function indexFront(EntityManagerInterface $entityManager, Security $security): Response
    {
        
    // Récupérer l'utilisateur connecté
    $user = $security->getUser();

    // Récupérer les paniers de l'utilisateur connecté
    $paniers = $entityManager
        ->getRepository(Panier::class)
        ->findBy(['idUser' => $user]);

    return $this->render('panier/indexFront.html.twig', [
        'paniers' => $paniers,
    ]);
    }
    #[Route('/newFront', name: 'app_panier_new_front', methods: ['GET', 'POST'])]
    public function newFront(Request $request, EntityManagerInterface $entityManager): Response
    {
        $panier = new Panier();
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($panier);
            $entityManager->flush();

            return $this->redirectToRoute('app_panier_index_front', [], Response::HTTP_SEE_OTHER);
        }
    
    

        return $this->renderForm('panier/newFront.html.twig', [
            'panier' => $panier,
            'form' => $form,
          
        ]);
    }
    #[Route('/new', name: 'app_panier_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $panier = new Panier();
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($panier);
            $entityManager->flush();

            return $this->redirectToRoute('app_panier_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('panier/new.html.twig', [
            'panier' => $panier,
            'form' => $form,
        ]);
    }

    #[Route('/{idPanier}', name: 'app_panier_show', methods: ['GET'])]
    public function show(Panier $panier): Response
    {
        return $this->render('panier/show.html.twig', [
            'panier' => $panier,
        ]);
    }

    #[Route('/{idPanier}/edit', name: 'app_panier_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Panier $panier, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_panier_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('panier/edit.html.twig', [
            'panier' => $panier,
            'form' => $form,
        ]);
    }
    #[Route('/{idPanier}/edit/front', name: 'app_panier_edit_front', methods: ['GET', 'POST'])]
    public function editFront(Request $request, Panier $panier, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_panier_index_front', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('panier/editFront.html.twig', [
            'panier' => $panier,
            'form' => $form,
        ]);
    }


    #[Route('/{idPanier}', name: 'app_panier_delete', methods: ['POST'])]
    public function delete(Request $request, Panier $panier, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$panier->getIdPanier(), $request->request->get('_token'))) {
            $entityManager->remove($panier);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_panier_index', [], Response::HTTP_SEE_OTHER);
    } 
    #[Route('/{idPanier}', name: 'app_panier_deleteF', methods: ['POST'])]
    public function deletef(Request $request, Panier $panier, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$panier->getIdPanier(), $request->request->get('_token'))) {
            $entityManager->remove($panier);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_panier_index_front', [], Response::HTTP_SEE_OTHER);
    }
}
