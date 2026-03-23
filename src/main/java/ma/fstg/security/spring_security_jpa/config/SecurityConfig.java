package ma.fstg.security.spring_security_jpa.config;

import ma.fstg.security.spring_security_jpa.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuration de Spring Security.
 * Elle définit l'authentification, les rôles et les règles d'accès aux URLs.
 */
@Configuration
public class SecurityConfig {

    // Service personnalisé qui charge les détails des utilisateurs depuis la base de données
    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    /**
     * Bean pour encoder les mots de passe en utilisant BCrypt.
     * @return un encodeur de mot de passe BCrypt
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean qui configure le fournisseur d'authentification basé sur DAO.
     * Il utilise notre JpaUserDetailsService et l'encodeur BCrypt pour vérifier les mots de passe.
     * @return DaoAuthenticationProvider configuré
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(jpaUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder()); // Utilisation de BCrypt pour comparer les mots de passe
        return provider;
    }

    /**
     * Définition de la chaîne de filtres de sécurité pour gérer les accès HTTP.
     * @param http HttpSecurity fourni par Spring Security
     * @return SecurityFilterChain configuré
     * @throws Exception si la configuration échoue
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // Définition du fournisseur d'authentification personnalisé
                .authenticationProvider(daoAuthenticationProvider())

                // Autorisation d'accès aux URLs selon les rôles
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/access-denied").permitAll() // accès public
                        .requestMatchers("/admin/**").hasRole("ADMIN") // accès réservé aux ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // accès USER ou ADMIN
                        .requestMatchers("/home").authenticated() // accessible uniquement si connecté
                        .anyRequest().authenticated() // toutes les autres requêtes nécessitent authentification
                )

                // Configuration du formulaire de login
                .formLogin(form -> form
                        .loginPage("/login") // page de connexion personnalisée
                        .defaultSuccessUrl("/home", true) // redirection après login réussi
                        .failureUrl("/login?error=true") // redirection en cas d'erreur
                        .permitAll()
                )

                // Configuration du logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true") // redirection après déconnexion
                        .permitAll()
                )

                // Gestion des accès refusés
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied") // page personnalisée pour accès interdit
                );

        return http.build(); // Retourne la chaîne de filtres construite
    }
}