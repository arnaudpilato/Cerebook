package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CerebookEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date startEvent;
    private Date endEvent;
    private String address;
    private String city;
    private int phone;
    private String email;
    private String image;
    @Column(length = 65535, columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private CerebookUser cerebookUser;
    @OneToMany(mappedBy = "cerebookEvent", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @OrderBy("id DESC")
    private final List<CerebookComment> comments = new ArrayList<CerebookComment>();
    private boolean roles;
    private Double x;
    private Double y;

    public CerebookEvent() {
    }

    public CerebookEvent(Long id, String title, Date startEvent, Date endEvent, String address, String city, int phone, String email, String image, String description, CerebookUser cerebookUser, boolean roles, Double x, Double y) {
        this.id = id;
        this.title = title;
        this.startEvent = startEvent;
        this.endEvent = endEvent;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.description = description;
        this.cerebookUser = cerebookUser;
        this.roles = roles;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartEvent() {
        return startEvent;
    }

    public void setStartEvent(Date startEvent) {
        this.startEvent = startEvent;
    }

    public Date getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(Date endEvent) {
        this.endEvent = endEvent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }

    public void setCerebookUser(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }

    public List<CerebookComment> getComments() {
        return comments;
    }

    public boolean isRoles() {
        return roles;
    }

    public void setRoles(boolean roles) {
        this.roles = roles;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
