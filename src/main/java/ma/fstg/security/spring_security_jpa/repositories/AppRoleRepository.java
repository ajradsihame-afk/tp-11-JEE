package ma.fstg.security.spring_security_jpa.repositories;

import ma.fstg.security.spring_security_jpa.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour gérer les opérations CRUD sur l'entité AppRole.
 * Étend JpaRepository pour bénéficier des méthodes standards de Spring Data JPA.
 */
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    /**
     * Recherche un rôle par son nom exact.
     * Utile pour vérifier si un rôle existe déjà ou pour l'assigner à un utilisateur.
     *
     * @param roleName Nom du rôle à rechercher (ex: "ROLE_ADMIN")
     * @return l'objet AppRole correspondant, ou null si non trouvé
     */
    AppRole findByRoleName(String roleName);
}