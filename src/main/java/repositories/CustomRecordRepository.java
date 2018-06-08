
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CustomRecord;

@Repository
public interface CustomRecordRepository extends JpaRepository<CustomRecord, Integer> {

}
