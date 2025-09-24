import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1) Création d’objets (instances)
        User dev = new User(1, "SIBA", "SIBA@uqac.ca", "Développeur");
        User testeur = new User(2, "Elvis", "Elvis@uqac.ca", "Testeur");
        Admin admin = new Admin(100, "Guilavogui", "Guilavogui.admin@uqac.ca");

        System.out.println(dev);
        System.out.println(testeur);
        System.out.println(admin);
        System.out.println();

        // 2) Création d’un ticket (via User.createTicket(ticket: Ticket): void)
        Ticket t = new Ticket(101, "Bug interface", "Erreur d’affichage sur login", null, "Haute");
        dev.createTicket(t);               // pose OUVERT + date de création
        admin.registerTicket(t);           // l’admin suit le ticket
        dev.viewTicket(t);

        // 3) Admin assigne le ticket à Bob
        admin.assignTicket(t, testeur);
        dev.viewTicket(t);

        // 4) Ajout d’un commentaire et changement d’état
        t.addComment("Analyse en cours par le testeur.");
        testeur.updateTicket(t, "VALIDATION"); // User.updateTicket(ticket, status)
        dev.viewTicket(t);

        // 5) Admin ferme le ticket
        admin.closeTicket(t);
        dev.viewTicket(t);

        // 6) Afficher tous les tickets suivis par l’admin
        System.out.println("\n--- Tickets suivis par l’admin ---");
        List<Ticket> all = admin.viewAllTickets();
        for (Ticket each : all) {
            System.out.println(each);
        }
    }
}
