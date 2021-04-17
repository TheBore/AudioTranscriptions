package mk.ukim.finki.audiotranscriptions.service.impl;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import mk.ukim.finki.audiotranscriptions.model.exceptions.AudioNotFoundException;
import mk.ukim.finki.audiotranscriptions.repository.AudioTranscriptionRepository;
import mk.ukim.finki.audiotranscriptions.service.AudioTranscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        try {
            AudioTranscription audioTranscription = audioTranscriptionRepository.
                    findFirstByLockedAndTranscriptionIsNullOrTranscriptionEquals(false, "")
                    .orElseThrow(AudioNotFoundException::new);
            audioTranscription.setLocked(true);
            audioTranscriptionRepository.save(audioTranscription);
            return audioTranscription;
        }
        catch (AudioNotFoundException e) {
            return null;
        }
    }

    @Override
    public AudioTranscription saveTranscription(Long id, String transcription) {
        AudioTranscription audioTranscription =
                audioTranscriptionRepository.findById(id).orElseThrow(AudioNotFoundException::new);
        audioTranscription.setLocked(false);
        if(transcription != null && !transcription.equals(""))
            audioTranscription.setTranscription(transcription);
        return audioTranscriptionRepository.save(Objects.requireNonNull(audioTranscription));
    }

    @Override
    public void addAudioFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                byte[] bytes = file.getBytes();
                // C:\Users\Boris\Documents\GitHub\AudioTranscriptions\Audio Transcriptions\src\main\resources\audios
                String audiosDirectory = Paths.get("")
                        .toAbsolutePath()
                        .toString()
                        .concat("\\src\\main\\resources\\audios\\")
                        .concat(Objects.requireNonNull(file.getOriginalFilename()));
                Files.write(Path.of(audiosDirectory), bytes);
                AudioTranscription audioTranscription = new AudioTranscription();
                audioTranscription.setAudioName(file.getOriginalFilename());
                audioTranscription.setLocked(false);
                audioTranscriptionRepository.save(audioTranscription);
                //return file.getOriginalFilename();
            } catch (IOException e) {
                //return null;
            }
        }
    }

    @Override
    public List<AudioTranscription> findAll() {
        return audioTranscriptionRepository.findAll();
    }
}
