package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.CommentDto;
import ru.practicum.explorewhithme.dto.CommentFullDto;
import ru.practicum.explorewhithme.mapper.CommentMapper;
import ru.practicum.explorewhithme.model.Comment;
import ru.practicum.explorewhithme.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @PostMapping("/comments")// Добавить комментарий на своё событие нельзя
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentMapper.toComment(commentDto);
        Comment savedComment = commentService.save(comment);
        log.info("Новый комментарий: " + savedComment);
        return commentMapper.toCommentDto(savedComment);
    }

    @PatchMapping("/comments/{userId}")//Исправить комментарий может только автор комментария
    public CommentDto updateComment(@Valid @RequestBody CommentDto commentDto, @PathVariable long userId) {
        Comment comment = commentMapper.toComment(commentDto);
        Comment savedComment = commentService.update(comment, userId);
        log.info("Комментарий обновлён : " + savedComment);
        return commentMapper.toCommentDto(savedComment);
    }

    @GetMapping("/comments/{commId}") //Получить полную информацию о комментарии
    public CommentFullDto getFullComment(@PathVariable long commId) {
        Comment comment = commentService.findById(commId);
        return commentMapper.toCommentFullDto(comment);
    }

    @GetMapping("/comments/author/{authId}") //Получить все комментарии автора отсортированные по дате создания
    public List<CommentDto> getCommentForUser(@PathVariable long authId) {
        List<Comment> comments = commentService.getCommentForAuthor(authId);
        return commentMapper.toCommentDtoList(comments);
    }

    @GetMapping("/comments/event/{eventId}") // Получить все комментарии события отсортированные по дате создания
    public List<CommentDto> getCommentForEvent(@PathVariable long eventId) {
        List<Comment> comments = commentService.getCommentForEvent(eventId);
        return commentMapper.toCommentDtoList(comments);
    }

    @DeleteMapping("/comments/{commId}")
    public void deleteComment(@PathVariable long commId) {
        commentService.deleteComment(commId);
        log.info("Комментарий id " + commId + " удалён");
    }


}
