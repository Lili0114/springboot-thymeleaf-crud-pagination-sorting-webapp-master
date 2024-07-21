package net.javaguides.springboot.service;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.model.User;

public interface UserService {
    void saveUser(User user);
    User findUserByUsername(String username);

}
