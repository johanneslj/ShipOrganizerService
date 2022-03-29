package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "EXEC SelectAll @CallTime = 'Map', @Department = :dep, @Username = '', @ProductName = '', @DateTime='';", nativeQuery = true)
    List<String> getMapMarkers(@Param(value = "dep") String dep);

    @Query(value = "EXEC SelectAll @CallTime = 'Productmap', @Department = :dep, @Username = '', @ProductName = :name, @DateTime='';", nativeQuery = true)
    List<String> getMapMarkersOnName(@Param(value = "dep") String dep, @Param(value = "name") String name);
}
