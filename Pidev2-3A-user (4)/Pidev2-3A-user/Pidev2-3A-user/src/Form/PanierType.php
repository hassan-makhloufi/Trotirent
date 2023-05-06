<?php

namespace App\Form;
use App\Entity\User;
use App\Entity\Trotinette;
use App\Entity\Panier;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;


class PanierType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')
            ->add('image',FileType::class, array('data_class' => null,'required' => false))

            ->add('prix')
            ->add('qty')
            
            ->add('idProduit',EntityType::class,['class'=> Trotinette::class,
                'choice_label'=>'description',
                'label'=>'Trotinette'])
            ->add('idUser',EntityType::class,['class'=> User::class,
                'choice_label'=>'nomUser',
                'label'=>'Votre nom']);
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Panier::class,
        ]);
    }
}
