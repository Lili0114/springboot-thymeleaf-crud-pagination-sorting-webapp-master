package net.javaguides.springboot.service;

import net.javaguides.springboot.model.UserBook;
import net.javaguides.springboot.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserBookServiceImplementation implements UserBookService {

    private final UserBookRepository userBookRepository;

    @Autowired
    public UserBookServiceImplementation(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    @Override
    public void addUserBook(UserBook userBook, Date readDate) {
        userBook.setReadDate(readDate);
        userBookRepository.save(userBook);
    }

    @Override
    public List<UserBook> getUserBooksByUserId(int userId) {
        return userBookRepository.findByUserId(userId);
    }
}
