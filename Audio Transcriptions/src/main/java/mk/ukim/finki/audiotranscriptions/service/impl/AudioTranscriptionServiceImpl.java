package mk.ukim.finki.audiotranscriptions.service.impl;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import mk.ukim.finki.audiotranscriptions.model.exceptions.AudioNotFoundException;
import mk.ukim.finki.audiotranscriptions.repository.AudioTranscriptionRepository;
import mk.ukim.finki.audiotranscriptions.service.AudioTranscriptionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class AudioTranscriptionServiceImpl implements AudioTranscriptionService {

    private final AudioTranscriptionRepository audioTranscriptionRepository;

    public AudioTranscriptionServiceImpl(AudioTranscriptionRepository audioTranscriptionRepository) {
        this.audioTranscriptionRepository = audioTranscriptionRepository;
    }

    @Override
    @Transactional
    public AudioTranscription getNextTranscription() {

        AudioTranscription audioTranscription = audioTranscriptionRepository.
                findFirstByLockedAndTranscriptionIsNull(false).orElseThrow(AudioNotFoundException::new);

        audioTranscription.setLocked(true);
        audioTranscriptionRepository.save(audioTranscription);

        return audioTranscription;
    }

    @Override
    public AudioTranscription saveTranscription(Long id, String transcription) {

        AudioTranscription audioTranscription =
                audioTranscriptionRepository.findById(id).orElseThrow(AudioNotFoundException::new);

        audioTranscription.setLocked(false);
        audioTranscription.setTranscription(transcription);

        return audioTranscriptionRepository.save(Objects.requireNonNull(audioTranscription));
    }

    @Override
    public void unlockTranscription(Long id) {
        AudioTranscription audioTranscription =
                audioTranscriptionRepository.findById(id).orElseThrow(AudioNotFoundException::new);

        audioTranscription.setLocked(false);
        audioTranscriptionRepository.save(audioTranscription);
    }
}
