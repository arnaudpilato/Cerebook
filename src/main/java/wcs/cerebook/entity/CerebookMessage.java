package wcs.cerebook.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CerebookMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime date;
    @ManyToOne(optional = true)
    private CerebookUser currentUser;
    @ManyToMany()
    private List<CerebookUser> UserDestination;


    public CerebookMessage() {
    }

    public CerebookMessage(String message, LocalDateTime date, CerebookUser currentUser,
                           List<CerebookUser> userDestination) {
        this.message = message;
        this.date = date;
        this.currentUser = currentUser;
        UserDestination = userDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CerebookUser> getUserDestination() {
        return UserDestination;
    }

    public void setUserDestination(List<CerebookUser> userDestination) {
        UserDestination = userDestination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CerebookUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CerebookUser currentUser) {
        this.currentUser = currentUser;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
