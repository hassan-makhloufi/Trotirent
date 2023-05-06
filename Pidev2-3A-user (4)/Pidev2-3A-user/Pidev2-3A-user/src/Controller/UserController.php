<?php

namespace App\Controller;
use Symfony\Component\Mailer\MailerInterface;
use App\Entity\User;
use App\Form\UserType;
use App\Form\PwdType;
use App\Form\ForgotPasswordType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use App\Form\EditType;
use Symfony\Component\HttpFoundation\Session\Session;
use App\Form\new;
use App\Repository\UserRepository;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\Exception\AuthenticationException;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\Mime\Email;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Knp\Component\Pager\PaginatorInterface;
use Endroid\QrCode\QrCode;
use Symfony\Component\Mime\Part\DataPart;
use Endroid\QrCode\Color\Color;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelLow;
use Endroid\QrCode\Label\Label;
use Endroid\QrCode\Logo\Logo;
use Endroid\QrCode\RoundBlockSizeMode\RoundBlockSizeModeMargin;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCode\Writer\ValidationException;
use Endroid\QrCode\Label\Margin\Margin;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;
use Endroid\QrCode\Label\Alignment\LabelAlignmentCenter;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use TCPDF;


#[Route('/user')]
class UserController extends AbstractController
{

    private $builder;

    public function __construct(BuilderInterface $builder)
    {
        $this->builder = $builder;
    }

    #[Route('/{iduser}', name: 'app_user_showback', methods: ['GET'])]
    public function showback(User $user): Response
    {
        return $this->render('user/profilback.html.twig', [
            'user' => $user,
        ]);
    }
    #[Route('/PDF/a', name: 'generate_pdf')]
    public function generatePdf() {
     // Récupérer les détails de tableau depuis la base de données
    $users = $this->getDoctrine()->getRepository(User::class)->findAll();

    // Générer le fichier PDF du tableau d'utilisateurs
    $pdf = new TCPDF();
    $pdf->AddPage();

    // Ajouter le titre du tableau
    $pdf->SetFont('Helvetica', 'B', 16);
    $pdf->Write(10, 'Liste des utilisateurs - ' . date('d/m/Y'));

    // Ajouter le tableau d'utilisateurs
    $pdf->SetFont('Helvetica', '', 12);
    $html = $this->renderView('user/pdf.html.twig', ['users' => $users]);
    $pdf->writeHTML($html);

    // Envoyer le fichier PDF au navigateur
    $response = new BinaryFileResponse($pdf->Output('utilisateurs.pdf', 'I'));
    $response->setContentDisposition(ResponseHeaderBag::DISPOSITION_INLINE, 'utilisateurs.pdf');
    return $response;
}
    
public function qrcodeN($user)
{
    // $url = 'https://www.google.com/search?q=';

    $objDateTime = new \DateTime('NOW');
    $dateString = $objDateTime->format('d-m-Y H:i:s');

    $path = dirname(__DIR__, 2).'/public/uploads/images';

    $qrContent = "Bonjour cher(e) utilisateur(".$user->getNomuser()."),

    Nous sommes ravis de vous accueillir sur l'application TunMix. Nous espérons que vous apprécierez les fonctionnalités offertes par notre plateforme et que votre expérience sera des plus agréables.
    
    Veuillez trouver ci-dessous les données que nous avons enregistrées pour votre compte :
    
    Nom complet : ".$user->getNomuser()."   ".$user->getNomuser()."
    Adresse email : ".$user->getEmail()."
    Numéro de téléphone : ".$user->getNumtel()."
    
    N'hésitez pas à nous contacter si vous avez des questions ou des préoccupations. Nous sommes là pour vous aider à tout moment.
    
    Cordialement,
    L'équipe TunMix.";
    
    // set qrcode
    $result = $this->builder
        ->data($qrContent)
        ->encoding(new Encoding('UTF-8'))
        ->errorCorrectionLevel(new ErrorCorrectionLevelHigh())
        ->size(400)
        ->margin(10)
        ->labelText($dateString)
        ->labelAlignment(new LabelAlignmentCenter())
        ->labelMargin(new Margin(15, 5, 5, 5))
//           ->logoPath($path.'/img/logo.png')
//            ->logoResizeToWidth('100')
//            ->logoResizeToHeight('100')
        ->backgroundColor(new Color(221, 158, 3))
        ->build()
    ;
        
    //generate name
    $namePng = uniqid('', '') . '.png';

    //Save img png
    $result->saveToFile($path.'/qr-code/'.$namePng);

    return $result->getDataUri();
}


