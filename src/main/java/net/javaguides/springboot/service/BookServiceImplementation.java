package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Book;
import net.javaguides.springboot.model.Category;
import net.javaguides.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImplementation implements BookService{

    private final BookRepository bookRepo;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepo = bookRepository;
        this.categoryService = categoryService;
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public void saveBook(Book book) {
        this.bookRepo.save(book);
    }

    @Override
    public Book getBookById(int id) {
        Optional< Book > optional = bookRepo.findById(id);
        Book book = null;
        if (optional.isPresent()) {
            book = optional.get();
        } else {
            throw new RuntimeException(" Book not found for id :: " + id);
        }
        return book;
    }

    @Override
    public void deleteBookById(int id) {
        this.bookRepo.deleteById(id);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.bookRepo.findAll(pageable);
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ArrayList<>(category.getBooks());
    }
}
