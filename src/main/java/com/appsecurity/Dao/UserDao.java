package com.appsecurity.Dao;

import com.appsecurity.Entity.User;

import javax.persistence.*;
import java.util.List;

public class UserDao {

    EntityManagerFactory emf;
    EntityManager em;
    Query query;

    public UserDao() {
        this.emf = Persistence.createEntityManagerFactory("AppSecurity");
        this.em = emf.createEntityManager();
    }

    public List<User> getAllUsers() throws Exception, NoResultException {
        return this.em
                .createNamedQuery("User.findAll")
                .getResultList();
    }

    /* Sem SQLInjection, pois ele busca na Entity*/
    public User getUserByLogin(String login) throws Exception, NoResultException {
        return this.em
                .createNamedQuery("User.findByLogin", User.class)
                .setParameter("P_LOGIN", login)
                .getSingleResult();
    }

    /*Com SQLInjection
    public User getUserByLogin(String login) throws Exception, NoResultException {
        return this.em
                .createQuery(String.format("SELECT U FROM USER U WHERE U.login = '%s'", login), User.class)
                .getSingleResult();
    }*/

    public void addUser(User user) throws Exception {
        try {
            em.getTransaction().begin();
        } catch (Exception ex) {
            //Do nothing
        }
        em.persist(user);
        em.getTransaction().commit();
    }
}
