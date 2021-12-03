package wcs.cerebook.entity;

import javax.persistence.*;

@Entity
public class CerebookPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade=CascadeType.ALL
    )
    private CerebookPost cerebookPost;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade=CascadeType.ALL
    )
    private CerebookUser cerebookUser;
    public CerebookPostLike() {
    }

    public CerebookPostLike(Long id, boolean liked, CerebookPost cerebookPost, CerebookUser cerebookUser) {
        this.id = id;
        this.liked = liked;
        this.cerebookPost = cerebookPost;
        this.cerebookUser = cerebookUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public CerebookPost getCerebookPost() {
        return cerebookPost;
    }

    public void setCerebookPost(CerebookPost cerebookPost) {
        this.cerebookPost = cerebookPost;
    }

    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }

    public void setCerebookUser(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }
}
