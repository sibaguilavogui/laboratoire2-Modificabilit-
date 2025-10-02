import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ticket avec statut/priorité en enum + descriptions extensibles.
 */
public class Ticket {
    private int ticketID;
    private String title;

    private Status status;         // OUVERT, ASSIGNE, VALIDATION, TERMINE
    private Priority priority;     // BASSE, MOYENNE, HAUTE

    private Date creationDate;
    private Date updateDate;

    private User assignedUser;

    private final List<Description> descriptions = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();

    public Ticket(int ticketID, String title, Priority priority) {
        this.ticketID = ticketID;
        this.title = title;
        this.priority = priority;
        this.status = null;        // sera fixé à OUVERT par User.createTicket(...)
        this.updateDate = new Date();
    }

    // ---- Description(s)
    public void addTextDescription(String text, User by) {
        descriptions.add(Description.ofText(text, by));
        touch();
    }

    public void addAttachment(DescriptionType type, String uri, User by) {
        descriptions.add(Description.of(type, uri, by));
        touch();
    }

    public List<Description> getDescriptions() {
        return new ArrayList<>(descriptions);
    }

    // ---- Cycle de vie
    public void assignTo(User user) {
        this.assignedUser = user;
        this.status = Status.ASSIGNE;
        touch();
    }

    public void updateStatus(Status status) {
        this.status = status;
        touch();
    }

    public void addComment(String comment) {
        if (comment != null && !comment.isBlank()) {
            comments.add(comment);
            touch();
        }
    }

    // ---- Utilitaires internes
    void touchCreationIfMissing() {
        if (this.creationDate == null) this.creationDate = new Date();
    }

    private void touch() {
        this.updateDate = new Date();
    }

    // ---- Getters
    public int getTicketID() { return ticketID; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public Date getCreationDate() { return creationDate; }
    public Date getUpdateDate() { return updateDate; }
    public User getAssignedUser() { return assignedUser; }
    public List<String> getComments() { return new ArrayList<>(comments); }

    @Override
    public String toString() {
        return "Ticket{ticketID=" + ticketID +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", assignedUser=" + (assignedUser == null ? "none" : assignedUser.getName()) +
                ", descriptions=" + descriptions +
                ", comments=" + comments + '}';
    }
}
