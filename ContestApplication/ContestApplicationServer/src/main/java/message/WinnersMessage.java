package message;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WinnersMessage {

    private int competitionId;
    private String winnerLogin1;
    private String winnerLogin2;
    private String winnerLogin3;
    private UserSimple user = null;
    private String dateOfCompetition = null;

    public WinnersMessage() {
    }

    public WinnersMessage(int competitionId, String winnerLogin1, String winnerLogin2, String winnerLogin3) {
        this.competitionId = competitionId;
        this.winnerLogin1 = winnerLogin1;
        this.winnerLogin2 = winnerLogin2;
        this.winnerLogin3 = winnerLogin3;
    }

    public WinnersMessage(int competitionId, String winnerLogin1, String winnerLogin2, String winnerLogin3, UserSimple user) {
        this.competitionId = competitionId;
        this.winnerLogin1 = winnerLogin1;
        this.winnerLogin2 = winnerLogin2;
        this.winnerLogin3 = winnerLogin3;
        this.user = user;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public String getWinnerLogin1() {
        return winnerLogin1;
    }

    public String getWinnerLogin2() {
        return winnerLogin2;
    }

    public String getWinnerLogin3() {
        return winnerLogin3;
    }

    public UserSimple getUser() {
        return user;
    }

    public String getDateOfCompetition() {
        return dateOfCompetition;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public void setWinnerLogin1(String winnerLogin1) {
        this.winnerLogin1 = winnerLogin1;
    }

    public void setWinnerLogin2(String winnerLogin2) {
        this.winnerLogin2 = winnerLogin2;
    }

    public void setWinnerLogin3(String winnerLogin3) {
        this.winnerLogin3 = winnerLogin3;
    }

    public void setUser(UserSimple user) {
        this.user = user;
    }

    public void setDateOfCompetition(String dateOfCompetition) {
        this.dateOfCompetition = dateOfCompetition;
    }
        
}
