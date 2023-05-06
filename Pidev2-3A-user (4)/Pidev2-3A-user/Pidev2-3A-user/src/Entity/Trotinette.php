<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Trotinette
 *
 * @ORM\Table(name="trotinette", indexes={@ORM\Index(name="id_categorie", columns={"id_categorie"})})
 * @ORM\Entity
 */
class Trotinette
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_trot", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idTrot;

    /**
     * @ORM\Column(type="string", length=255)
     */
    #[Assert\NotBlank (message:"veuillez saisir nom ")]

    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="marque", type="string", length=250, nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir marque ")]
    private $marque;

    /**
     * @var string
     *
     * @ORM\Column(name="energie", type="string", length=250, nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir energie ")]

    private $energie;

    /**
     * @var int
     *
     * @ORM\Column(name="autonomie", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir autonomie ")]

    private $autonomie;

    /**
     * @var int
     *
     * @ORM\Column(name="prix", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir prix ")]

    private $prix;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text", length=65535, nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir description ")]
    private $description;

    /**
     * @var \Categorie
     *
     * @ORM\ManyToOne(targetEntity="Categorie")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_categorie", referencedColumnName="id")
     * })
     */
    #[Assert\NotBlank (message:"veuillez saisir id categorie ")]

    private $idCategorie;
      /**
     * 
     * @ORM\Column(length=1000)
     * 
     */
    #[Assert\NotBlank (message:"veuillez saisir l'image du l'evenement ")]
    private $image;

    public function getIdTrot(): ?int
    {
        return $this->idTrot;
    }
    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getMarque(): ?string
    {
        return $this->marque;
    }

    public function setMarque(string $marque): self
    {
        $this->marque = $marque;

        return $this;
    }

    public function getEnergie(): ?string
    {
        return $this->energie;
    }

    public function setEnergie(string $energie): self
    {
        $this->energie = $energie;

        return $this;
    }

    public function getAutonomie(): ?int
    {
        return $this->autonomie;
    }

    public function setAutonomie(int $autonomie): self
    {
        $this->autonomie = $autonomie;

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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getIdCategorie(): ?Categorie
    {
        return $this->idCategorie;
    }

    public function setIdCategorie(?Categorie $idCategorie): self
    {
        $this->idCategorie = $idCategorie;

        return $this;
    }
    public function getImage()
    {
        return $this->image;
    }

    public function setImage( $image)
    {
        $this->image = $image;

        return $this;
    }


}
