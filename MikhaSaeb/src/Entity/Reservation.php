<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Reservation
 *
 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="id_user", columns={"id_user"}), @ORM\Index(name="id_trot", columns={"id_trot"})})
 * @ORM\Entity
 */
class Reservation
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_reservation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idReservation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_debut", type="date", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir date debut ")]
    private $dateDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_fin", type="date", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir date fin ")]

    private $dateFin;

    /**
     * @var int
     *
     * @ORM\Column(name="prix", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir prix ")]

    private $prix;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     * })
     */
    #[Assert\NotBlank (message:"veuillez saisir prix ")]

    private $idUser;

    /**
     * @var \Trotinette
     *
     * @ORM\ManyToOne(targetEntity="Trotinette")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_trot", referencedColumnName="id_trot")
     * })
     */
    #[Assert\NotBlank (message:"veuillez saisir id trotinnette ")]

    private $idTrot;

    public function getIdReservation(): ?int
    {
        return $this->idReservation;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getIdTrot(): ?Trotinette
    {
        return $this->idTrot;
    }

    public function setIdTrot(?Trotinette $idTrot): self
    {
        $this->idTrot = $idTrot;

        return $this;
    }


}
