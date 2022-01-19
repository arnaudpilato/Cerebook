package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm ")
    private Date createdAt;
    //many (comment) to one for one post
    @ManyToOne(fetch = FetchType.LAZY)
    private CerebookPost cerebookPost;
    @ManyToOne(fetch = FetchType.LAZY)
    private CerebookEvent cerebookEvent;
    //many (comment)  for one user
    @ManyToOne(fetch = FetchType.LAZY)
    private CerebookUser cerebookUser;

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

    public CerebookEvent getCerebookEvent() {
        return cerebookEvent;
    }

    public void setCerebookEvent(CerebookEvent cerebookEvent) {
        this.cerebookEvent = cerebookEvent;
    }

    public CerebookComment(Long id, String comment, Date createdAt, CerebookPost cerebookPost) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.cerebookPost = cerebookPost;
    }
}
