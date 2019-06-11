package operation;

import hibernate.HibernateUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import message.Message;
import message.UserSimple;
import message.WinnersMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import tables.Competition;
import tables.Participation;
import tables.User;

public class ParticipationOperation {

    private CompetitionOperation competitionOperation = new CompetitionOperation();
    private UserOperation userOperation = new UserOperation();

    public String takePart(UserSimple userSimple) {
        UserOperation userOperation = new UserOperation();
        User user = userOperation.getUser(userSimple);
        Competition competition = competitionOperation.getToDayCompetition();
        if (user != null && competition != null) {
            //nalerzy sprawdzić czy użytkownik nie wzioł udziału
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            String query = "From Participation where User_Id=" + user.getId() + " and Competition_Id=" + competition.getId();
            List<Participation> participations = session.createQuery(query).list();
            if (participations.size() > 0) {
                session.close();
                return "2";//uzytkownik nie moze wziość udziału bo już uczesniczy
            }
            Transaction transaction = session.getTransaction();
            transaction.begin();
            List<Participation> listOfAll = session.createQuery("From Participation").list();
            int newId = 1;
            if (listOfAll.size() > 0) {
                newId = listOfAll.get(listOfAll.size() - 1).getId() + 1;
            }
            Participation part = new Participation(newId, competition, user);
            session.save(part);
            transaction.commit();
            session.close();
            return "1";//uzytkownik moze wziość udziału
        }
        return "0";//uzytkownik nie moze wziość udziału bo nei ma konkursu
    }

    public List<Message> getUsersParticipation(String idCompetition) {
        List<Message> logins = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String query = "From Participation where Competition_Id=" + idCompetition;
        List<Participation> participations = session.createQuery(query).list();
        participations.stream().forEach(part -> {
            logins.add(new Message(part.getUser().getLogin()));
        });
        session.close();
        return logins;
    }

    public String selectWinners(WinnersMessage winners) {
        UserSimple admin = winners.getUser();
        if (userOperation.getRole(admin).equals("ADMIN")) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            String query0 = "From Competition where id=" + winners.getCompetitionId();
            List<Competition> com = session.createQuery(query0).list();
            System.out.println("i get-size: " + com.size());
            if (com.size() > 0) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar competitionDate = Calendar.getInstance();
                    competitionDate.setTime(dateFormat.parse(com.get(0).getDateOfCompetitionBegin()));
                    //dzis after dzien=dzis -> false
                    //dzis after dzien=wczoraj -> true
                    System.out.println("i get-boolean: " + competitionDate.after(Calendar.getInstance()));
                    if (Calendar.getInstance().before(competitionDate)
                            || com.get(0).getDateOfCompetitionBegin().equals(dateFormat.format(Calendar.getInstance().getTime()))) {
                        return "2";//konkurs jeszcze sie nie skończył
                    }
                } catch (ParseException e) {
                    System.out.println("selectWinners-error: " + e);
                }
            }
            ///
            String query = "From Participation where Competition_Id=" + winners.getCompetitionId();
            List<Participation> participations = session.createQuery(query).list();
            int z1 = 1, z2 = 1, z3 = 1;
            if (!winners.getWinnerLogin1().equals("")) {
                z1 = participations.stream()
                        .filter(part -> part.getUser().getLogin().equals(winners.getWinnerLogin1()))
                        .collect(Collectors.toList()).size();
            }
            if (!winners.getWinnerLogin2().equals("")) {
                z2 = participations.stream()
                        .filter(part -> part.getUser().getLogin().equals(winners.getWinnerLogin2()))
                        .collect(Collectors.toList()).size();
            }
            if (!winners.getWinnerLogin3().equals("")) {
                z3 = participations.stream()
                        .filter(part -> part.getUser().getLogin().equals(winners.getWinnerLogin3()))
                        .collect(Collectors.toList()).size();
            }
            if (z1 + z2 + z3 == 3) {
                participations.stream().forEach(part -> {
                    part.setPosition(0);
                });
                for (int i = 0; i < participations.size(); i++) {
                    String login = participations.get(i).getUser().getLogin();
                    if (login.equals(winners.getWinnerLogin1())) {
                        participations.get(i).setPosition(1);
                    }
                    if (login.equals(winners.getWinnerLogin2())) {
                        participations.get(i).setPosition(2);
                    }
                    if (login.equals(winners.getWinnerLogin3())) {
                        participations.get(i).setPosition(3);
                    }
                }
                Transaction transaction = session.getTransaction();
                transaction.begin();
                participations.stream().forEach(part -> {
                    session.update(part);
                });
                transaction.commit();
                session.close();
                return "1";//udalo się
            }
            session.close();
        }
        return "0";//nie udalo sie
    }

    public WinnersMessage getCurrentWinners(String idCompetition, Session session) {
        WinnersMessage message = new WinnersMessage(0, "", "", "");
        try {
            message.setCompetitionId(Integer.valueOf(idCompetition));
        } catch (Exception e) {
            return message;
        }
        String query = "From Participation where Competition_Id=" + idCompetition;
        List<Participation> participations = session.createQuery(query).list();
        for (int i = 0; i < participations.size(); i++) {
            int position = participations.get(i).getPosition();
            switch (position) {
                case 1:
                    message.setWinnerLogin1(participations.get(i).getUser().getLogin());
                    break;
                case 2:
                    message.setWinnerLogin2(participations.get(i).getUser().getLogin());
                    break;
                case 3:
                    message.setWinnerLogin3(participations.get(i).getUser().getLogin());
                    break;
            }
        }
        return message;
    }

    public WinnersMessage getCurrentWinners(String idCompetition) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        WinnersMessage message = getCurrentWinners(idCompetition, session);
        session.close();
        return message;
    }

    public List<WinnersMessage> getAllWinners() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<WinnersMessage> allWinners = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Competition> competitions = session.createQuery("From Competition").list();
        Calendar today = Calendar.getInstance();
        competitions.stream().forEach(com -> {
            try {
                Calendar comDay = Calendar.getInstance();
                comDay.setTime(dateFormat.parse(com.getDateOfCompetitionBegin()));
                if (comDay.before(today) && !dateFormat.format(today.getTime()).equals(com.getDateOfCompetitionBegin())) {
                    //System.out.println("hi2: " + com.getDateOfCompetitionBegin());
                    WinnersMessage ms = getCurrentWinners(com.getId() + "", session);
                    ms.setDateOfCompetition(com.getDateOfCompetitionBegin());
                    allWinners.add(ms);
                }
            } catch (Exception e) {
            }
        });
        session.close();
        allWinners.sort(new WinnersComparator());

        return allWinners;
    }

    private class WinnersComparator implements Comparator<WinnersMessage> {

        private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public int compare(WinnersMessage w1, WinnersMessage w2) {
            Calendar day1 = Calendar.getInstance();
            Calendar day2 = Calendar.getInstance();
            try {
                day1.setTime(dateFormat.parse(w1.getDateOfCompetition()));
                day2.setTime(dateFormat.parse(w2.getDateOfCompetition()));
            } catch (Exception e) {
            }
            return day1.after(day2) ? -1 : w1.getDateOfCompetition().equals(w2.getDateOfCompetition()) ? 0 : 1;
        }
    }
}
