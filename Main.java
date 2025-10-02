import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Ticket> tickets = new ArrayList<>();
    private static final Admin admin = new Admin(100, "Guilavogui Siba", "GuilavoguiSiba.admin@uqac.ca");

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Créer un utilisateur");
            System.out.println("2. Créer un ticket");
            System.out.println("3. Ajouter une description (texte/pièce jointe) à un ticket");
            System.out.println("4. Voir tous les tickets");
            System.out.println("5. Assigner un ticket à un utilisateur");
            System.out.println("6. Mettre à jour le statut d’un ticket");
            System.out.println("7. Fermer un ticket");
            System.out.println("8. Ajouter un commentaire à un ticket");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choice = readInt();
            switch (choice) {
                case 1 -> creerUtilisateur();
                case 2 -> creerTicket();
                case 3 -> ajouterDescription();
                case 4 -> voirTickets();
                case 5 -> assignerTicket();
                case 6 -> majStatut();
                case 7 -> fermerTicket();
                case 8 -> ajouterCommentaire();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide !");
            }
        } while (choice != 0);
    }

    // ---- Actions de menu

    private static void creerUtilisateur() {
        System.out.print("ID utilisateur : ");
        int id = readInt();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Rôle : ");
        String role = scanner.nextLine();

        User user = new User(id, nom, email, role);
        users.add(user);
        System.out.println("Utilisateur créé : " + user);
    }

    private static void creerTicket() {
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur. Créez d’abord un utilisateur !");
            return;
        }
        System.out.print("ID du ticket : ");
        int id = readInt();
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Priorité (BASSE/MOYENNE/HAUTE) : ");
        Priority prio = Priority.valueOf(scanner.nextLine().trim().toUpperCase());

        Ticket t = new Ticket(id, titre, prio);
        // Le créateur par défaut = premier user de la liste (ou on pourrait demander)
        User createur = users.get(0);
        createur.createTicket(t);
        admin.registerTicket(t);
        tickets.add(t);
        System.out.println("Ticket créé : " + t);
    }

    private static void ajouterDescription() {
        Ticket t = askTicketById();
        if (t == null) return;

        System.out.println("Type de description ? (1=Texte, 2=Image, 3=Vidéo, 4=Fichier)");
        int type = readInt();
        User by = users.isEmpty() ? null : users.get(0); // auteur par défaut

        switch (type) {
            case 1 -> {
                System.out.print("Texte : ");
                String txt = scanner.nextLine();
                t.addTextDescription(txt, by);
            }
            case 2 -> {
                System.out.print("URL/chemin de l’image : ");
                String uri = scanner.nextLine();
                t.addAttachment(DescriptionType.IMAGE, uri, by);
            }
            case 3 -> {
                System.out.print("URL/chemin de la vidéo : ");
                String uri = scanner.nextLine();
                t.addAttachment(DescriptionType.VIDEO, uri, by);
            }
            case 4 -> {
                System.out.print("URL/chemin du fichier : ");
                String uri = scanner.nextLine();
                t.addAttachment(DescriptionType.FICHIER, uri, by);
            }
            default -> System.out.println("Type invalide.");
        }
        System.out.println("Descriptions du ticket #" + t.getTicketID() + " -> " + t.getDescriptions());
    }

    private static void voirTickets() {
        if (tickets.isEmpty()) {
            System.out.println(" Aucun ticket.");
            return;
        }
        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }

    private static void assignerTicket() {
        if (users.isEmpty()) {
            System.out.println(" Aucun utilisateur.");
            return;
        }
        Ticket t = askTicketById();
        if (t == null) return;

        System.out.print("ID utilisateur assigné : ");
        int idUser = readInt();
        User u = users.stream().filter(x -> x.getUserID() == idUser).findFirst().orElse(null);
        if (u == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }
        admin.assignTicket(t, u);
    }

    private static void majStatut() {
        Ticket t = askTicketById();
        if (t == null) return;

        System.out.print("Nouveau statut (OUVERT/ASSIGNE/VALIDATION/TERMINE) : ");
        Status s = Status.valueOf(scanner.nextLine().trim().toUpperCase());
        // on fait “comme si” c’était le premier user qui met à jour
        if (!users.isEmpty()) {
            users.get(0).updateTicket(t, s);
        } else {
            t.updateStatus(s);
        }
    }

    private static void fermerTicket() {
        Ticket t = askTicketById();
        if (t == null) return;
        admin.closeTicket(t);
    }

    private static void ajouterCommentaire() {
        Ticket t = askTicketById();
        if (t == null) return;
        System.out.print("Commentaire : ");
        String c = scanner.nextLine();
        t.addComment(c);
        System.out.println("Commentaires : " + t.getComments());
    }

    // ---- Helpers

    private static Ticket askTicketById() {
        if (tickets.isEmpty()) {
            System.out.println("Aucun ticket.");
            return null;
        }
        System.out.print("ID du ticket : ");
        int idTicket = readInt();
        Ticket t = tickets.stream().filter(x -> x.getTicketID() == idTicket).findFirst().orElse(null);
        if (t == null) System.out.println("Ticket introuvable.");
        return t;
    }

    private static int readInt() {
        while (true) {
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Veuillez entrer un entier : ");
            }
        }
    }
}
