<?php

namespace App\Controller;

use App\Entity\Categorieevent;
use App\Form\CategorieeventType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/categorieevent')]
class CategorieeventController extends AbstractController
{
    #[Route('/', name: 'app_categorieevent_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $categorieevents = $entityManager
            ->getRepository(Categorieevent::class)
            ->findAll();

        return $this->render('categorieevent/index.html.twig', [
            'categorieevents' => $categorieevents,
        ]);
    }

    #[Route('/new', name: 'app_categorieevent_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $categorieevent = new Categorieevent();
        $form = $this->createForm(CategorieeventType::class, $categorieevent);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($categorieevent);
            $entityManager->flush();

            return $this->redirectToRoute('app_categorieevent_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('categorieevent/new.html.twig', [
            'categorieevent' => $categorieevent,
            'form' => $form,
        ]);
    }

    #[Route('/{idcat}', name: 'app_categorieevent_show', methods: ['GET'])]
    public function show(Categorieevent $categorieevent): Response
    {
        return $this->render('categorieevent/show.html.twig', [
            'categorieevent' => $categorieevent,
        ]);
    }

    #[Route('/{idcat}/edit', name: 'app_categorieevent_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Categorieevent $categorieevent, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CategorieeventType::class, $categorieevent);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_categorieevent_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('categorieevent/edit.html.twig', [
            'categorieevent' => $categorieevent,
            'form' => $form,
        ]);
    }

    #[Route('/{idcat}', name: 'app_categorieevent_delete', methods: ['POST'])]
    public function delete(Request $request, Categorieevent $categorieevent, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$categorieevent->getIdcat(), $request->request->get('_token'))) {
            $entityManager->remove($categorieevent);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_categorieevent_index', [], Response::HTTP_SEE_OTHER);
    }
}
