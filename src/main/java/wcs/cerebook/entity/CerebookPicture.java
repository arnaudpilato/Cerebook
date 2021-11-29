package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class CerebookPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String picturePath;

    public CerebookPicture() {

    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CerebookUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public CerebookUser getUser() {
        return user;
    }

    public void setUser(CerebookUser user) {
        this.user = user;
    }
}
