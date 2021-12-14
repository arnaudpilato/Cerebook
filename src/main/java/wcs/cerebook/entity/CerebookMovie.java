package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class CerebookMovie {
    public static enum Type {
        SimpleMedia,
        ResizedPicture
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String moviePath;
    private CerebookPicture.Type mediaType;
    private boolean amazonS3Hosted;

    // PIL : One To many vers Movie
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userMovies")
    private Set<CerebookUser> actors = new TreeSet<>();

    public CerebookMovie() {
    }

    public CerebookMovie(String mediaType,String moviePath) {
        this(moviePath, CerebookPicture.Type.valueOf(mediaType));
    }

    public CerebookMovie(String moviePath, CerebookPicture.Type mediaType) {
        this.moviePath = moviePath;
        this.mediaType = mediaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }

    public CerebookPicture.Type getMediaType() {
        return mediaType;
    }

    public void setMediaType(CerebookPicture.Type mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isAmazonS3Hosted() {
        return amazonS3Hosted;
    }

    public void setAmazonS3Hosted(boolean amazonS3Hosted) {
        this.amazonS3Hosted = amazonS3Hosted;
    }

    public Set<CerebookUser> getActors() {
        return actors;
    }
}
