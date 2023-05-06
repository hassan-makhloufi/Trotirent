<?php

namespace App\Repository;

use App\Entity\Trotinette;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Trotinette>
 *
 * @method Trotinette|null find($id, $lockMode = null, $lockVersion = null)
 * @method Trotinette|null findOneBy(array $criteria, array $orderBy = null)
 * @method Trotinette[]    findAll()
 * @method Trotinette[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class TrotinetteRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Trotinette::class);
    }

    public function save(Trotinette $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Trotinette $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

//    /**
//     * @return Trotinette[] Returns an array of Categorie objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Trotinette
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }

    public function order_By_Prix()
    {
        return $this->createQueryBuilder('s')
            ->orderBy('s.prix', 'ASC')
            ->getQuery()->getResult();
    }
  


    public function searchByMarque($marque)
        {
        return $this->createQueryBuilder('t')
            ->andWhere('t.marque LIKE :marque')
            ->setParameter('marque', '%'.$marque.'%')
            ->getQuery()
            ->getResult();
        }

        
}

