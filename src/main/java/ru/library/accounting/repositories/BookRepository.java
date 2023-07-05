package ru.library.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.library.accounting.models.Book;
import ru.library.accounting.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b where b.person.id in (:id)")
    List<Book> showBookByIdPerson(@Param(value = "id") int id);


}
