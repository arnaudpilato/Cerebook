package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CerebookUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String password;
    private Date birthday;
    private String role;
    private boolean enable;

    @OneToMany(
            mappedBy = "cerebookUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CerebookPost> cerebookPosts ;
    @OneToMany(
            mappedBy = "cerebookUser",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CerebookPostLike> cerebookPostLikes = new ArrayList<>();
    @OneToMany(mappedBy = "currentUser")
    private List<CerebookMessage> messages;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profil_id", referencedColumnName = "id")
    private CerebookProfil profil;
    // relation oneToMany between user and comments
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CerebookComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CerebookPicture> pictures;

    @OneToMany(mappedBy = "user")
    private List<CerebookVideo> videos;

    public List<CerebookPostLike> getCerebookPostLikes() {
        return cerebookPostLikes;
    }

    public void setCerebookPostLikes(List<CerebookPostLike> cerebookPostLikes) {
        this.cerebookPostLikes = cerebookPostLikes;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    private CerebookCartography cartography;

    public CerebookUser() {
    }

    public CerebookUser(Long id, String username, String firstName, String lastName, String city, String address, String email, String password, Date birthday, String role, boolean enable, List<CerebookPost> cerebookPosts, List<CerebookPostLike> cerebookPostLikes, List<CerebookMessage> messages, CerebookProfil profil, List<CerebookComment> comments, List<CerebookPicture> pictures, List<CerebookVideo> videos, CerebookCartography cartography) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.role = role;
        this.enable = enable;
        this.cerebookPosts = cerebookPosts;
        this.cerebookPostLikes = cerebookPostLikes;
        this.messages = messages;
        this.profil = profil;
        this.comments = comments;
        this.pictures = pictures;
        this.videos = videos;
        this.cartography = cartography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<CerebookPost> getCerebookPosts() {
        return cerebookPosts;
    }

    public void setCerebookPosts(List<CerebookPost> cerebookPosts) {
        this.cerebookPosts = cerebookPosts;
    }

    public List<CerebookComment> getComments() {
        return comments;
    }

    public void setComments(List<CerebookComment> comments) {
        this.comments = comments;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<CerebookMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<CerebookMessage> messages) {
        this.messages = messages;
    }

    public CerebookProfil getProfil() {
        return profil;
    }

    public void setProfil(CerebookProfil profil) {
        this.profil = profil;
    }

    public CerebookCartography getCartography() {
        return cartography;
    }

    public void setCartography(CerebookCartography cartography) {
        this.cartography = cartography;
    }

    public List<CerebookPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<CerebookPicture> pictures) {
        this.pictures = pictures;
    }

    public List<CerebookVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<CerebookVideo> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "CerebookUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", role='" + role + '\'' +
                ", enable=" + enable +
                ", messages=" + messages +
                ", profil=" + (profil == null ? "null" : profil.getId()) +
                '}';
    }
}
