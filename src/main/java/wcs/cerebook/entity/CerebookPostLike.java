package wcs.cerebook.entity;

import antlr.collections.List;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class CerebookPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "boolean default false")
    private boolean liked;
    private long countdisLike=0;
    private long countLike=0;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade=CascadeType.ALL
    )
    private CerebookPost cerebookPost;

    public CerebookPostLike() {
    }

    public void setCountdisLike(long countdisLike) {
        this.countdisLike = countdisLike;
    }

    public CerebookPostLike(Long id, boolean liked, long countdisLike, long countLike, CerebookPost cerebookPost) {
        this.id = id;
        this.liked = liked;
        this.countdisLike = countdisLike;
        this.countLike = countLike;
        this.cerebookPost = cerebookPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public long getCountdisLike() {
        return countdisLike;
    }

    public void setCountdislike(long countdislike) {
        this.countdisLike = countdislike;
    }

    public long getCountLike() {
        return countLike;
    }

    public void setCountLike(long countLike) {
        this.countLike = countLike;
    }

    public CerebookPost getCerebookPost() {
        return cerebookPost;
    }

    public void setCerebookPost(CerebookPost cerebookPost) {
        this.cerebookPost = cerebookPost;
    }

    @Override
    public String toString() {
        return "CerebookPostLike{" +
                "id=" + id +
                ", liked=" + liked +
                ", countdisLike=" + countdisLike +
                ", countLike=" + countLike +
                '}';
    }
}
