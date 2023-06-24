package ru.library.accounting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.accounting.model.Book;
import ru.library.accounting.model.Person;


import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAllPerson() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPersonId(int id) {
        return jdbcTemplate.query("select * from person where id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Optional<Person> showPersonByIdBook(int id_book){
        return  jdbcTemplate.query("select person.* from Book join person on Book.person_id=person.id  where  book.id=?",
                new Object[]{id_book},new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person (full_name,year_of_birth) values (?,?)", person.getFullName(), person.getYearOfBirth());
    }

    public void updatePerson(int id, Person updatePerson) {
        jdbcTemplate.update("update person set full_name=?,year_of_birth=? where id=?",
                updatePerson.getFullName(), updatePerson.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from person where id=?",id);
    }
}
