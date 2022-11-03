package pe.edu.upc.aggregates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "profiles")
@Data
public class Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String profilePictureUrl;

    private Long DNI;

    private String gender;

    private Date birthdate;

    //One to one
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = true)
    @JsonIgnore
    private User user;

    private double subscribersPrice;

    private Boolean subsOn;

    private Boolean tipsOn;


    public Long getId() {
        return id;
    }

    public Profile setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public Profile setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }

    public Long getDNI() {
        return DNI;
    }

    public Profile setDNI(Long DNI) {
        this.DNI = DNI;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Profile setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Profile setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Profile setUser(User user) {
        this.user = user;
        return this;
    }

    public double getSubscribersPrice() {
        return subscribersPrice;
    }

    public Profile setSubscribersPrice(double subscribersPrice) {
        this.subscribersPrice = subscribersPrice;
        return this;
    }

    public Boolean getSubsOn() {
        return subsOn;
    }

    public Profile setSubsOn(Boolean subsOn) {
        this.subsOn = subsOn;
        return this;
    }

    public Boolean getTipsOn() {
        return tipsOn;
    }

    public Profile setTipsOn(Boolean tipsOn) {
        this.tipsOn = tipsOn;
        return this;
    }

}
