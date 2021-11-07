package wcs.cerebook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CerebookPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private String content;
    private boolean isPrivatePost;
    @OneToMany(mappedBy ="post")
    private List<CerebookUser> cerebookUsers = new ArrayList<CerebookUser>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
@Temporal(TemporalType.TIME)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPrivatePost() {
        return isPrivatePost;
    }

    public void setPrivatePost(boolean privatePost) {
        isPrivatePost = privatePost;
    }

    public CerebookPost() {
    }

    public CerebookPost(Long id, Date createdAt, String content, boolean isPrivatePost, List<CerebookUser> cerebookUsers) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.isPrivatePost = isPrivatePost;
        this.cerebookUsers = cerebookUsers;
    }
}