    #[Route('/{iduser}/showprofile/b', name: 'showprofile')]
public function front(User $user): Response
{
    $qrCodeUri = $this->qrcodeN($user);

    return $this->render('user/profil.html.twig', [
        'controller_name' => 'UserController',
        'user' => $user,
        'qrCodeUri' => $qrCodeUri, 

    ]);
}
    #[Route('/Admin/a', name: 'app_user_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager, PaginatorInterface $paginator,Request $request): Response
    {
        $users = $entityManager
            ->getRepository(User::class)
            ->findAll();


            $pagination = $paginator->paginate(
                $users,
                $request->query->getInt('page', 1), 3
            );

        return $this->render('user/index.html.twig', [
            'pagination' => $pagination,
        ]);
        
    }
    #[Route('/Client/a', name: 'app_user_home', methods: ['GET'])]
    public function home(EntityManagerInterface $entityManager): Response
    {
        $users = $entityManager
            ->getRepository(User::class)
            ->findAll();

        return $this->render('user/home.html.twig', [
            'users' => $users,
        ]);
    }
    
    #[Route('/new/a', name: 'app_user_new', methods: ['GET', 'POST'])]
    public function new(Request $request, UserPasswordHasherInterface $userPasswordHasher, EntityManagerInterface $entityManager,Session $session): Response
    {

        $password = 'mon_mot_de_passe';
        $hashedPassword = password_hash($password, PASSWORD_BCRYPT, ['cost' => 12]);

        $user = new User();

        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {
           
            $uploadedFile = $form['Photo']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFile = $originalFilename.'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFile
            );
            $user->setPhoto($newFile);

            


           
            $user->setPwd(
                $userPasswordHasher->hashPassword(
                    $user,
                    $form->get('pwd')->getData()
                )



                
            );
            

            $entityManager->persist($user);
            $entityManager->flush();

            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/new.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }


    #[Route('/inscri/a', name: 'app_user_inscri', methods: ['GET', 'POST'])]
    public function inscri(Request $request, UserPasswordHasherInterface $userPasswordHasher, EntityManagerInterface $entityManager,Session $session): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
           
