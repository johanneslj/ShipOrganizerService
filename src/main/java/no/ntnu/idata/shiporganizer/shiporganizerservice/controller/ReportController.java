package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import java.util.List;
import javax.transaction.Transactional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/reports")
@Transactional
public class ReportController {

    private final ReportRepository reportRepository;

    ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/all-reports")
    public ResponseEntity<List<String>> getAllReports() {
        return ResponseEntity.ok(reportRepository.getMapMarkers());
    }


}