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

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> topics = new HashSet<>();

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> connections = new HashMap<>();
}