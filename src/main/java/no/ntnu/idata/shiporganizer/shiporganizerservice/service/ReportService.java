package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Report;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ReportRepository;
import org.springframework.stereotype.Service;

/**
 * Report service is responsible for handling the data regarding
 * map markers.
 */
@Service
public class ReportService {
    private ReportRepository reportRepository;

    ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Gets the reports from the repository and creates report objects
     * each report is added to a list which is then sorted to group the reports
     * together based on their relative closeness.
     *
     * @return a sorted Map with LatLng as key and a List of reports as values
     */
    public Map<String, List<Report>> getMapMarkers() {
        List<String> stringReports = reportRepository.getMapMarkers();

        return sortReportsByIntoGrids(convertDataToReports(stringReports));
    }

    /**
     * Gets reports from the repository based on the name of the equipment used
     * each report is added to a list which is then sorted to group the reports
     * together based on their relative closeness.
     *
     * @param name the name of equipment which is to be shown
     * @return a sorted Map with LatLng as key and a List of reports as values
     */
    public Map<String, List<Report>> getMapMarkersOnName(String name) {
        List<String> stringReports = reportRepository.getMapMarkersOnName(name);
        return sortReportsByIntoGrids(convertDataToReports(stringReports));
    }

    /**
     * Takes the list of strings recieved from the database and converts it into reports
     *
     * @param stringReports List of reports in string form
     * @return List of Reports
     */
    private List<Report> convertDataToReports(List<String> stringReports) {
        List<Report> markers = new ArrayList<>();

        stringReports.forEach(reportString -> {
            List<String> reportBits = Arrays.asList(reportString.split(","));
            Report report = new Report(reportBits.get(1),
                Math.abs(Integer.parseInt(reportBits.get(2))),
                Float.parseFloat(reportBits.get(3)),
                Float.parseFloat(reportBits.get(4)),
                getDateFromString(reportBits.get(5)),
                reportBits.get(6));
            if(report.getFullName() == null || report.getFullName().equals("null")) {
                report.setFullName("No name registered");
            }
            markers.add(report);
        });
        return markers;
    }

    /**
     * Gets the date from inside a string
     *
     * @param dateString the string to convert to a Date
     * @return a date, generated from the String
     */
    private Date getDateFromString(String dateString) {
        Date date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRANCE);
            date = formatter.parse(dateString);
            date.setTime(date.getTime() + 3600000);
        } catch (ParseException e) {
            date = new Date();
        }

        return date;
    }

    /**
     * Sorts the list of markers from the database into a grid based on their coordinates
     * <p>
     * This method rounds the lat lng coordinates to 3 decimal places and then creates
     * a string composed of both which is used as a key in the map of reports
     *
     * @param markers the list of reports to sort
     * @return a sorted map of reports
     */
    private Map<String, List<Report>> sortReportsByIntoGrids(List<Report> markers) {

        Map<String, List<Report>> mapSortedByLatLng = new HashMap<>();
        markers.forEach(report -> {
            float lat = report.getLatitude();
            float lng = report.getLongitude();

            lat = getRoundedFloat(lat, 2);
            lng = getRoundedFloat(lng, 2);

            String latLng = "" + lat + ", " + lng;

            // If the Map is empty or it doesn't contain the new key
            // create a new key with that latLng and add the report which
            // produced the key to the list in the map
            if(mapSortedByLatLng.isEmpty() || !mapSortedByLatLng.containsKey(latLng)) {
                mapSortedByLatLng.put(latLng, new ArrayList<>());
                mapSortedByLatLng.get(latLng).add(report);
            } else if(mapSortedByLatLng.containsKey(latLng)) {
                // If the map already contains the key just add the report
                // To the list present on that key
                mapSortedByLatLng.get(latLng).add(report);
            }
        });

        return mapSortedByLatLng;
    }

    /**
     * Rounds a float to a given amount of decimals
     *
     * @param floatToRound    the float which will be rounded
     * @param howManyDecimals how many decimals to round the float to
     * @return the rounded float
     */
    public float getRoundedFloat(float floatToRound, int howManyDecimals) {
        float roundedFloat;

        roundedFloat = (float) (Math.round(floatToRound * Math.pow(10, howManyDecimals)) / Math.pow(10, howManyDecimals));

        return roundedFloat;
    }
}
