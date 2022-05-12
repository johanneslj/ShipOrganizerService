package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Report controller.
 * Endpoint for the client
 */
@RestController
@RequestMapping(value ="/reports")
@Transactional
public class ReportController {

    private final ReportService reportService;

    /**
     * Instantiates a Report controller.
     *
     * @param reportService the report service
     */
    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Gets all reports.
     *
     * @param dep the user department
     * @return all reports
     */
    @GetMapping("/all-reports={dep}")
    public ResponseEntity<Map<String, List<Report>>> getAllReports(@PathVariable(value = "dep") String dep) {
        return ResponseEntity.ok(reportService.getMapMarkers(dep));
    }

    /**
     * Gets reports based on product name.
     *
     * @param name the product name
     * @param dep  the users department
     * @return the reports based on product name
     */
    @GetMapping("/reports-with-name={name}-dep={dep}")
    public ResponseEntity<Map<String, List<Report>>> getReportsWithName(@PathVariable(value = "name") String name, @PathVariable(value = "dep") String dep) {
        return ResponseEntity.ok(reportService.getMapMarkersOnName(dep, name));
    }

}