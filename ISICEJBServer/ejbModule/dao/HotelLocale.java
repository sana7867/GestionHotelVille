package dao;

import java.util.List;

import entities.Hotel;
import jakarta.ejb.Local;
@Local
public interface HotelLocale extends IDaoLocale<Hotel> {
	List<Hotel> findByVille(String nom);
}