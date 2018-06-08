
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("select a.banners from Academy a where a.id=?1")
	List<Banner> listBannerActor(int a);
	
	@Query("select c from Banner c ORDER BY rand()")
	List<Banner> getRandom();

}
