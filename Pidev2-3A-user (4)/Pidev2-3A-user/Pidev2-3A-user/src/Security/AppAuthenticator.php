<?php

namespace App\Security;
use App\Entity\User;

use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Security;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Security\Http\Authenticator\AbstractLoginFormAuthenticator;
use Symfony\Component\Security\Http\Authenticator\Passport\Badge\CsrfTokenBadge;
use Symfony\Component\Security\Http\Authenticator\Passport\Badge\UserBadge;
use Symfony\Component\Security\Http\Authenticator\Passport\Credentials\PasswordCredentials;
use Symfony\Component\Security\Http\Authenticator\Passport\Passport;
use Symfony\Component\Security\Http\Util\TargetPathTrait;

class AppAuthenticator extends AbstractLoginFormAuthenticator
{
    
    use TargetPathTrait;

    public const LOGIN_ROUTE = 'app_login';
    
    private UrlGeneratorInterface $urlGenerator;
    

    public function __construct(EntityManagerInterface $entityManager, UrlGeneratorInterface $urlGenerator)
    {
        $this->entityManager = $entityManager;
        $this->urlGenerator = $urlGenerator;
    }

    public function authenticate(Request $request): Passport
    {
        $email = $request->request->get('email', '');
        $request->getSession()->set(Security::LAST_USERNAME, $email);
        $user = $this->entityManager->getRepository(User::class)->findOneBy(['email' => $email]);

        $request->getSession()->set(Security::LAST_USERNAME, $email);
        // if (!$user || !$user->isIsActive()) {
        //     throw new AuthenticationException('Invalid credentials.');
        // }
        if($user){
        $disabledUntil = $user->getDisabledUntil();

        if ($disabledUntil && $disabledUntil > new \DateTime() && !$user->isIsActive()) {
            throw new AuthenticationException(sprintf('Your account has been disabled. Please try again in %d seconds.', $disabledUntil->getTimestamp() - time()));
        }
    }
        return new Passport(
            new UserBadge($email),
            new PasswordCredentials($request->request->get('password', '')),
            [
                new CsrfTokenBadge('authenticate', $request->request->get('_csrf_token')),
            ]
        );
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, string $firewallName): ?Response
    {
        if ($targetPath = $this->getTargetPath($request->getSession(), $firewallName)) {
            return new RedirectResponse($targetPath);
        }

        // For example:
        // return new RedirectResponse($this->urlGenerator->generate('some_route'));
        // redirection selon role
        $role = $token->getUser()->getTypeuser();
        if($role === "admin"){
            return new RedirectResponse($this->urlGenerator->generate('app_user_index'));
       

        }else{
            return new RedirectResponse($this->urlGenerator->generate('app_user_home'));

        }

       // For example:
       // return new RedirectResponse($this->urlGenerator->generate('app_user_freelancers'));
       throw new \Exception('TODO: provide a valid redirect inside '.__FILE__);

    }

    protected function getLoginUrl(Request $request): string
    {
       return $this->urlGenerator->generate(self::LOGIN_ROUTE);
        //return $this->generateUrl('app_login', [], UrlGeneratorInterface::ABSOLUTE_URL);
    }
    

}
