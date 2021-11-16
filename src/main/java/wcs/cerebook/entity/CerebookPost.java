package wcs.cerebook.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Entity
public class CerebookPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm ")
    private Date createdAt;
    private String content;
    private boolean privatePost;
    // manyToone for post one user can have many post
    @ManyToOne(fetch = FetchType.LAZY)

    private CerebookUser cerebookUser;
    // one post to  can have many comment
    @OneToMany(mappedBy = "cerebookPost", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //private CerebookPost cerebookPost;
    private List<CerebookComment> comments;
    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }

    public void setCerebookUser(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }

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
        return privatePost;
    }

    public void setPrivatePost(boolean privatePost) {
        this.privatePost = privatePost;
    }

    public CerebookPost() {
    }

    public CerebookPost(Long id, Date createdAt, String content, boolean privatePost, CerebookUser cerebookUser) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.privatePost = privatePost;
        this.cerebookUser = cerebookUser;
    }
}