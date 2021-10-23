package example.registration.repository;

import example.registration.table.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.id_user = ?1")
    public List<Note> findNotesByUserId(String id);

    @Query("SELECT n FROM Note n WHERE n.id = ?1")
    public Note findNoteById(long id);
}
