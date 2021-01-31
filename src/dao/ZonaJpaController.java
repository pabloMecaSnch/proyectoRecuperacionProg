/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ZonaJpaController implements Serializable {

    public ZonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Zona zona) {
        if (zona.getAnimalList() == null) {
            zona.setAnimalList(new ArrayList<Animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Animal> attachedAnimalList = new ArrayList<Animal>();
            for (Animal animalListAnimalToAttach : zona.getAnimalList()) {
                animalListAnimalToAttach = em.getReference(animalListAnimalToAttach.getClass(), animalListAnimalToAttach.getIdAnimal());
                attachedAnimalList.add(animalListAnimalToAttach);
            }
            zona.setAnimalList(attachedAnimalList);
            em.persist(zona);
            for (Animal animalListAnimal : zona.getAnimalList()) {
                Zona oldZonaidZonaOfAnimalListAnimal = animalListAnimal.getZonaidZona();
                animalListAnimal.setZonaidZona(zona);
                animalListAnimal = em.merge(animalListAnimal);
                if (oldZonaidZonaOfAnimalListAnimal != null) {
                    oldZonaidZonaOfAnimalListAnimal.getAnimalList().remove(animalListAnimal);
                    oldZonaidZonaOfAnimalListAnimal = em.merge(oldZonaidZonaOfAnimalListAnimal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Zona zona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zona persistentZona = em.find(Zona.class, zona.getIdZona());
            List<Animal> animalListOld = persistentZona.getAnimalList();
            List<Animal> animalListNew = zona.getAnimalList();
            List<String> illegalOrphanMessages = null;
            for (Animal animalListOldAnimal : animalListOld) {
                if (!animalListNew.contains(animalListOldAnimal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Animal " + animalListOldAnimal + " since its zonaidZona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Animal> attachedAnimalListNew = new ArrayList<Animal>();
            for (Animal animalListNewAnimalToAttach : animalListNew) {
                animalListNewAnimalToAttach = em.getReference(animalListNewAnimalToAttach.getClass(), animalListNewAnimalToAttach.getIdAnimal());
                attachedAnimalListNew.add(animalListNewAnimalToAttach);
            }
            animalListNew = attachedAnimalListNew;
            zona.setAnimalList(animalListNew);
            zona = em.merge(zona);
            for (Animal animalListNewAnimal : animalListNew) {
                if (!animalListOld.contains(animalListNewAnimal)) {
                    Zona oldZonaidZonaOfAnimalListNewAnimal = animalListNewAnimal.getZonaidZona();
                    animalListNewAnimal.setZonaidZona(zona);
                    animalListNewAnimal = em.merge(animalListNewAnimal);
                    if (oldZonaidZonaOfAnimalListNewAnimal != null && !oldZonaidZonaOfAnimalListNewAnimal.equals(zona)) {
                        oldZonaidZonaOfAnimalListNewAnimal.getAnimalList().remove(animalListNewAnimal);
                        oldZonaidZonaOfAnimalListNewAnimal = em.merge(oldZonaidZonaOfAnimalListNewAnimal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zona.getIdZona();
                if (findZona(id) == null) {
                    throw new NonexistentEntityException("The zona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Zona zona;
            try {
                zona = em.getReference(Zona.class, id);
                zona.getIdZona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Animal> animalListOrphanCheck = zona.getAnimalList();
            for (Animal animalListOrphanCheckAnimal : animalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Zona (" + zona + ") cannot be destroyed since the Animal " + animalListOrphanCheckAnimal + " in its animalList field has a non-nullable zonaidZona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(zona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Zona> findZonaEntities() {
        return findZonaEntities(true, -1, -1);
    }

    public List<Zona> findZonaEntities(int maxResults, int firstResult) {
        return findZonaEntities(false, maxResults, firstResult);
    }

    private List<Zona> findZonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Zona.class));
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

    public Zona findZona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Zona.class, id);
        } finally {
            em.close();
        }
    }

    public int getZonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Zona> rt = cq.from(Zona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public Zona fingZonaByName(String nombre){
        EntityManager em = getEntityManager();
        try{
            Query query = em.createNamedQuery("Zona.findByNombreZona");
            query.setParameter("nombreZona", nombre);
            Zona z = (Zona)query.getSingleResult();
            return z;
        }finally{
            em.close();
        }
    }
    
}
