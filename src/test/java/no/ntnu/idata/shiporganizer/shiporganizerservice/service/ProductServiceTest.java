package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        1,
        productTwo.getBarcode(),
        "Skipper",
        "2022-02-2 02:22:22"
    );
    underTest.createNewProduct(
        productThree.getProductName(),
        productThree.getProductNumber(),
        3,
        1,
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
    assertEquals(3, underTest.getInitialProductInventory("Skipper").size());
  }

  @Test
  void getUpdatedProductInventoryTest() {
    assertEquals(2, underTest.getUpdatedProductInventory("Skipper", "2022-02-01 11:11:10").size());
  }

  @Test
  void getProductRecommendedInventoryTest() {
    List<Product> products = underTest.getProductRecommendedInventory("Skipper");
    assertEquals("0", products.get(0).getStock());
    assertEquals("1", products.get(1).getStock());
    assertEquals("2", products.get(2).getStock());
  }

  @Test
  void setNewStockTest() {
    assertEquals(
        "Success",
        underTest.setNewStock("PN1", "username", -1, 1.11f, 1.12f, "2022-04-01 11:11:12")
    );
  }

  @Test
  void checkProdNumberTest() {
    assertTrue(underTest.checkProdNumber("PN1"));
    assertFalse(underTest.checkProdNumber("000"));
  }

  @Test
  void editProductTest() {
    assertTrue(
        underTest.editProduct(1, "One Edited", "PN1", 5, "111", "Skipper", "2022-05-07 10:10:11"));
  }
}