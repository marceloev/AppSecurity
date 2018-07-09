package com.appsecurity.Dao;

import com.appsecurity.Entity.Anexo;

import javax.persistence.*;
import java.util.List;

public class AnexoDao {

    EntityManagerFactory emf;
    EntityManager em;
    Query query;

    public AnexoDao() {
        this.emf = Persistence.createEntityManagerFactory("AppSecurity");
        this.em = emf.createEntityManager();
    }

    public List<Anexo> getAllAnexosFromUser(int coduser) throws Exception, NoResultException {
        query = em.createNamedQuery("Anexo.findByUser");
        query.setParameter("P_CODUSU", coduser);
        return query.getResultList();
    }

    public void removeAnexoByID(Anexo anexo) throws Exception {
        em.getTransaction().begin();
        em.remove(anexo);
        em.getTransaction().commit();
    }

    public void editAnexo(Anexo anexo) throws Exception {
        try {
            em.getTransaction().begin();
        } catch (Exception ex) {
            //Do nothing
        }
        if (anexo.getCodigo() == 0) {
            em.merge(anexo);
        } else {
            em.persist(anexo);
        }
        em.getTransaction().commit();
    }
}