            $uploadedFile = $form['Photo']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFile = $originalFilename.'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFile
            );
            $user->setPhoto($newFile);

            $user->setPwd(
                $userPasswordHasher->hashPassword(
                    $user,
                    $form->get('pwd')->getData()
                )
            );

            $entityManager->persist($user);
            $entityManager->flush();

            return $this->redirectToRoute('app_user_home', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/inscrirUser.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/{iduser}', name: 'app_user_show', methods: ['GET'])]
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }

   

    

  

    #[Route('/{iduser}/edit', name: 'app_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, UserPasswordHasherInterface $userPasswordHasher, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(userType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            
            $uploadedFile = $form['Photo']->getData();
            $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
            $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFile = $originalFilename.'-'.uniqid().'.'.$uploadedFile->guessExtension();
            $uploadedFile->move(
                $destination,
                $newFile
               );
               $user->setPhoto($newFile);
              
               $em=$this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
          
            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }











    #[Route('/{iduser}', name: 'app_user_delete', methods: ['POST'])]
    public function delete(Request $request, User $user, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getIduser(), $request->request->get('_token'))) {
            $entityManager->remove($user);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/sign', name: 'app_sign')]
public function sign(): Response
{
    return $this->render('user/sign.html.twig', [
        'controller_name' => 'UserController',
    ]);
}

#[Route('/{iduser}/editprofile', name: 'editprofile', methods: ['GET', 'POST'])]
public function editprofile(Request $request, User $user, UserPasswordHasherInterface $userPasswordHasher, EntityManagerInterface $entityManager): Response
{ 
    $form = $this->createForm(userType::class, $user);
    $form->handleRequest($request);
    if ($form->isSubmitted() && $form->isValid()) {
        
        $uploadedFile = $form['Photo']->getData();
        $destination = $this->getParameter('kernel.project_dir').'/public/uploads';
        $originalFilename = pathinfo($uploadedFile->getClientOriginalName(), PATHINFO_FILENAME);
        $newFile = $originalFilename.'-'.uniqid().'.'.$uploadedFile->guessExtension();
        $uploadedFile->move(
            $destination,
            $newFile
           );
           $user->setPhoto($newFile);
    
      
           $em=$this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();
            return $this->redirectToRoute('showprofile', ['iduser' => $user->getIduser()], Response::HTTP_SEE_OTHER);
    }


    return $this->renderForm('user/editprofile.html.twig', [
        'user' => $user,
        'form' => $form,

    ]);
}
#[Route('/{iduser}/editmdp', name: 'editmdp')]
public function editmdp(Request $request, UserPasswordHasherInterface $userPasswordHasher, EntityManagerInterface $entityManager): Response
{  $user=$this->getUser();
        $form = $this->createForm(pwdType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
          
           
          
            // encode the plain password
            $user->setPassword(
                $userPasswordHasher->hashPassword(
                    $user,
                    $form->get('pwd')->getData()
                )
            );

            $entityManager->persist($user);
            $entityManager->flush();
            // do anything else you need here, like send an email
$this->addFlash('message','Mot de passe mis a jour avec succés');
            return $this->redirectToRoute('showprofile', ['iduser' => $user->getIduser()]);
   
    }
    return $this->render('user/editmdp.html.twig', [
        'form' => $form->createView(),
    ]);

}
public function qrcode($user)
{
    // $url = 'https://www.google.com/search?q=';

    $objDateTime = new \DateTime('NOW');
    $dateString = $objDateTime->format('d-m-Y H:i:s');

    $path = dirname(__DIR__, 2).'/public/uploads/images';

    $qrContent = "Id: ".$user->getIduser().", Nom: ".$user->getNomuser();

    // set qrcode
    $result = $this->builder
        ->data($qrContent)
        ->encoding(new Encoding('UTF-8'))
        ->errorCorrectionLevel(new ErrorCorrectionLevelHigh())
        ->size(400)
        ->margin(10)
        ->labelText($dateString)
        ->labelAlignment(new LabelAlignmentCenter())
        ->labelMargin(new Margin(15, 5, 5, 5))
//           ->logoPath($path.'/img/logo.png')
//            ->logoResizeToWidth('100')
//            ->logoResizeToHeight('100')
        ->backgroundColor(new Color(221, 158, 3))
        ->build()
    ;
        
    //generate name
    $namePng = uniqid('', '') . '.png';

    //Save img png
    $result->saveToFile($path.'/qr-code/'.$namePng);

    return $result->getDataUri();
}


  /**
     * @Route("/email/a", name="email")
     */
    public function sendEmail(Request $request, MailerInterface $mailer,UserRepository $userRepository,TokenGeneratorInterface  $tokenGenerator): Response
    {



        $path = dirname(__DIR__, 2).'/public/uploads/images';
        $form = $this->createForm(ForgotPasswordType::class);
        $form->handleRequest($request);
        if($form->isSubmitted()) {
            $donnees = $form->getData();//


            $user = $userRepository->findOneBy(['email'=>$donnees]);
            if(!$user) {
                $this->addFlash('danger','cette adresse n\'existe pas');
                return $this->redirectToRoute("forgot");

            }
            $token = $tokenGenerator->generateToken();

            try{
                $user->setResetToken($token);
                $entityManger = $this->getDoctrine()->getManager();
                $entityManger->persist($user);
                $entityManger->flush();




            }catch(\Exception $exception) {
                $this->addFlash('warning','une erreur est survenue :'.$exception->getMessage());
                return $this->redirectToRoute("app_login");


            }

            $url = $this->generateUrl('app_reset_password',array('token'=>$token),UrlGeneratorInterface::ABSOLUTE_URL);
        
            
            $email = (new Email())
                ->from('Trottient@gmail.com')
                ->to($user->getEmail())
                ->subject('Réinitialisation de mot de passe')
                ->html('
                    <p>Bonjour cher client,</p>
                    <p>Nous avons remarqué que vous avez oublié votre mot de passe pour accéder à notre site . Pas de panique, nous avons une solution pour vous !</p>
                    <p>Veuillez cliquer sur le lien suivant pour accéder à notre page de réinitialisation de mot de passe : '.$url.'</p>
                    <p>Si vous avez des questions ou des préoccupations, n\'hésitez pas à nous contacter. Nous sommes toujours là pour vous aider.</p>
          
                    <p>Cordialement,</p>
                  
                    

                 
                ');
 try {
        $mailer->send($email);
        $this->addFlash('message','E-mail  de réinitialisation du mp envoyé :');
    } catch (TransportExceptionInterface $e) {
        // Gérer les erreurs d'envoi de courriel
    }

    
}
return $this->render("user/forgotPassword.html.twig",['form'=>$form->createView()]);


    }

    

   
    /**
     * @Route("/resetpassword/{token}", name="app_reset_password")
     */
    public function resetpassword(Request $request,string $token, UserPasswordEncoderInterface  $passwordEncoder)
    {
        $user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['reset_token'=>$token]);

        if($user == null ) {
            $this->addFlash('danger','TOKEN INCONNU');
            return $this->redirectToRoute("app_login");

        }

        if($request->isMethod('POST')) {
            $user->setResetToken(null);

            $user->setPassword($passwordEncoder->encodePassword($user,$request->request->get('password')));
            $entityManger = $this->getDoctrine()->getManager();
            $entityManger->persist($user);
            $entityManger->flush();

            $this->addFlash('message','Mot de passe mis à jour :');
            return $this->redirectToRoute("app_login");

        }
        else {
            return $this->render("user/resetPassword.html.twig",['token'=>$token]);

        }
    }
    /**
     * @Route("/{iduser}/enable", name="user_enable")
     */
    public function enableUser(User $user): Response
    {
        $user->setisActive(true);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        return $this->redirectToRoute('app_user_showback', ['iduser' => $user->getIduser()]);
    }

    /**
     * @Route("/{iduser}/disable", name="user_disable")
     */
    public function disableUser(User $user): Response
    {
        $user->setisActive(0);
        $now = new \DateTime();
        $disabledUntil = clone $now;
        $disabledUntil->modify('+2 minute');

        $user->setDisabledUntil($disabledUntil);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        return $this->redirectToRoute('app_user_showback', ['iduser' => $user->getIduser()]);
    }


    
}
