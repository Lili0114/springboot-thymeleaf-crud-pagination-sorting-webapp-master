package net.javaguides.springboot.service;

import net.javaguides.springboot.model.UserBook;

import java.util.Date;
import java.util.List;

public interface UserBookService {
    void addUserBook(UserBook userBook, Date readDate);
    List<UserBook> getUserBooksByUserId(int userId);
}

