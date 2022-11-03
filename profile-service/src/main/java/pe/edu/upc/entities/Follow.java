package pe.edu.upc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pe.edu.upc.aggregates.Profile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "follows")
@Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Date followDate;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "profile_chef_id",nullable = false)
    @JsonIgnore
    private Profile chef;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "profile_reader_id",nullable = false)
    @JsonIgnore
    private Profile reader;

    public Long getId() {
        return id;
    }

    public Follow setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public Follow setFollowDate(Date followDate) {
        this.followDate = followDate;
        return this;
    }

    public Profile getChef() {
        return chef;
    }

    public Follow setChef(Profile chef) {
        this.chef = chef;
        return this;
    }

    public Profile getReader() {
        return reader;
    }

    public Follow setReader(Profile reader) {
        this.reader = reader;
        return this;
    }
}
