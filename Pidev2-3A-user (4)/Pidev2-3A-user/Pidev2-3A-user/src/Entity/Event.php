<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
/**
 * Event
 *
 * @ORM\Table(name="event", indexes={@ORM\Index(name="idCat", columns={"idCat"})})
 * @ORM\Entity
 */
class Event
{
    /**
     * @var int
     *
     * @ORM\Column(name="IdEvent", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idevent;

    /**
     * @var string
     *
     * @ORM\Column(name="NomEvent", type="string", length=255, nullable=false)
     */
    private $nomevent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DateDebut", type="date", nullable=false)
     */
    private $datedebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="DateFin", type="date", nullable=false)
     */
    private $datefin;

    /**
     * @var string
     *
     * @ORM\Column(name="Localisation", type="string", length=255, nullable=false)
     */
    private $localisation;

    /**
     * @var string
     *
     * @ORM\Column(name="Description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="HeureEvent", type="string", length=255, nullable=false)
     */
    private $heureevent;

    /**
     * @var float
     *
     * @ORM\Column(name="prix", type="float", precision=10, scale=0, nullable=false)
     */
    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="PhotoE", type="string", length=255, nullable=false)
     */
    private $photoe;

    /**
     * @var \Categorieevent
     *
     * @ORM\ManyToOne(targetEntity="Categorieevent")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idCat", referencedColumnName="idCat")
     * })
     */
    private $idcat;

    public function getIdevent(): ?int
    {
        return $this->idevent;
    }

    public function getNomevent(): ?string
    {
        return $this->nomevent;
    }

    public function setNomevent(string $nomevent): self
    {
        $this->nomevent = $nomevent;

        return $this;
    }

    public function getDatedebut(): ?\DateTimeInterface
    {
        return $this->datedebut;
    }

    public function setDatedebut(\DateTimeInterface $datedebut): self
    {
        $this->datedebut = $datedebut;

        return $this;
    }

    public function getDatefin(): ?\DateTimeInterface
    {
        return $this->datefin;
    }

    public function setDatefin(\DateTimeInterface $datefin): self
    {
        $this->datefin = $datefin;

        return $this;
    }

    public function getLocalisation(): ?string
    {
        return $this->localisation;
    }

    public function setLocalisation(string $localisation): self
    {
        $this->localisation = $localisation;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getHeureevent(): ?string
    {
        return $this->heureevent;
    }

    public function setHeureevent(string $heureevent): self
    {
        $this->heureevent = $heureevent;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getPhotoe(): ?string
    {
        return $this->photoe;
    }

    public function setPhotoe(string $photoe): self
    {
        $this->photoe = $photoe;

        return $this;
    }

    public function getIdcat(): ?Categorieevent
    {
        return $this->idcat;
    }

    public function setIdcat(?Categorieevent $idcat): self
    {
        $this->idcat = $idcat;

        return $this;
    }
  
  
/**
     * @Assert\Callback
     */
    public function validateDates(ExecutionContextInterface $context, $payload)
    {
        $now = new \DateTime(); // obtenir la date actuelle

        if ($this->getDatefin() < $this->getDatedebut()) {
            $context->buildViolation('La date de début doit être antérieure à la date de fin')
                ->atPath('dateDebut')
                ->addViolation();
        }
        if ($this->getDatedebut() < $now || $this->getDatefin() < $now) {
            $context->buildViolation('Les dates doivent être supérieures ou égales à la date actuelle')
                ->atPath('dateDebut')
                ->addViolation();
        }
    }

}
