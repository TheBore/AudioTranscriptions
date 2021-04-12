package mk.ukim.finki.audiotranscriptions.web;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import mk.ukim.finki.audiotranscriptions.service.AudioTranscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/audios","/"})
public class AudiosController {

    private final AudioTranscriptionService audioTranscriptionService;

    public AudiosController(AudioTranscriptionService audioTranscriptionService) {
        this.audioTranscriptionService = audioTranscriptionService;
    }


    @GetMapping
    public String getAudiosPage(Model model){

        AudioTranscription transcription = audioTranscriptionService.getNextTranscription();

        if(transcription == null)
            return "finished";

        model.addAttribute("transcription",transcription);

        return "list_audios";
    }

    @PostMapping("/save_transcription/{id}")
    public String saveTranscription(@PathVariable Long id, @RequestParam String transcription){

        audioTranscriptionService.saveTranscription(id,transcription);

        return "redirect:/audios";
    }

}
