<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\Form\FormError;

class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomuser')
            ->add('prenomuser')
            ->add('pwd')
            ->add('email')
            ->add('numtel')
            ->add('typeuser', ChoiceType::class, [
                'label' => 'Type User',
                'choices' => User::getAvailableTypeUserOptions(),
                'placeholder' => 'Choose an option',
            ])
            ->add('Photo', FileType::class ,[
                'data_class' => null,
                'label' => 'Photo' ,
                'attr' => [
                    'placeholder' => 'Photo.jpg',
                    'class' => "form-control-file"
                ]
            ])
            ->addEventListener(FormEvents::SUBMIT, function (FormEvent $event) {
                $form = $event->getForm();
                $data = $event->getData();

                // Vérification du champ 'email'
                $email = $data->getEmail();
                if (empty($email)) {
                    $form->get('email')->addError(new FormError('Le champ Email est obligatoire.'));
                } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL) || !preg_match('/@.*\.*$/', $email)) {
                    $form->get('email')->addError(new FormError('verfier votre email cher Client le champ email cotient @ et fini par .tn'));
                }

                // Vérification du champ 'numtel'
                $numtel = $data->getNumtel();
if (empty($numtel)) {
    $form->get('numtel')->addError(new FormError('Le champ Numéro de téléphone est obligatoire.'));
} elseif (!preg_match('/^\d{8}$/', $numtel)) {
    $form->get('numtel')->addError(new FormError('Verifiez votre numtel cher Client. Le champ Numéro de téléphone doit contenir exactement 8 chiffres.'));
}

                
                  
                
                

                // Vérification du champ 'typeuser'
                $typeuser = $data->getTypeuser();
                if (empty($typeuser)) {
                    $form->get('typeuser')->addError(new FormError('Le champ Type User est obligatoire.'));
                } elseif (!in_array($typeuser, User::getAvailableTypeUserOptions())) {
                    $form->get('typeuser')->addError(new FormError('Le champ Type User contient une option invalide.'));
                }

               
            })
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
