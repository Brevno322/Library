package ru.library.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.library.accounting.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("select p from Book b join Person p on b.person.id=p.id  where  b.id in (:id)")
    Optional<Person> findBookByPersonId(@Param(value ="id")int id);
}
