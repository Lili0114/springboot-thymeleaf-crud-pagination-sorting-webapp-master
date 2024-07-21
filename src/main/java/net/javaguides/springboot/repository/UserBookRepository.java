package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
    List<UserBook> findByUserId(int userId);
}

