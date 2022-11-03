package pe.edu.upc.valueobject;

public class UserInformation {
    private Long id;
    private String email;

    public Long getId() {
        return id;
    }

    public UserInformation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInformation setEmail(String email) {
        this.email = email;
        return this;
    }
}
