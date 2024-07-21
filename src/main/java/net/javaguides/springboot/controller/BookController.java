package net.javaguides.springboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.springboot.model.Book;
import net.javaguides.springboot.model.Category;
import net.javaguides.springboot.service.BookService;
import net.javaguides.springboot.service.CategoryService;
import net.javaguides.springboot.service.ExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;


@Controller
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final ExcelService excelService;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService, ExcelService excelService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.excelService = excelService;
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Book> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Book> listBooks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "index_user";
    }

    @GetMapping("/index_user")
    public String userHome(Model model) {
        return findPaginated(1, "title", "asc", model);
    }

    /*
    @GetMapping("/index_admin")
    public String adminHome(Model model) {
        return findPaginatedAdmin(1, "title", "asc", model);
    }*/

    @GetMapping("/getBooks")
    public ResponseEntity<Object> getBooks(){
        if(bookService.getAllBooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new_book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/index_user";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "update_book";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(value = "id") int id) {
        this.bookService.deleteBookById(id);
        return "redirect:/index_user";
    }

    @GetMapping("/assignCategories")
    public String assignCategories(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);

        return "assign_category";
    }

    @PostMapping("/assignCategory/save")
    public String assignCategorySave(@RequestParam int book_id, @RequestParam int category_id, Model model) {
        Book book = bookService.getBookById(book_id);
        Category category = categoryService.getCategoryById(category_id);

        List<Book> books = bookService.getAllBooks();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);

        if (book.getCategories().contains(category)) {
            model.addAttribute("error", "This book is already assigned to the selected category.");
        } else {
            book.getCategories().add(category);
            bookService.saveBook(book);
        }
        return "redirect:/showCategories";
    }

    @GetMapping("/showCategories")
    public String showCategories(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);

        return "show_categories";
    }

    @GetMapping("/booksByCategory")
    public String showBooksByCategory(@RequestParam(name = "categoryId") int categoryId, Model model) {
        String error = "Error retrieving books by category: ";
        try {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            List<Book> books = bookService.getBooksByCategory(categoryId);
            model.addAttribute("books", books);
        } catch (Exception e) {
            model.addAttribute("error", error + e.getMessage());
        }
        return "show_categories";
    }

    @GetMapping("/exportToExcel")
    public void exportToExcel(
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            HttpServletResponse response) throws IOException {

        List<Book> books = bookService.getAllBooks();

        Workbook workbook = excelService.generateExcel(books);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=bookList.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "redirect:/";
    }

}
