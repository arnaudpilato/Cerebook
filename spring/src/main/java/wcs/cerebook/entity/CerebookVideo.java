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
public class CerebookVideo {
    public static enum Type {
        SimpleMedia,
        ResizedPicture
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoPath;
    private CerebookVideo.Type mediaType;
    private boolean amazonS3Hosted;

    public CerebookVideo() {
    }

    public CerebookVideo(String videoPath, CerebookVideo.Type mediaType) {
        this.videoPath = videoPath;
        this.mediaType = mediaType;
    }

    public CerebookVideo(String mediaType,String videoPath) {
        this(videoPath, CerebookVideo.Type.valueOf(mediaType));
    }

    public CerebookVideo(String videoPath, CerebookVideo.Type mediaType, boolean amazonS3Hosted) {
        this.videoPath = videoPath;
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

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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
