package ma.fstg.security.spring_security_jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entité représentant un rôle d'utilisateur dans l'application.
 * Chaque rôle permet de définir des permissions spécifiques (ex: ADMIN, USER).
 */
@Entity
public class AppRole {

    // --- Clé primaire auto-générée ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Nom du rôle (ex: ROLE_ADMIN, ROLE_USER) ---
    private String roleName;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public AppRole() {
    }

    /**
     * Constructeur pour créer un rôle avec un identifiant et un nom.
     * @param id Identifiant du rôle (généré automatiquement si null)
     * @param roleName Nom du rôle
     */
    public AppRole(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    // --- Getter pour l'identifiant ---
    public Long getId() {
        return id;
    }

    // --- Getter pour le nom du rôle ---
    public String getRoleName() {
        return roleName;
    }

    // --- Setter pour l'identifiant ---
    public void setId(Long id) {
        this.id = id;
    }

    // --- Setter pour le nom du rôle ---
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Méthode utilitaire pour afficher le rôle sous forme de chaîne.
     * Utile pour le debug ou les logs.
     */
    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}