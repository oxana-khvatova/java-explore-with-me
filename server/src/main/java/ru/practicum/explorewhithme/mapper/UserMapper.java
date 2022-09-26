package ru.practicum.explorewhithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.UserDto;
import ru.practicum.explorewhithme.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> listDto = new ArrayList<UserDto>();
        if (users.size() == 0) {
            return listDto;
        }
        for (User user : users) {
            UserDto dto = toUserDto(user);
            listDto.add(dto);
        }
        return listDto;
    }

    public User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
