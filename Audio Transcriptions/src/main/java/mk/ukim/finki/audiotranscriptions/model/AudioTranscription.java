package mk.ukim.finki.audiotranscriptions.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AudioTranscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String audioName;

    private String transcription;

    private Boolean locked;

}
