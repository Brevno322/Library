package ru.library.accounting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.accounting.model.Book;
import ru.library.accounting.model.Person;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBooks(){
        return jdbcTemplate.query("select * from book",new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        jdbcTemplate.update("insert into book(name,author,year)values (?,?,?)",
                book.getName(),book.getAuthor(),book.getYear());
    }
    public Book showBook(int id){
        return jdbcTemplate.query("select * from book where id=?"
                ,new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public List<Book> showBookByIdPerson(int id){
        return jdbcTemplate.query("select * from book where person_id=?"
                        ,new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }


    public void editBook(int id,Book book){
        jdbcTemplate.update("update book set name=?,author=?,year=? where id=?",
                book.getName(),book.getAuthor(),book.getYear(),id);
    }

    public void orderBook(int id,Person person){
jdbcTemplate.update("update book set person_id=? where id=?",person.getId(),id);
    }
    public void returnBook(int id){
        jdbcTemplate.update("update book set person_id=null where id=?",id);
    }

    public void deleteBook(int id){
        jdbcTemplate.update("delete from book where id=?",id);
    }
}
