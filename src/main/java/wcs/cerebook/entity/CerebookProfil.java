package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookProfil {
    public static enum Type {
        SimpleMedia,
        ResizedPicture
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activity;
    private String avatar;
    private String ornament;
    private String banner;
    private boolean interestedInWoman;
    private boolean interestedInMen;
    private String hometown;
    private String relashionship;
    private Type mediaType;
    private boolean amazonS3Hosted;

    @OneToOne(mappedBy = "profil")
    private CerebookUser user;

    public CerebookProfil() {
    }

    public CerebookProfil(String activity, String avatar, String ornament, String banner, boolean interestedInWoman, boolean interestedInMen, String hometown, String relashionship, Type mediaType, CerebookUser user) {
        this.activity = activity;
        this.avatar = avatar;
        this.ornament = ornament;
        this.banner = banner;
        this.interestedInWoman = interestedInWoman;
        this.interestedInMen = interestedInMen;
        this.hometown = hometown;
        this.relashionship = relashionship;
        this.mediaType = mediaType;
        this.user = user;
    }

    public CerebookProfil(String avatar, String banner, Type mediaType) {
        this.avatar = avatar;
        this.banner = banner;
        this.mediaType = mediaType;
    }

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

    public String getOrnament() {
        return ornament;
    }

    public void setOrnament(String ornament) {
        this.ornament = ornament;
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

    public Type getMediaType() {
        return mediaType;
    }

    public void setMediaType(Type mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isAmazonS3Hosted() {
        return amazonS3Hosted;
    }

    public void setAmazonS3Hosted(boolean amazonS3Hosted) {
        this.amazonS3Hosted = amazonS3Hosted;
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
                ", user=" + (user == null ? "null" : user.getId()) +
                '}';
    }
}
