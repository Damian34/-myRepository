package message;

import javax.xml.bind.annotation.XmlRootElement;
import tables.Competition;

@XmlRootElement
public class CompetitionMessage {

     private int id;
     private String dateOfCompetitionBegin;
     private String title;
     private String description;

    public CompetitionMessage() {
    }

    public CompetitionMessage(Competition competition) {
        this.id = competition.getId();
        this.dateOfCompetitionBegin = competition.getDateOfCompetitionBegin();
        this.title = competition.getTitle();
        this.description = competition.getDescription();
    }
    
    public CompetitionMessage(int id, String dateOfCompetitionBegin, String title, String description) {
        this.id = id;
        this.dateOfCompetitionBegin = dateOfCompetitionBegin;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDateOfCompetitionBegin() {
        return dateOfCompetitionBegin;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfCompetitionBegin(String dateOfCompetitionBegin) {
        this.dateOfCompetitionBegin = dateOfCompetitionBegin;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
     
}
