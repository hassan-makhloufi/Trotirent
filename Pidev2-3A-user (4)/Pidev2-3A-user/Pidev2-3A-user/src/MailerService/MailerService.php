<?php

namespace App\Service;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use App\Entity\Commande;

class MailerService
{
    
    private $mailer;
    
    
    public function __construct( MailerInterface $mailer)
     {
        
        $this->mailer=$mailer;
     }
    
    public function sendEmail( $to ): void
    {
        
        $email = (new Email())
            ->from('Trottient@gmail.com')
            ->to($to)
            ->subject('Validation de Commade')
            ->text(' confirmer votre Commande , merci!');
            
            $this->mailer->send($email);
      
        // ...
    }
}
?>