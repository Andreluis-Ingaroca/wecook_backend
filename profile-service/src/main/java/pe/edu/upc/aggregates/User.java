package pe.edu.upc.aggregates;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="users")
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;



    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }


}
