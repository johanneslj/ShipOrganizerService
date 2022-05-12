package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @MockBean
  MailService mailService;

  @Autowired
  ProductService underTest;

  final Product productOne = new Product(1, "One", "PN1", "1111111111111", "1", "1");
  final Product productTwo = new Product(2, "Two", "PN2", "2222222222222", "2", "2");
  final Product productThree = new Product(3, "Three", "PN3", "3333333333333", "3", "3");

  @BeforeEach
  void setUp() {
    Mockito.doNothing().when(mailService).createAndSendPdf(any(), any(), any());

    underTest.createNewProduct(
        productOne.getProductName(),
        productOne.getProductNumber(),
        1,
        1,
        productOne.getBarcode(),
        "Skipper",
        "2022-01-1 01:11:11"
    );
    underTest.createNewProduct(
        productTwo.getProductName(),
        productTwo.getProductNumber(),
        2,
        2,
        productTwo.getBarcode(),
        "Skipper",
        "2022-02-2 02:22:22"
    );
    underTest.createNewProduct(
        productThree.getProductName(),
        productThree.getProductNumber(),
        3,
        3,
        productThree.getBarcode(),
        "Skipper",
        "2022-03-3 03:33:33"
    );
  }

  @AfterEach
  void tearDown() {
    underTest.deleteProduct("PN1");
    underTest.deleteProduct("PN2");
    underTest.deleteProduct("PN3");
  }

  @Test
  void getInitialProductInventoryTest() {
    assertEquals(
        Arrays.asList(productOne, productTwo, productThree),
        underTest.getInitialProductInventory("Skipper"));
  }

  @Test
  void getUpdatedProductInventoryTest() {
  }

  @Test
  void getProductRecommendedInventoryTest() {
  }

  @Test
  void setNewStockTest() {
  }

  @Test
  void createNewProductTest() {
  }

  @Test
  void checkProdNumberTest() {
  }

  @Test
  void deleteProductTest() {
  }

  @Test
  void editProductTest() {
  }

  @Test
  void createPdfTest() {
  }
}