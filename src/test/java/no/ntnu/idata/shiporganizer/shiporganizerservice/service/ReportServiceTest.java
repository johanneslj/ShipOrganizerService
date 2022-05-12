package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  @MockBean
  MailService mailService;

  @Autowired
  ProductService productService;

  @Autowired
  ReportService underTest;

  final Product productOne = new Product(1, "One", "PN1", "1111111111111", "1", "1");
  final Product productTwo = new Product(2, "Two", "PN2", "2222222222222", "2", "2");

  @BeforeEach
  void setUp() {
    productService.createNewProduct(
        productOne.getProductName(),
        productOne.getProductNumber(),
        3,
        3,
        productOne.getBarcode(),
        "Skipper",
        "2022-01-1 01:11:11"
    );
    productService.createNewProduct(
        productTwo.getProductName(),
        productTwo.getProductNumber(),
        2,
        1,
        productTwo.getBarcode(),
        "Skipper",
        "2022-02-2 02:22:22"
    );
    productService.setNewStock("PN1", "username", -1, 1.11f, 1.12f, "2022-04-01 11:11:12");
    productService.setNewStock("PN2", "username", -2, 2.11f, 2.12f, "2022-04-02 22:20:12");
  }

  @AfterEach
  void tearDown() {
    productService.deleteProduct("PN1");
    productService.deleteProduct("PN2");
  }

  @Test
  void getMapMarkers() {
    assertEquals(2, underTest.getMapMarkers("Skipper").size());
  }

  @Test
  void getMapMarkersOnName() {
    assertEquals(1, underTest.getMapMarkersOnName("Skipper", "One").size());
  }
}