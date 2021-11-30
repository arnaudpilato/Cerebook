package wcs.cerebook.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm ")
    private Date createdAt;
    private String content;
    private boolean privatePost;

    //there I want to save status - liked/disliked;
    @Column(columnDefinition = "boolean default false")
    private boolean liked;
    //fin propriete systeme de like
    // manyToone for post one user can have many post
    @ManyToOne(fetch = FetchType.LAZY)

    private CerebookUser cerebookUser;

    public List<CerebookComment> getComments() {
        return comments;
    }

    // one post to  can have many comment
    @OneToMany(mappedBy = "cerebookPost", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)

    private final List<CerebookComment> comments = new ArrayList<CerebookComment>();
    //oneTomany bidireictionelle one vers  les likes
    @OneToMany(
            mappedBy = "cerebookPost",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    List<CerebookPostLike> cerebookPostLikes = new ArrayList<>();

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public List<CerebookPostLike> getCerebookPostLikes() {
        return cerebookPostLikes;
    }

    public void setCerebookPostLikes(List<CerebookPostLike> cerebookPostLikes) {
        this.cerebookPostLikes = cerebookPostLikes;
    }

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

    public CerebookPost(Long id, Date createdAt, String content, boolean privatePost, boolean liked, CerebookUser cerebookUser, List<CerebookPostLike> cerebookPostLikes) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.privatePost = privatePost;
        this.liked = liked;
        this.cerebookUser = cerebookUser;
        this.cerebookPostLikes = cerebookPostLikes;
    }
}