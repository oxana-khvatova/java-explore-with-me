package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.UserDto;
import ru.practicum.explorewhithme.mapper.UserMapper;
import ru.practicum.explorewhithme.model.User;
import ru.practicum.explorewhithme.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAll() {
        List<User> allUsers = new ArrayList<>(userService.findAll());
        log.info("Пользователей в базе: {}", allUsers.size());
        return userMapper.toUserDtoList(allUsers);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        log.info("Запрошен пользователь id: " + id);
        User user = userService.findById(id);
        return userMapper.toUserDto(user);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userService.save(user);
        log.info("Новый пользователь: " + savedUser);
        return userMapper.toUserDto(savedUser);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id, @Valid @RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User userForUpdate = userService.upDate(user, id);
        log.info("Update user: " + user);
        return userMapper.toUserDto(userForUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        userService.deleteById(id);
    }
}
