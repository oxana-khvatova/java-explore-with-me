package ru.practicum.explorewhithme.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.CommentDto;
import ru.practicum.explorewhithme.dto.CommentFullDto;
import ru.practicum.explorewhithme.dto.EventDto;
import ru.practicum.explorewhithme.dto.UserDto;
import ru.practicum.explorewhithme.model.Comment;
import ru.practicum.explorewhithme.service.EventService;
import ru.practicum.explorewhithme.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentMapper {

    private final EventService eventService;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getEventId(),
                comment.getAuthorId(),
                comment.getCreated() == null ? LocalDateTime.now() : comment.getCreated());
    }

    public Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setEventId(commentDto.getEventId());
        comment.setAuthorId(commentDto.getAuthorId());
        comment.setCreated(commentDto.getCreated() == null ? LocalDateTime.now() : commentDto.getCreated());
        return comment;
    }

    public CommentFullDto toCommentFullDto(Comment comment) {
        EventDto eventDto = eventMapper.toEventDto(eventService.findById(comment.getEventId()));
        UserDto userDto = userMapper.toUserDto(userService.findById(comment.getAuthorId()));
        return new CommentFullDto(comment.getId(), comment.getText(), eventDto, userDto,
                comment.getCreated() == null ? LocalDateTime.now() : comment.getCreated());
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtoList.add(toCommentDto(comment));
        }
        return commentDtoList;
    }
}
