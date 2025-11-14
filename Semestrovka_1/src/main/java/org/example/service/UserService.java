package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    public void save(User user) throws SQLException {
        validateSave(user);
        user.setHashPassword(
                bCrypt.encode(user.getHashPassword()));

        userRepository.save(user);
    }

    public void delete(User user) throws SQLException {
        userRepository.delete(user);
    }

    public void update(User user) throws SQLException {
        validateUpdate(user);
        userRepository.update(user);
    }

    public void updateHashPassword(String username, String password) throws SQLException {
        String hashedPassword = bCrypt.encode(password);
        userRepository.updateHashPassword(username, hashedPassword);
    }

    public void updateUsername(String username, String newUsername) throws SQLException {
        if (!USERNAME_PATTERN.matcher(newUsername).matches()) {
            throw new IllegalArgumentException("Имя пользователя может содержать только буквы, цифры и подчеркивание (3-50 символов)");
        }
        if (isUsernameExists(newUsername)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        userRepository.updateUsername(username, newUsername);

    }

    public void updatePhoneNumber(String username, String phoneNumber) throws SQLException {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Некорректный формат номера телефона");
        }

        if (isPhoneNumberExists(phoneNumber)) {
            throw new IllegalArgumentException("Пользователь с таким телефоном уже существует");
        }
        userRepository.updatePhoneNumber(username, phoneNumber);
    }

    public void updateEmail(String username, String email) throws SQLException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Некорректный формат email");
        }

        if (isEmailExists(email)) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }

        userRepository.updateEmail(username, email);
    }

    public List<User> getAll() throws SQLException {
        return userRepository.findAll();
    }

    public List<UserDto> getAllDto() throws SQLException {
        List<UserDto> usersDto = new ArrayList<>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            usersDto.add(convertToUserDto(user));
        }
        return usersDto;
    }

    public UserDto getById(Long id) throws SQLException {
        User userTemp = userRepository.findById(id);
        return convertToUserDto(userTemp);
    }

    public UserDto getByUsername(String username) throws SQLException {
        User userTemp = userRepository.findByUsername(username);
        return convertToUserDto(userTemp);
    }

    public UserDto getByPhoneNumber(String phoneNumber) throws SQLException {
        User userTemp = userRepository.findByPhoneNumber(phoneNumber);
        return convertToUserDto(userTemp);
    }

    public UserDto getByEmail(String email) throws SQLException {
        User userTemp = userRepository.findByEmail(email);
        return convertToUserDto(userTemp);
    }

    public User authenticate(String username, String password) throws SQLException {
        try {
            User user = userRepository.findByUsername(username);
            String HashPassword = userRepository.getHashPassword(username);
            if (bCrypt.matches(password, HashPassword)) {
                return user;
            }
            return null;

        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return null;
            }
            throw e;
        }
    }

    private void validateSave(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }

        if (isUsernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        if (isPhoneNumberExists(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Пользователь с таким телефоном уже существует");
        }

        if (isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя обязательно");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Номер пользователя обязательно");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Почта пользователя обязательно");
        }

        if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
            throw new IllegalArgumentException("Имя пользователя может содержать только буквы, цифры и подчеркивание (3-50 символов)");
        }

        if (!PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("Некорректный формат номера телефона");
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("Некорректный формат email");
        }

        if (user.getHashPassword() == null || user.getHashPassword().length() < 6) {
            throw new IllegalArgumentException("Пароль должен содержать минимум 6 символов");
        }
    }

    private void validateUpdate(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя обязательно");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Номер пользователя обязательно");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Почта пользователя обязательно");
        }

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя обязательно");
        }

        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Фамилия пользователя обязательно");
        }

        if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
            throw new IllegalArgumentException("Имя пользователя может содержать только буквы, цифры и подчеркивание (3-50 символов)");
        }

        if (!PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("Некорректный формат номера телефона");
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("Некорректный формат email");
        }
    }

    public boolean isUsernameExists(String username) throws SQLException {
        try {
            userRepository.findByUsername(username);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }

    public boolean isPhoneNumberExists(String phoneNumber) throws SQLException {
        try {
            userRepository.findByPhoneNumber(phoneNumber);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }

    public boolean isEmailExists(String email) throws SQLException {
        try {
            userRepository.findByEmail(email);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Не найдены данные")) {
                return false;
            }
            throw e;
        }
    }

    private UserDto convertToUserDto(User user) throws SQLException {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setGender(user.getGender());
        userDto.setBirthday(user.getBirthday());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
