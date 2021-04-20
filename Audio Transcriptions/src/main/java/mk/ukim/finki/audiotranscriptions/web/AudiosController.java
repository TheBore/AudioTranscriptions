package mk.ukim.finki.audiotranscriptions.web;

import mk.ukim.finki.audiotranscriptions.model.AudioTranscription;
import mk.ukim.finki.audiotranscriptions.service.AudioExcelExporter;
import mk.ukim.finki.audiotranscriptions.service.AudioTranscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class AudiosController {

    private final AudioTranscriptionService audioTranscriptionService;

    public AudiosController(AudioTranscriptionService audioTranscriptionService) {
        this.audioTranscriptionService = audioTranscriptionService;
    }

    @GetMapping
    public String getHomePage(){
        return "home_page";
    }

    @GetMapping("audios")
    public String getAudiosPage(Model model){
        AudioTranscription transcription = audioTranscriptionService.getNextTranscription();
        if(transcription == null)
            return "finished";
        model.addAttribute("transcription",transcription);
        model.addAttribute("bodyContent", "robot");
        return "list_audios";
    }
    @GetMapping("audios/upload_files")
    public String getUploadPage(){
        return "upload_page";
    }

    @PostMapping("audios/save_transcription/{id}")
    public String saveTranscription(@PathVariable Long id, @RequestParam String transcription){
        audioTranscriptionService.saveTranscription(id,transcription);
        return "redirect:/audios";
    }

    @RequestMapping(value = "audios/upload", method = RequestMethod.POST)
    public String save(@RequestParam(value = "files") MultipartFile[] files) {
        audioTranscriptionService.addAudioFiles(files);
        return "redirect:/audios";
    }

    @GetMapping("audios/download_excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=audios_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<AudioTranscription> listAudios = audioTranscriptionService.findAll();

        AudioExcelExporter excelExporter = new AudioExcelExporter(listAudios);

        excelExporter.export(response);
    }
}
