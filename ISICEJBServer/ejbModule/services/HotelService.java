package services;

import java.util.List;

import dao.HotelLocale;
import dao.IDaoRemote;
import entities.Hotel;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class HotelService implements IDaoRemote<Hotel>, HotelLocale {

    @PersistenceContext
    private EntityManager em;

    @Override
    @PermitAll
    public Hotel create(Hotel o) {
        em.persist(o);
        return o;
    }

    @Override
    @PermitAll
    public boolean delete(Hotel o) {
        try {
            em.remove(em.contains(o) ? o : em.merge(o));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @PermitAll
    public Hotel update(Hotel o) {
        try {
            return em.merge(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @PermitAll
    public Hotel findById(int id) {
        return em.find(Hotel.class, id);
    }

    @Override
    @PermitAll
    public List<Hotel> findAll() {
        try {
            Query query = em.createQuery("SELECT h FROM Hotel h");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	@PermitAll
	public List<Hotel> findByVille(String nom) {
	    List<Hotel> hotels = em
	        .createQuery("SELECT h FROM Hotel h WHERE h.ville.nom = :nom", Hotel.class)
	        .setParameter("nom", nom)
	        .getResultList();
	    return hotels;
	}

}