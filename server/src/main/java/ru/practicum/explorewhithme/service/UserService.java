package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.explorewhithme.exception.UserNotFoundException;
import ru.practicum.explorewhithme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User upDate(User user, Long id) {
        User userUpDate = findById(id);
        if (user.getName() != null) {
            userUpDate.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userUpDate.setEmail(user.getEmail());
        }
        return userRepository.save(userUpDate);
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else throw new UserNotFoundException("User id= " + id + " not found");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
