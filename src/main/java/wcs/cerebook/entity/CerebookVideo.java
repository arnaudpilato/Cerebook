package wcs.cerebook.entity;

import javax.persistence.*;

@Entity
public class CerebookVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoPath;

    public CerebookVideo() {
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
}
