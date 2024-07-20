package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   @Transactional
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> getUserByCarModelAndSeries(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery("from User u where u.car.model = :model and u.car.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .list();
   }
}
