package wcs.cerebook.entity;

import org.apache.tomcat.jni.User;

import javax.persistence.*;

@Entity
public class CerebookProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activity;
    private String avatar;
    private boolean isInterestedInWoman;
    private boolean isInterestedInMen;
    private String hometown;
    private String relashionship;

    @OneToOne(mappedBy = "profil")
    private CerebookUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isInterestedInWoman() {
        return isInterestedInWoman;
    }

    public void setInterestedInWoman(boolean interestedInWoman) {
        isInterestedInWoman = interestedInWoman;
    }

    public boolean isInterestedInMen() {
        return isInterestedInMen;
    }

    public void setInterestedInMen(boolean interestedInMen) {
        isInterestedInMen = interestedInMen;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getRelashionship() {
        return relashionship;
    }

    public void setRelashionship(String relashionship) {
        this.relashionship = relashionship;
    }

    public CerebookUser getUser() {
        return user;
    }

    public void setUser(CerebookUser user) {
        this.user = user;
    }
}
