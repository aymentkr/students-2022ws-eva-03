package whz.pti.eva.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface Grade repository.
 */
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    // Optional<Grade> findByChatUser_NicknameAndChatWith(String from, String to);
}