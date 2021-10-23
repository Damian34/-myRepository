package example.registration.controller;

import example.registration.repository.NoteRepository;
import example.registration.table.Note;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepo;

    @GetMapping("/notes")
    public String showNotesByUserId(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Note> listNotes = noteRepo.findNotesByUserId(auth.getName());
        model.addAttribute("listNotes", listNotes);
        return "notes";
    }

    @GetMapping("/add_note")
    public String addNote(Model model) {
        model.addAttribute("note", new Note());
        return "add_note";
    }

    @PostMapping("/create_note")
    public String createNote(Model model, Note note) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        note.setId_user(auth.getName());
        noteRepo.save(note);//when Entity is saving on the same primary key(id) then the row is updating   

        List<Note> listNotes = noteRepo.findNotesByUserId(auth.getName());
        model.addAttribute("listNotes", listNotes);
        return "notes";
    }

    @GetMapping("/remove_note/{id}")
    public String removeNote(Model model, @PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        noteRepo.deleteById(Long.valueOf(id));

        List<Note> listNotes = noteRepo.findNotesByUserId(auth.getName());
        model.addAttribute("listNotes", listNotes);
        return "notes";
    }

    @GetMapping("/edit_note/{id}")
    public String editNote(Model model, @PathVariable String id) {
        Note note = noteRepo.findNoteById(Long.valueOf(id));
        model.addAttribute("note", note);
        return "edit_note";
    }

}
