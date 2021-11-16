package wcs.cerebook.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CerebookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm ")
    private Date createdAt;
    //many (comment) to one for one post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cerebookPost")
    private CerebookPost cerebookPost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CerebookPost getCerebookPost() {
        return cerebookPost;
    }

    public void setCerebookPost(CerebookPost cerebookPost) {
        this.cerebookPost = cerebookPost;
    }

    public CerebookComment() {
    }

    public CerebookComment(Long id, String comment, Date createdAt, CerebookPost cerebookPost) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.cerebookPost = cerebookPost;
    }
}
