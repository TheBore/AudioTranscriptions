package mk.ukim.finki.audiotranscriptions.repository;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioTranscriptionRepository extends JpaRepository<AudioTranscription, Long> {

    Optional<AudioTranscription> findFirstByLockedAndTranscriptionIsNull(Boolean locked);
}
