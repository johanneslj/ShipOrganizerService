package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "EXEC SelectAll @CallTime = 'Map', @Department = 'Deck', @Username = '';", nativeQuery = true)
    List<String> getMapMarkers();
}
