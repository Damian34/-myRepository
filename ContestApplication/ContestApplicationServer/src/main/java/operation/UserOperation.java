package operation;

import hibernate.HibernateUtil;
import java.util.List;
import message.UserSimple;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import tables.User;

public class UserOperation {

    public User getUser(UserSimple user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> list = session.createQuery("From User where login='" + user.getLogin() + "'").list();
        session.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean verifyUserLoginIsAvailable(UserSimple user) {
        return getUser(user) == null ? true : false;
    }

    public String createAccount(UserSimple user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if (verifyUserLoginIsAvailable(user)) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            List<User> list = session.createQuery("From User").list();
            int newId = 1;
            if (list.size() > 0) {
                newId = list.get(list.size() - 1).getId() + 1;
            }
            User newUser = new User(newId, user.getLogin(), user.getPassword());
            session.save(newUser);
            transaction.commit();
            session.close();
            return "1";
        }
        return "0";
    }

    public String getLoggedUser(UserSimple user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> list = session.createQuery("From User where login='" + user.getLogin() + "' and password='" + user.getPassword() + "'").list();
        session.close();
        if (list.size() > 0) {
            User loggedUser = list.get(0);
            return loggedUser.getRole();
        }
        return "";
    }

    public String getRole(UserSimple user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> list = session.createQuery("From User where login='" + user.getLogin() + "' and password='" + user.getPassword() + "'").list();
        session.close();
        if (list.size() > 0) {
            return list.get(0).getRole();
        }
        return "USER";
    }

}
