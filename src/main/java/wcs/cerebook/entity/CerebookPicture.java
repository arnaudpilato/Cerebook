package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.persistence.metamodel.Type;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CerebookPicture {
    public static enum Type {
        SimpleMedia,
        ResizedPicture
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String picturePath;
    private Type mediaType;
    private boolean amazonS3Hosted;

    public CerebookPicture() {
    }

    public CerebookPicture(String picturePath, Type mediaType) {
        this.picturePath = picturePath;
        this.mediaType = mediaType;
    }

    public CerebookPicture(String mediaType,String picturePath) {
        this(picturePath, Type.valueOf(mediaType));
    }

    public CerebookPicture(String picturePath, Type mediaType, boolean amazonS3Hosted) {
        this.picturePath = picturePath;
        this.mediaType = mediaType;
        this.amazonS3Hosted = amazonS3Hosted;
    }

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
}
