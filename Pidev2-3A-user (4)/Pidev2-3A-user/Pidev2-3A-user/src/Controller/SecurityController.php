<?php

namespace App\Controller;

use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Form\ForgotPasswordType;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\UserRepository;
use App\Form\UserType;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use App\Entity\User;
use App\Form\RegistrationFormType;
use Swift_Mailer;
use Swift_Message;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Mime\Email;;

class SecurityController extends AbstractController
{
   
    #[Route(path: '/login', name: 'app_login')]
    public function login(AuthenticationUtils $authenticationUtils,Session $session): Response
    {
    
        // if ($this->getUser()) {
        //     return $this->redirectToRoute('target_path');
        // }

        // get the login error if there is one
        // $error = $authenticationUtils->getLastAuthenticationError();
        // // last username entered by the user
        // $lastUsername = $authenticationUtils->getLastUsername();
        // return $this->render('user/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);



         // if ($this->getUser()) {
                //     return $this->redirectToRoute('target_path');
                // }

                // get the login error if there is one
                $error = $authenticationUtils->getLastAuthenticationError();
                // last username entered by the user
                $lastUsername = $authenticationUtils->getLastUsername();

                $return = ['last_username' => $lastUsername, 'error' => $error];

                if($session->has('message'))
                {
                        $message = $session->get('message');
                        $session->remove('message'); //on vide la variable message dans la session
                        $return['message'] = $message; //on ajoute à l'array de paramètres notre message
                }

                return $this->render('security/login.html.twig', $return);
       
    }

    // #[Route(path:'/logout', name: 'app_logout')]
    // public function logout(): void
    // {  

    //     throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    // }

         /**
         * @Route("/logout", name="app_logout")
         */
        public function logout(Request $request, ?Response $response, TokenInterface $token)
        {
            // clear any remember me cookies
            $response = parent::logout($request, $response, $token);
        
            // redirect to the homepage
            return new RedirectResponse($this->urlGenerator->generate('app_user_home'));
        }
}
