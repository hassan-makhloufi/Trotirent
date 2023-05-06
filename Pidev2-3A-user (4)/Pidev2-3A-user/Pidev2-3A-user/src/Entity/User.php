<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\UserRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Scheb\TwoFactorBundle\Model\Email\TwoFactorInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * User
 *
 * @ORM\Table(name="user")
 * @ORM\Entity(repositoryClass=UserRepository::class)
 */
class User implements UserInterface
{
    /**
     * @var int
     *
     * @ORM\Column(name="IdUser", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $iduser = null;

    /**
     * @var string
     *
     * @ORM\Column(name="NomUser", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $nomuser = null;

    /**
     * @var string
     *
     * @ORM\Column(name="PrenomUser", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $prenomuser = null;

    /**
     * @var string The hashed password
     *
     * @ORM\Column(name="Pwd", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $pwd = null;

    /**
     * @var string
     *
     * @ORM\Column(name="Email", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $email = null;

    /**
     * @var int
     *
     * @ORM\Column(name="NumTel", type="integer", nullable=false)
     */
    #[ORM\Column]
    private ?int $numtel = null;

    /**
     * @var string
     *
     * @ORM\Column(name="TypeUser", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $typeuser = null;

       /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private ?string $reset_token = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $authCode = null;

    /**
     * @var string
     *
     * @ORM\Column(name="Photo", type="string", length=255, nullable=false)
     */
    #[ORM\Column]
    private ?string $photo = null;
    #[ORM\Column(nullable: false)]
    private ?bool $isActive = false;

    #[ORM\Column(type: Types::DATETIME_MUTABLE, nullable: true)]
    private ?\DateTimeInterface $disabledUntil = null;


    public static function getAvailableTypeUserOptions(): array
    {
        // Define the available options for the "TypeUser" property
        return [
            'admin' => 'admin',
            'client' => 'client',
        ];
        
    }

    public function getIduser(): ?int
    {
        return $this->iduser;
    }

    public function getNomuser(): ?string
    {
        return $this->nomuser;
    }
    public function getUsername(): ?string
    {
        return $this->nomuser;
    }

    public function setNomuser(string $nomuser): self
    {
        $this->nomuser = $nomuser;

        return $this;
    }

    public function getPrenomuser(): ?string
    {
        return $this->prenomuser;
    }

    public function setPrenomuser(string $prenomuser): self
    {
        $this->prenomuser = $prenomuser;

        return $this;
    }

    public function getPwd(): ?string
    {
        return $this->pwd;
    }
     /**
     * @see PasswordAuthenticatedUserInterface
     */
    public function getPassword(): ?string
    {
        return $this->pwd;
    }
    public function setPassword(string $pwd): self
    {
        $this->pwd = $pwd;

        return $this;
    }
    public function setPwd(string $pwd): self
    {
        $this->pwd = $pwd;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getNumtel(): ?int
    {
        return $this->numtel;
    }

    public function setNumtel(int $numtel): self
    {
        $this->numtel = $numtel;

        return $this;
    }

    public function getTypeuser(): ?string
    {
        return $this->typeuser;
    }
    public function getRoles(): array
{
    return [$this->typeuser];
}

    public function setTypeuser(string $typeuser): self
    {
        $this->typeuser = $typeuser;

        return $this;
    }

    public function getPhoto(): ?string
    {
        return $this->photo;
    }

    public function setPhoto(string $photo): self
    {
        $this->photo = $photo;

        return $this;
    }
     /**
     * Returning a salt is only needed, if you are not using a modern
     * hashing algorithm (e.g. bcrypt or sodium) in your security.yaml.
     *
     * @see UserInterface
     */
    public function getSalt(): ?string
    {
        return null;
    }
     /**
     * @see UserInterface
     */
    public function eraseCredentials()
    {
        // If you store any temporary, sensitive data on the user, clear it here
        // $this->plainPassword = null;
    }
    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }
     /**
     * Return true if the user should do two-factor authentication.
     */
    public function isEmailAuthEnabled(): bool
    {
 return true;
    }

    /**
     * Return user email address.
     */
    public function getEmailAuthRecipient(): string
    {
return $this->email;
    }

    /**
     * Return the authentication code.
     */
    public function getEmailAuthCode(): ?string
    {
 if(null == $this->authCode){
                         throw new \LogicalException('The email authentification code was not set');
                      }
 return $this->authCode;
    }

    /**
     * Set the authentication code.
     */
    public function setEmailAuthCode(string $authCode): void
    {
        $this->authCode =$authCode;

    }
    public function isIsActive(): ?bool
    {
        return $this->isActive;
    }

    public function setIsActive(?bool $isActive): self
    {
        $this->isActive = $isActive;

        return $this;
    }

    public function getDisabledUntil(): ?\DateTimeInterface
    {
        return $this->disabledUntil;
    }

    public function setDisabledUntil(?\DateTimeInterface $disabledUntil): self
    {
        $this->disabledUntil = $disabledUntil;

        return $this;
    }


}

