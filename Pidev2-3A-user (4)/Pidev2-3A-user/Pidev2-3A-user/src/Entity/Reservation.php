<?php

namespace App\Entity;
use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Doctrine\CustomIdGenerator;
use App\Entity\Reservation;




/**
 * Reservation
 *
 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="IdEvent", columns={"IdEvent"}), @ORM\Index(name="IdUser", columns={"IdUser"})})
 * @ORM\Entity
 */
#[ORM\Entity(repositoryClass: ReservationRepository::class)]

class Reservation
{
   
    
    /**
     * @var int
    * @ORM\Id
 * @ORM\Column(name="IdRes", type="integer")
 * @ORM\GeneratedValue
     */
   
    private $idres;

    /**
     * @var Event
     *
     * 
     * @ORM\ManyToOne(targetEntity="Event")
     * @ORM\JoinColumn(name="IdEvent", referencedColumnName="IdEvent")
     */
    private $idevent;

    /**
     * @var User
     *

     
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumn(name="IdUser", referencedColumnName="IdUser")
     */
    private $iduser ;

   
    public function getIdres(): ?int
    {
        return $this->idres;
    }

    public function getIdevent(): ?Event
    {
        return $this->idevent;
    }

    public function setIdevent(Event $idevent):self
    {
        $this->idevent = $idevent;

        return $this;
    }

    public function getIduser(): ?User
    {
        return $this->iduser;
    }

    public function setIduser(User $iduser):self
    {
        $this->iduser = $iduser;

        return $this;
    }


}
