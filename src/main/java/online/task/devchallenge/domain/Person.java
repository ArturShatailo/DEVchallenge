package online.task.devchallenge.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Person {
/*
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

*/

    @Id
    private String id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> topics = new HashSet<>();

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> connections = new HashMap<>();
}