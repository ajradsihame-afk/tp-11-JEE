package ma.fstg.security.spring_security_jpa.config;

import ma.fstg.security.spring_security_jpa.entities.AppRole;
import ma.fstg.security.spring_security_jpa.entities.AppUser;
import ma.fstg.security.spring_security_jpa.repositories.AppRoleRepository;
import ma.fstg.security.spring_security_jpa.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * Cette classe est responsable de l'initialisation des données de sécurité
 * au démarrage de l'application, notamment la création des rôles et des utilisateurs par défaut.
 */
@Configuration
public class DataSeeder {

    /**
     * Bean CommandLineRunner exécuté au démarrage de l'application.
     * Il vérifie si certains rôles et utilisateurs existent déjà et les crée si nécessaire.
     *
     * @param roleRepository Repository pour gérer les rôles
     * @param userRepository Repository pour gérer les utilisateurs
     * @param passwordEncoder Encodeur de mot de passe sécurisé BCrypt
     * @return CommandLineRunner qui s'exécute au démarrage
     */
    @Bean
    CommandLineRunner loadData(AppRoleRepository roleRepository,
                               AppUserRepository userRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            // --- Création du rôle ADMIN si inexistant ---
            AppRole adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
            if (adminRole == null) {
                // Enregistrement du rôle ADMIN dans la base de données
                adminRole = roleRepository.save(new AppRole(null, "ROLE_ADMIN"));
            }

            // --- Création du rôle USER si inexistant ---
            AppRole userRole = roleRepository.findByRoleName("ROLE_USER");
            if (userRole == null) {
                // Enregistrement du rôle USER dans la base de données
                userRole = roleRepository.save(new AppRole(null, "ROLE_USER"));
            }

            // --- Création de l'utilisateur ADMIN si inexistant ---
            if (userRepository.findByLogin("admin").isEmpty()) {
                AppUser admin = new AppUser(
                        null, // id généré automatiquement
                        "admin", // login
                        passwordEncoder.encode("1234"), // mot de passe encodé
                        true, // utilisateur actif
                        List.of(adminRole, userRole) // assignation des rôles
                );
                userRepository.save(admin); // sauvegarde de l'utilisateur
            }

            // --- Création de l'utilisateur USER standard si inexistant ---
            if (userRepository.findByLogin("user").isEmpty()) {
                AppUser user = new AppUser(
                        null, // id généré automatiquement
                        "user", // login
                        passwordEncoder.encode("1111"), // mot de passe encodé
                        true, // utilisateur actif
                        List.of(userRole) // assignation du rôle USER
                );
                userRepository.save(user); // sauvegarde de l'utilisateur
            }
        };
    }
}