package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Derimek;


public interface DerimekRepository extends JpaRepository<Derimek, Integer>{
	
	@Query("select count(c.derimek)/(select count(co) from Course co) from Course c where c.derimek!=null")
	Double ratioderimek();

}
