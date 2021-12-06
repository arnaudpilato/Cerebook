package wcs.cerebook.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CerebookUser currentUser;
    @ManyToOne
    private CerebookUser currentFriends;

    @OneToOne()
    private CerebookConfirmationFriend confirmationFriend;

    public CerebookFriend() {
    }

    public CerebookFriend(CerebookUser currentUser, CerebookUser currentFriends) {
        this.currentUser = currentUser;
        this.currentFriends = currentFriends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CerebookUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CerebookUser currentUser) {
        this.currentUser = currentUser;
    }

    public CerebookUser getCurrentFriends() {
        return currentFriends;
    }

    public void setCurrentFriends(CerebookUser currentFriends) {
        this.currentFriends = currentFriends;
    }

    public CerebookConfirmationFriend getConfirmationFriend() {
        return confirmationFriend;
    }

    public void setConfirmationFriend(CerebookConfirmationFriend confirmationFriend) {
        this.confirmationFriend = confirmationFriend;
    }
}
