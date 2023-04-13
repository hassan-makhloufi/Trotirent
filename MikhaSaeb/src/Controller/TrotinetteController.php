<?php

namespace App\Controller;

use App\Entity\Trotinette;
use App\Form\TrotinetteType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

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
    #[Route('/front', name: 'app_trotinette_index_front', methods: ['GET'])]
    public function indexFront(EntityManagerInterface $entityManager): Response
    {
        $trotinettes = $entityManager
            ->getRepository(Trotinette::class)
            ->findAll();

        return $this->render('trotinette/TrotFront.html.twig', [
            'trotinettes' => $trotinettes,
        ]);
    }

    #[Route('/new', name: 'app_trotinette_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $trotinette = new Trotinette();
        $form = $this->createForm(TrotinetteType::class, $trotinette);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($trotinette);
            $entityManager->flush();

            return $this->redirectToRoute('app_trotinette_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('trotinette/new.html.twig', [
            'trotinette' => $trotinette,
            'form' => $form,
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
