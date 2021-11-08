package wcs.cerebook.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class CerebookMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Date date;
    @ManyToOne(optional = true)
    private CerebookUser currentUser;
    @ManyToMany()
    private List<CerebookUser> UserDestination;


    public CerebookMessage() {
    }

    public CerebookMessage(Long id, String message, Date date, CerebookUser currentUser,
                           List<CerebookUser> userDestination) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
