package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible for the connection to
 * execute all user department procedures in the database.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    /**
     * Gets reports from the database based on the users department
     * @param dep the users department
     * @return List of Reports as String
     */
    @Query(value = "Call SelectAll('Map',:dep,'','','');;", nativeQuery = true)
    List<String> getMapMarkers(@Param(value = "dep") String dep);

    /**
     * Gets reports from the database based on the product name and users department
     * @param dep the users department
     * @param name the product name
     * @return List of Reports as String
     */
    @Query(value = "Call SelectAll('ProductMap', :dep,'',:name,'');;", nativeQuery = true)
    List<String> getMapMarkersOnName(@Param(value = "dep") String dep, @Param(value = "name") String name);
}
