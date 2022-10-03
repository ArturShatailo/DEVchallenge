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

@Data
@Entity
@Table(name = "persons")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Person {

    @Id
    private String id;

    //Set of String values as topics according to which the received message should be filtered on
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> topics = new HashSet<>();

    //Map collection of (ID -> trust level) pairs that show Person entities that is connected and trust-evaluated by this Person
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> connections = new HashMap<>();
}