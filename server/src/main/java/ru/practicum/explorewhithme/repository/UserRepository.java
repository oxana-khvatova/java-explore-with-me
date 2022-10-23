package ru.practicum.explorewhithme.repository;

import ru.practicum.explorewhithme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
