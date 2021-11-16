package wcs.cerebook.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class CerebookUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
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

    public CerebookUser() {
    }

    public CerebookUser(Long id, String username, String firstName, String lastName, String city, String address, String email, String password, Date birthday, String role, boolean enable, List<CerebookPost> cerebookPosts, List<CerebookMessage> messages, CerebookProfil profil) {
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
        this.messages = messages;
        this.profil = profil;
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
}
    