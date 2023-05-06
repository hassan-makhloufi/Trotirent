<?php

namespace App\Form;

use App\Entity\Event;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\PositiveOrZero;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\ExecutionContextInterface;
use App\Entity\Categorieevent;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints\NotNull;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;

class EventType extends AbstractType
{

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomevent')
            ->add('datedebut', DateType::class, [
                'constraints' => [new NotNull()],
                'data' => new \DateTime(), // Définit la date de début à aujourd'hui
            ])
            ->add('datefin', DateType::class, [
                'constraints' => [new NotNull()],
                'data' => new \DateTime(),]) // Définit la date de début à aujourd'hui)
            ->add('localisation', HiddenType::class, [
                    'attr' => [
                        'class' => 'localisation-field',
                    ],
                ])
            ->add('description')
            ->add('heureevent')
            ->add('prix', NumberType::class, [
        'label' => 'Prix',
        'required' => true,
        'scale' => 2,
        'constraints' => [
            new NotBlank([
                'message' => 'Veuillez saisir le prix de l\'événement',
            ]),
            new PositiveOrZero([
                'message' => 'Le prix doit être un nombre positif ou nul',
            ]),
        ],
    ])
            ->add('photoe', FileType::class, [
                'data_class' => null,
                'required' => false,
                'label' => 'Upload image',
                'attr' => ['placeholder' => 'Photo.jpg',
                'class'=>"form-control-file"]
            ])
            ->add('idcat', EntityType::class, [
                'class' => Categorieevent::class,
                'choice_label' => 'nomcat',
                'multiple' => false,
            ])
        ;
    }
    
}
