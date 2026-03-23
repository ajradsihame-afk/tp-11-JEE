package ma.fstg.security.spring_security_jpa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contrôleur pour gérer les pages web de l'application.
 * Chaque méthode correspond à une URL et renvoie le nom de la vue Thymeleaf correspondante.
 */
@Controller
public class ViewController {

    /**
     * Affiche la page de connexion personnalisée.
     * URL : /login
     *
     * @return nom de la vue "login.html"
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Affiche la page d'accueil accessible après authentification.
     * URL : /home
     *
     * @return nom de la vue "home.html"
     */
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    /**
     * Affiche le tableau de bord de l'administrateur.
     * URL : /admin/dashboard
     *
     * @return nom de la vue "admin-dashboard.html"
     */
    @GetMapping("/admin/dashboard")
    public String showAdminPage() {
        return "admin-dashboard";
    }

    /**
     * Affiche le tableau de bord de l'utilisateur standard.
     * URL : /user/dashboard
     *
     * @return nom de la vue "user-dashboard.html"
     */
    @GetMapping("/user/dashboard")
    public String showUserPage() {
        return "user-dashboard";
    }

    /**
     * Affiche la page d'accès refusé lorsqu'un utilisateur tente
     * d'accéder à une ressource pour laquelle il n'a pas de droits.
     * URL : /access-denied
     *
     * @return nom de la vue "access-denied.html"
     */
    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}