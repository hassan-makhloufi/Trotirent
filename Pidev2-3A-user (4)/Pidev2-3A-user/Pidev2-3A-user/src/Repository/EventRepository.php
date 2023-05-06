<?php

namespace App\Repository;

use App\Entity\Event;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\Query\ResultSetMapping;
/**
 * @extends ServiceEntityRepository<Event>
 *
 * @method Event|null find($id, $lockMode = null, $lockVersion = null)
 * @method Event|null findOneBy(array $criteria, array $orderBy = null)
 * @method Event[]    findAll()
 * @method Event[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EventRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Event::class);
    }

    public function save(Event $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Event $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function findEvent($id){
        echo $id;
        $entityManager = $this->getEntityManager();
    $rsm = new ResultSetMapping();
    $query = $entityManager
        ->createNativeQuery("SELECT * FROM event WHERE idEvent = :id", $rsm)
        ->setParameter('id', $id);
    return $query->getResult();
    }
  
    public function advancedSearch($prix, $nomevent, $localisation) {
        $queryBuilder = $this->createQueryBuilder('e');
        
        if ($prix) {
            $queryBuilder->andWhere('e.prix LIKE :prix')
                ->setParameter('prix', $prix);
        }
        
        if ($nomevent) {
            $queryBuilder->andWhere('e.nomevent LIKE :nomevent')
                ->setParameter('nomevent', $nomevent);
        }
        
        if ($localisation) {
            $queryBuilder->andWhere('e.localisation LIKE :localisation')
                ->setParameter('localisation', $localisation);
        }
        
        return $queryBuilder->getQuery()->getResult();
    }
    

    public function findByCategoryId(int $idcat)
    {
        return $this->createQueryBuilder('e')
            ->andWhere('e.idcat = :idcat')
            ->setParameter('idcat', $idcat)
            ->getQuery()
            ->getResult();
    }
/*

// trier par prix
public function sortByPrix() {
    return $this->createQueryBuilder('e')
        ->orderBy('e.prix', 'ASC')
        ->getQuery()
        ->getResult();
}
// trier par date debut
public function sortByDateDebut() {
    return $this->createQueryBuilder('e')
        ->orderBy('e.datedebut', 'ASC')
        ->getQuery()
        ->getResult();
}


// rechercher par prix 
public function findByprix($prix) {
    return $this->createQueryBuilder('e')   //creer une requete
        ->where('e.prix LIKE :prix')
        ->setParameter('prix', '%'.$prix.'%')
        ->getQuery()
        ->getResult();
}


// rechercher par nom
public function findByNomevent($nomevent) {
    return $this->createQueryBuilder('e')
        ->where('e.nomevent = :nomevent')
        ->setParameter('nomevent', $nomevent)
        ->getQuery()
        ->getResult();
}


public function advancedSearch($prix, $nomevent)
{
    $qb = $this->createQueryBuilder('e');

    if ($prix) {
        $qb->andWhere('e.prix LIKE :prix')
            ->setParameter('prix', '%'.$prix.'%');
    }

    if ($nomev) {
        $qb->andWhere('e.nomevent = :nomevent')
            ->setParameter('nomevent', $nomevent);
    }
   


    return $qb->getQuery()->getResult();
}
*/

//    /**
//     * @return Event[] Returns an array of Event objects
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

//    public function findOneBySomeField($value): ?Event
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
