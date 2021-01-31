/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Pablo
 */
public class AnimalJpaController implements Serializable {

    public AnimalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Animal animal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zona zonaidZona = animal.getZonaidZona();
            if (zonaidZona != null) {
                zonaidZona = em.getReference(zonaidZona.getClass(), zonaidZona.getIdZona());
                animal.setZonaidZona(zonaidZona);
            }
            em.persist(animal);
            if (zonaidZona != null) {
                zonaidZona.getAnimalList().add(animal);
                zonaidZona = em.merge(zonaidZona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Animal animal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal persistentAnimal = em.find(Animal.class, animal.getIdAnimal());
            Zona zonaidZonaOld = persistentAnimal.getZonaidZona();
            Zona zonaidZonaNew = animal.getZonaidZona();
            if (zonaidZonaNew != null) {
                zonaidZonaNew = em.getReference(zonaidZonaNew.getClass(), zonaidZonaNew.getIdZona());
                animal.setZonaidZona(zonaidZonaNew);
            }
            animal = em.merge(animal);
            if (zonaidZonaOld != null && !zonaidZonaOld.equals(zonaidZonaNew)) {
                zonaidZonaOld.getAnimalList().remove(animal);
                zonaidZonaOld = em.merge(zonaidZonaOld);
            }
            if (zonaidZonaNew != null && !zonaidZonaNew.equals(zonaidZonaOld)) {
                zonaidZonaNew.getAnimalList().add(animal);
                zonaidZonaNew = em.merge(zonaidZonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = animal.getIdAnimal();
                if (findAnimal(id) == null) {
                    throw new NonexistentEntityException("The animal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal animal;
            try {
                animal = em.getReference(Animal.class, id);
                animal.getIdAnimal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The animal with id " + id + " no longer exists.", enfe);
            }
            Zona zonaidZona = animal.getZonaidZona();
            if (zonaidZona != null) {
                zonaidZona.getAnimalList().remove(animal);
                zonaidZona = em.merge(zonaidZona);
            }
            em.remove(animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Animal> findAnimalEntities() {
        return findAnimalEntities(true, -1, -1);
    }

    public List<Animal> findAnimalEntities(int maxResults, int firstResult) {
        return findAnimalEntities(false, maxResults, firstResult);
    }

    private List<Animal> findAnimalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Animal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Animal findAnimal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnimalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Animal> rt = cq.from(Animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
