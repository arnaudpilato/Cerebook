package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CerebookConfirmationFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean add;
    @OneToOne
    private CerebookUser friendUser;


    public CerebookConfirmationFriend() {
    }

    public CerebookConfirmationFriend(boolean add, CerebookUser friendUserId) {
        this.add = add;
        this.friendUser = friendUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getAdd() {
        return add;
    }

    public CerebookUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(CerebookUser friendUser) {
        this.friendUser = friendUser;
    }

}
