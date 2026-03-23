package ma.fstg.security.spring_security_jpa.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Entité représentant un utilisateur de l'application.
 * Chaque utilisateur possède un login, un mot de passe, un statut activé/désactivé
 * et une collection de rôles (permissions) associée.
 */
@Entity
public class AppUser {

    // --- Clé primaire auto-générée ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Login unique de l'utilisateur ---
    private String login;

    // --- Mot de passe encodé (BCrypt par exemple) ---
    private String password;

    // --- Indique si le compte est activé ou non ---
    private boolean enabled;

    /**
     * Liste des rôles attribués à l'utilisateur.
     * FetchType.EAGER : les rôles sont chargés immédiatement avec l'utilisateur.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new ArrayList<>();

    /**
     * Constructeur par défaut requis par JPA.
     */
    public AppUser() {
    }

    /**
     * Constructeur pour créer un utilisateur complet avec ses informations et rôles.
     *
     * @param id Identifiant de l'utilisateur (généré automatiquement si null)
     * @param login Login de l'utilisateur
     * @param password Mot de passe encodé
     * @param enabled Statut activé/désactivé
     * @param roles Liste des rôles attribués
     */
    public AppUser(Long id, String login, String password, boolean enabled, Collection<AppRole> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<AppRole> getRoles() {
        return roles;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Collection<AppRole> roles) {
        this.roles = roles;
    }

    /**
     * Méthode utilitaire pour afficher les informations de l'utilisateur.
     * Utile pour le debug ou les logs.
     */
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}