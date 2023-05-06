<?php

namespace App\Form;
use App\Entity\Categorie;
use App\Entity\Trotinette;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\FileType;


class TrotinetteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('image',FileType::class, array('data_class' => null,'required' => false))
            ->add('nom')
            ->add('marque')
            ->add('energie')
            ->add('autonomie')
            ->add('prix')
            ->add('description')
            ->add('idCategorie',EntityType::class,['class'=> Categorie::class,
          'choice_label'=>'categorie',
          'label'=>'Categorie']);
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trotinette::class,
        ]);
    }
}
