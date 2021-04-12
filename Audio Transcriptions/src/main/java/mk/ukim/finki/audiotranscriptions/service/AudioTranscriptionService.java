package mk.ukim.finki.audiotranscriptions.service;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;

public interface AudioTranscriptionService {

    AudioTranscription getNextTranscription();

    AudioTranscription saveTranscription(Long id, String transcription);

    void unlockTranscription(Long id);
}
