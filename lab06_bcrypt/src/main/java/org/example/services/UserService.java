package org.example.services;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    public static UserRepository userRepository = new UserRepository();

    public void addUser(User user) throws Exception {

        if (userRepository.getUserByUserName(user.getUserName()) != null) {
            throw new Exception("Пользователь с таким именем уже существует");
        }
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        user.setHashPassword(
                bCrypt.encode(user.getHashPassword()));

        userRepository.addUser(user);
    }

    public User getUserByUserName(String username) throws Exception {
        return userRepository.getUserByUserName(username);
    }

    public User checkUser(String username, String password) throws Exception {
        String hashPassword = userRepository.getUserPasswordHash(username);

        if (hashPassword == null) return null;

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (!bCrypt.matches(password, hashPassword)) return null;

        User user = getUserByUserName(username);

        return user;
    }
}
