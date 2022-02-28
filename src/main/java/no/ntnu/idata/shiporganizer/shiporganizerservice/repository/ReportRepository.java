package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "EXEC SelectAll @CallTime = 'Map', @Department = '', @Username = '', @ProductName = '';", nativeQuery = true)
    List<String> getMapMarkers();

    @Transactional
    @Query(value = "EXEC SelectAll @CallTime = 'Productmap', @Department = '', @Username = '', @ProductName = :productName ;", nativeQuery = true)
    List<String> getMapMarkersOnName(@Param(value = "productName") String productName);
}
