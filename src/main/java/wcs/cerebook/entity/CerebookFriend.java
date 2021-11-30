package wcs.cerebook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CerebookFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isFriend;
    @ManyToOne
    private CerebookUser currentUser;
    @ManyToOne
    private CerebookUser currentFriends;

    public CerebookFriend() {
    }

    public CerebookFriend(boolean isFriend, CerebookUser currentUser, CerebookUser currentFriends) {
        this.isFriend = isFriend;
        this.currentUser = currentUser;
        this.currentFriends = currentFriends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
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
}
