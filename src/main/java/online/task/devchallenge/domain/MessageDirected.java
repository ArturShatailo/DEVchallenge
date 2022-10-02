package online.task.devchallenge.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "messages_directed")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class MessageDirected {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Set<String> topics = new HashSet<>();

    @Column(name = "from_person_id")
    private String from_person_id;

    @Column(name = "min_trust_level")
    private Integer min_trust_level;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> path;

}
