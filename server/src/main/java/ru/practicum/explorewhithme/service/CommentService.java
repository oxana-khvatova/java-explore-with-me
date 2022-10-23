package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.exception.AccessException;
import ru.practicum.explorewhithme.exception.CommentNotFoundException;
import ru.practicum.explorewhithme.model.Comment;
import ru.practicum.explorewhithme.model.Event;
import ru.practicum.explorewhithme.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository commentRepository;
    private final EventService eventService;

    public Comment save(Comment comment) {
        Event event = eventService.findById(comment.getEventId());
        if (Objects.equals(event.getInitiatorId(), comment.getAuthorId())) {
            throw new AccessException("Писать комментарий на своё событие не возможно");
        }
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment, Long userId) {
        if (!Objects.equals(comment.getEventId(), userId)) {
            throw new AccessException("Исправить чужой комментарий не возможно");
        }
        Comment updateComment = findById(comment.getId());
        if (comment.getEventId() != null) {
            updateComment.setEventId(comment.getEventId());
        }
        if (comment.getText() != null) {
            updateComment.setText(comment.getText());
        }
        return commentRepository.save(updateComment);
    }

    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else throw new CommentNotFoundException("Comment id= " + id + " not found");
    }

    public List<Comment> getCommentForAuthor(Long authId) {
        List<Comment> comments = commentRepository.findByAuthorId(authId);
        return comments.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated())).collect(Collectors.toList());
    }

    public List<Comment> getCommentForEvent(Long eventId) {
        List<Comment> comments = commentRepository.findByEventId(eventId);
        return comments.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated())).collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        commentRepository.delete(findById(commentId));
    }
}
