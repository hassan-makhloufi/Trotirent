<?php

namespace App\Form;
use App\Entity\Categorie;
use App\Entity\Trotinette;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;


class TrotinetteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('marque')
            ->add('energie')
            ->add('autonomie')
            ->add('prix')
            ->add('description')
            ->add('idCategorie',EntityType::class,
                ['class'=>Categorie::class,'choice_label'=>'id',
                    'expanded'=>true
                ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trotinette::class,
        ]);
    }
}
