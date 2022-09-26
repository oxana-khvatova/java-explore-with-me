package ru.practicum.explorewhithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByAuthorId(Long authorId);

    public List<Comment> findByEventId(Long eventId);
}
