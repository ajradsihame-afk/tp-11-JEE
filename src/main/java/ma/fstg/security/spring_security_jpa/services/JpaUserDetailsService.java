package ma.fstg.security.spring_security_jpa.services;

import ma.fstg.security.spring_security_jpa.entities.AppUser;
import ma.fstg.security.spring_security_jpa.repositories.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service qui implémente UserDetailsService pour Spring Security.
 * Il permet de charger un utilisateur depuis la base de données via son login
 * et de le transformer en objet UserDetails pour l'authentification.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    // Repository pour accéder aux utilisateurs dans la base de données
    private final AppUserRepository appUserRepository;

    public JpaUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * Charge un utilisateur par son login.
     *
     * @param login Le login de l'utilisateur à authentifier
     * @return UserDetails de Spring Security contenant login, mot de passe et rôles
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        // Recherche de l'utilisateur dans la base de données
        AppUser appUser = appUserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Aucun utilisateur trouvé avec ce login : " + login
                ));

        // Transformation de AppUser en UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(
                appUser.getLogin(), // login
                appUser.getPassword(), // mot de passe encodé
                appUser.isEnabled(), // compte activé
                true, // accountNonExpired : vrai pour compte actif
                true, // credentialsNonExpired : vrai si mot de passe non expiré
                true, // accountNonLocked : vrai si compte non verrouillé
                appUser.getRoles().stream() // transformation des rôles AppRole en SimpleGrantedAuthority
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList())
        );
    }
}