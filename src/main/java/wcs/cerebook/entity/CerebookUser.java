package wcs.cerebook.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class CerebookUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String password;
    private Date birthday;
    @OneToMany(mappedBy = "currentUser")
    private List<CerebookMessage> messages;
    
    public CerebookUser() {
    }

    public CerebookUser(String nickName, String firstName, String lastName, String city, String address, String email, String password, Date birthday) {
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.address = address;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
   
    public List<CerebookMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<CerebookMessage> messages) {
        this.messages = messages;
    }
}
    