package online.task.devchallenge.repository;

import online.task.devchallenge.domain.MessageDirected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDirectedRepository extends JpaRepository<MessageDirected, Integer> {

}
