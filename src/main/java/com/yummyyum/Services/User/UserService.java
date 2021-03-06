package com.yummyyum.Services.User;

import com.yummyyum.Model.Email;
import com.yummyyum.Model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    Boolean existsUserByUsername(String username);

    User createNewUser(String firstName, String lastName,
                       String username, String password,
                       Timestamp signUpDate, Email email);

    User updateUser(String newPassword, String email);

}
