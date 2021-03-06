package se.iths.springlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.springlab.entities.Monkey;

import java.util.List;

@Repository
public interface MonkeyRepository extends JpaRepository<Monkey, Integer> {

    List<Monkey> findAllByName(String name);
}
