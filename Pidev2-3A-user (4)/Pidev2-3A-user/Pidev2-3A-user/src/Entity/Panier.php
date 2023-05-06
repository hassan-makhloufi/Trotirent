<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

/**
 * Panier
 *
 * @ORM\Table(name="panier", indexes={@ORM\Index(name="id_produit", columns={"id_produit"}), @ORM\Index(name="idUser", columns={"idUser"})})
 * @ORM\Entity
 */
class Panier
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_panier", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idPanier;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=250, nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir  nom ")]

    private $nom;
      /**
     * 
     * @ORM\Column(length=1000)
     * 
     */
    #[Assert\NotBlank (message:"veuillez saisir l'image du l'evenement ")]
    private $image;

    /**
     * @var int
     *
     * @ORM\Column(name="prix", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir prix ")]

    private $prix;

    /**
     * @var int
     *
     * @ORM\Column(name="qty", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir quantité ")]

    private $qty;

    /**
     * @var \Trotinette
     *
     * @ORM\ManyToOne(targetEntity="Trotinette")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_produit", referencedColumnName="id_trot")
     * })
     */
    #[Assert\NotBlank (message:"veuillez saisir id trorinnete ")]

    private $idProduit;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="IdUser", referencedColumnName="IdUser")
     * })
     */
    #[Assert\NotBlank (message:"veuillez saisir id user ")]

    private $idUser;

    public function getIdPanier(): ?int
    {
        return $this->idPanier;
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

    public function getImage()
    {
        return $this->image;
    }

    public function setImage( $image)
    {
        $this->image = $image;

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

    public function getQty(): ?int
    {
        return $this->qty;
    }

    public function setQty(int $qty): self
    {
        $this->qty = $qty;

        return $this;
    }

    public function getIdProduit(): ?Trotinette
    {
        return $this->idProduit;
    }

    public function setIdProduit(?Trotinette $idProduit): self
    {
        $this->idProduit = $idProduit;

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

    
}
