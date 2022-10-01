package online.task.devchallenge.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "connections")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "dest_person_id")
    Person trust_person;

    @Column(name = "trust_level")
    int trust_level;

    public Connection(Person person, Person trust_person, int trust_level) {
        this.person = person;
        this.trust_person = trust_person;
        this.trust_level = trust_level;
    }
}