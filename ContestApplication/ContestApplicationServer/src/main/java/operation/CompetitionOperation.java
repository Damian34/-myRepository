package operation;

import hibernate.HibernateUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.CompetitionMessage;
import message.UserCompetition;
import message.UserSimple;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import tables.Competition;
import tables.Participation;
import tables.User;

public class CompetitionOperation {

    private UserOperation userOperation = new UserOperation();

    //należy jeszcze sprawdzić czy wszystkie pozostałe konkursy są zakończone przez np inną metode
    public String createCompetition(UserCompetition userCompetition) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            UserSimple user = new UserSimple(userCompetition.getLogin(), userCompetition.getPassword());
            Calendar competitionDate = Calendar.getInstance();
            competitionDate.setTime(dateFormat.parse(userCompetition.getDate()));
            String competitionStr = dateFormat.format(competitionDate.getTime());
            String currentDay = dateFormat.format(Calendar.getInstance().getTime());
            if (userOperation.getRole(user).equals("ADMIN") && canCreateCompetition(competitionDate)
                    && (competitionDate.after(Calendar.getInstance()) || competitionStr.equals(currentDay))) {
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                Transaction transaction = session.getTransaction();
                transaction.begin();
                List<Competition> list = session.createQuery("From Competition").list();
                int newId = 1;
                if (list.size() > 0) {
                    newId = list.get(list.size() - 1).getId() + 1;
                }
                String newDate = dateFormat.format(competitionDate.getTime());
                Competition competition = new Competition(newId, newDate, userCompetition.getTitle(), userCompetition.getDescription());
                session.save(competition);
                transaction.commit();
                session.close();
                return "1";
            }
        } catch (ParseException e) {
            System.out.println("error: " + e);
        }
        return "0";
    }

    public boolean canCreateCompetition(Calendar competitionDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(competitionDate.getTime());
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Competition> list = session.createQuery("From Competition where dateOfCompetitionBegin='" + time + "'").list();
        session.close();
        if (list.size() > 0) {
            return false;
        }
        return true;
    }

    public Competition getToDayCompetition() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(new Date());
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Competition> list = session.createQuery("From Competition where dateOfCompetitionBegin='" + time + "'").list();
        session.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<CompetitionMessage> getAllCompetitions(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Competition> list = session.createQuery("From Competition").list();
        session.close();
        List<CompetitionMessage> allCompetitions = new ArrayList<>();
        list.stream().forEach(competition->{
            allCompetitions.add(new CompetitionMessage(competition));
        });        
        return allCompetitions;
    }

}
