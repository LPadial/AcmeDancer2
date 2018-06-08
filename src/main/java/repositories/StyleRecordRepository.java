
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.StyleRecord;

@Repository
public interface StyleRecordRepository extends JpaRepository<StyleRecord, Integer> {

}
