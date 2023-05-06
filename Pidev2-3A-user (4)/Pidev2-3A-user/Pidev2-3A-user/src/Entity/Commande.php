<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

/**
 * Commande
 *
 * @ORM\Table(name="commande", indexes={@ORM\Index(name="iduser", columns={"iduser"})})
 * @ORM\Entity
 */
class Commande
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_commande", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idCommande;

    /**
     * @var int
     *
     * @ORM\Column(name="prix_totale", type="integer", nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir prix totale ")]

    private $prixTotale;

    /**
     * @var string
     *
     * @ORM\Column(name="produits", type="string", length=250, nullable=false)
     */
    //#[Assert\NotBlank (message:"veuillez saisir produits ")]

    private $produits;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=250, nullable=false)
     */
    #[Assert\NotBlank (message:"veuillez saisir adresses correcte! ")]

    private $adresse;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="IdUser", referencedColumnName="IdUser")
     * })
     */    
    //#[Assert\NotBlank (message:"veuillez saisir id client ")]

    private $iduser;
    

    public function getIdCommande(): ?int
    {
        return $this->idCommande;
    }

    public function getPrixTotale(): ?int
    {
        return $this->prixTotale;
    }

    public function setPrixTotale(int $prixTotale): self
    {
        $this->prixTotale = $prixTotale;

        return $this;
    }

    public function getProduits(): ?string
    {
        return $this->produits;
    }

    public function setProduits(string $produits): self
    {
        $this->produits = $produits;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getIduser(): ?User
    {
        return $this->iduser;
    }

    public function setIduser(?User $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }


}
