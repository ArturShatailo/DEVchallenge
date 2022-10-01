package online.task.devchallenge.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonIgnore
    @JoinTable(
            name = "topic_likes",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics = new HashSet<>();

    //@JsonIgnore
    @OneToMany(mappedBy = "trust_person")
    private Set<Connection> connections = new HashSet<>();

    //@JsonIgnore
    @OneToMany(mappedBy = "person")
    private Set<Connection> connectionsOf = new HashSet<>();


    /*
    private Set<String> topics = new HashSet<>();

    private Set<Connection> connectionsOf = new HashSet<>();
    */
}