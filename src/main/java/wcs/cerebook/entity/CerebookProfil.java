package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.tomcat.jni.User;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activity;
    private String avatar;
    private String banner;
    private boolean interestedInWoman;
    private boolean interestedInMen;
    private String hometown;
    private String relashionship;

    @JsonBackReference
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isInterestedInWoman() {
        return interestedInWoman;
    }

    public void setInterestedInWoman(boolean interestedInWoman) {
        this.interestedInWoman = interestedInWoman;
    }

    public boolean isInterestedInMen() {
        return interestedInMen;
    }

    public void setInterestedInMen(boolean interestedInMen) {
        this.interestedInMen = interestedInMen;
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

    @Override
    public String toString() {
        return "CerebookProfil{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", avatar='" + avatar + '\'' +
                ", banner='" + banner + '\'' +
                ", interestedInWoman=" + interestedInWoman +
                ", isInterestedInMen=" + interestedInMen +
                ", hometown='" + hometown + '\'' +
                ", relashionship='" + relashionship + '\'' +
                ", user=" + user +
                '}';
    }
}
