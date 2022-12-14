package online.task.devchallenge.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Message {

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

    //Set of String values that are ID of each Person entity that should receive this message
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> destinations = new HashSet<>();

    public Message(String text, Set<String> topics, String from_person_id, Integer min_trust_level) {
        this.text = text;
        this.topics = topics;
        this.from_person_id = from_person_id;
        this.min_trust_level = min_trust_level;
    }
}
