package mk.ukim.finki.audiotranscriptions.service;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AudioTranscriptionService {

    AudioTranscription getNextTranscription();

    AudioTranscription saveTranscription(Long id, String transcription);

    void addAudioFiles(MultipartFile[] multipartFile);

    List<AudioTranscription> findAll();
}
