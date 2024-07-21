package net.javaguides.springboot.service;
import net.javaguides.springboot.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void saveBook(Book book);
    Book getBookById(int id);
    void deleteBookById(int id);
    Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    List<Book> getBooksByCategory(int categoryId);

}
