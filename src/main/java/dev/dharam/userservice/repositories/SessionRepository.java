package dev.dharam.userservice.repositories;

import dev.dharam.userservice.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {


    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}
