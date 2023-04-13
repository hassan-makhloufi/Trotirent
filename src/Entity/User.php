<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user")
 * @ORM\Entity
 */
class User
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_user", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=250, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_user", type="string", length=250, nullable=false)
     */
    private $nomUser;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom_user", type="string", length=250, nullable=false)
     */
    private $prenomUser;

    /**
     * @var int
     *
     * @ORM\Column(name="cin_user", type="integer", nullable=false)
     */
    private $cinUser;

    /**
     * @var int
     *
     * @ORM\Column(name="nim_tel", type="integer", nullable=false)
     */
    private $nimTel;

    /**
     * @var string
     *
     * @ORM\Column(name="email_user", type="string", length=250, nullable=false)
     */
    private $emailUser;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse_user", type="string", length=250, nullable=false)
     */
    private $adresseUser;

    /**
     * @var string
     *
     * @ORM\Column(name="role", type="string", length=250, nullable=false)
     */
    private $role;

    public function getIdUser(): ?int
    {
        return $this->idUser;
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

    public function getNomUser(): ?string
    {
        return $this->nomUser;
    }

    public function setNomUser(string $nomUser): self
    {
        $this->nomUser = $nomUser;

        return $this;
    }

    public function getPrenomUser(): ?string
    {
        return $this->prenomUser;
    }

    public function setPrenomUser(string $prenomUser): self
    {
        $this->prenomUser = $prenomUser;

        return $this;
    }

    public function getCinUser(): ?int
    {
        return $this->cinUser;
    }

    public function setCinUser(int $cinUser): self
    {
        $this->cinUser = $cinUser;

        return $this;
    }

    public function getNimTel(): ?int
    {
        return $this->nimTel;
    }

    public function setNimTel(int $nimTel): self
    {
        $this->nimTel = $nimTel;

        return $this;
    }

    public function getEmailUser(): ?string
    {
        return $this->emailUser;
    }

    public function setEmailUser(string $emailUser): self
    {
        $this->emailUser = $emailUser;

        return $this;
    }

    public function getAdresseUser(): ?string
    {
        return $this->adresseUser;
    }

    public function setAdresseUser(string $adresseUser): self
    {
        $this->adresseUser = $adresseUser;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }


}
