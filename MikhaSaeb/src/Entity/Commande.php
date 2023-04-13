<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;

use Doctrine\ORM\Mapping as ORM;

/**
 * Commande
 *
 * @ORM\Table(name="commande", indexes={@ORM\Index(name="id_client", columns={"id_client"})})
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
    #[Assert\NotBlank (message:"veuillez saisir produits ")]

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
     *   @ORM\JoinColumn(name="id_client", referencedColumnName="id_user")
     * })
     */    
    #[Assert\NotBlank (message:"veuillez saisir id client ")]

    private $idClient;

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

    public function getIdClient(): ?User
    {
        return $this->idClient;
    }

    public function setIdClient(?User $idClient): self
    {
        $this->idClient = $idClient;

        return $this;
    }


}
