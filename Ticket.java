import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {
    private int ticketID;
    private String title;
    private String description;
    private String status;      // OUVERT, ASSIGNÉ, VALIDATION, TERMINÉ, ...
    private String priority;    // Haute, Moyenne, Basse, ...
    private Date creationDate;
    private Date updateDate;
    private User assignedUser;

    private final List<String> comments = new ArrayList<>();

    public Ticket(int ticketID, String title, String description, String status, String priority) {
        this.ticketID = ticketID;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.updateDate = new Date();
    }

    public void assignTo(User user) {
        this.assignedUser = user;
        this.status = "ASSIGNÉ";
        touch();
    }

    public void updateStatus(String status) {
        this.status = status;
        touch();
    }

    public void addComment(String comment) {
        if (comment != null && !comment.isBlank()) {
            comments.add(comment);
            System.out.println("Commentaire ajouté au ticket #" + ticketID + " : " + comment);
            touch();
        }
    }

    // Utilitaires internes
    private void touch() { this.updateDate = new Date(); }
    // package-private: accessible depuis User du même package (sans mot-clé)
    void touchCreationIfMissing() { if (this.creationDate == null) this.creationDate = new Date(); }

    // Getters
    public int getTicketID() { return ticketID; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getPriority() { return priority; }
    public Date getCreationDate() { return creationDate; }
    public Date getUpdateDate() { return updateDate; }
    public User getAssignedUser() { return assignedUser; }
    public List<String> getComments() { return new ArrayList<>(comments); }

    @Override
    public String toString() {
        return "Ticket{ticketID=" + ticketID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", assignedUser=" + (assignedUser == null ? "none" : assignedUser.getName()) +
                ", comments=" + comments +
                '}';
    }
}
