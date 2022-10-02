package online.task.devchallenge.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "messages_directed")
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class MessageDirected {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    //Set of String values as topics according to which the receivers should be filtered on
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> topics = new HashSet<>();

    @Column(name = "from_person_id")
    private String from_person_id;

    //Minimum of trust level needed to send this message
    @Column(name = "min_trust_level")
    private Integer min_trust_level;

    //List of String values that as IDs of Person entities that have be participating in message delivery chain
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> path;

}
