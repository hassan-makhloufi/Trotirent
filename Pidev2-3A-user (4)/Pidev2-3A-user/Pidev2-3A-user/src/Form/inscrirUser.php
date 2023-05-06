<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class inscrirUser extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('nomuser')
        ->add('prenomuser')
        ->add('pwd')
        ->add('email')
        ->add('numtel')
        ->add('typeuser')
        ->add('Photo'  , FileType::class ,[
            'data_class' => null,
            'label' => 'Photo' ,
            'attr' => ['placeholder' => 'Photo.jpg',
            'class'=>"form-control-file"]
      ])    ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
