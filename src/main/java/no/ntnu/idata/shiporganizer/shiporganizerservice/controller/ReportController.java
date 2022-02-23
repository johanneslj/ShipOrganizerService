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

@RestController
@RequestMapping(value ="/reports")
@Transactional
public class ReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/all-reports")
    public ResponseEntity<Map<String, List<Report>>> getAllReports() {
        return ResponseEntity.ok(reportService.getMapMarkers());
    }

    @GetMapping("/reports-with-name={name}")
    public ResponseEntity<Map<String, List<Report>>> getReportsWithName(@PathVariable(value = "name") String name) {
        System.out.println(name);
        return ResponseEntity.ok(reportService.getMapMarkersOnName(name));
    }

}