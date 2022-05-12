package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.JWTProperties;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Product;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.ProductService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.service.UserService;
import no.ntnu.idata.shiporganizer.shiporganizerservice.userprinciple.UserPrincipalService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  MockMvc mvc;

  @MockBean
  PasswordEncoder passwordEncoder;

  @MockBean
  UserPrincipalService userPrincipalService;

  @MockBean
  JWTProperties jwtProperties;

  @MockBean
  UserService userService;

  @MockBean
  ProductService productService;

  final Product productOne = new Product(1, "One", "PN1", "1111111111111", "1", "1");
  final Product productTwo = new Product(2, "Two", "PN2", "2222222222222", "2", "2");
  final Product productThree = new Product(3, "Three", "PN3", "3333333333333", "3", "3");

  @BeforeEach
  void setUp() {
    Mockito
        .when(
            productService.createNewProduct(any(), any(), anyInt(), anyInt(), any(), any(), any()))
        .thenReturn(true);
    Mockito
        .when(productService.getInitialProductInventory(any()))
        .thenReturn(Arrays.asList(productOne, productTwo, productThree));
  }

  @Test
  @WithMockUser(roles = "USER")
  void postNewProductTest() throws Exception {
    mvc.perform(post("/api/product/new-product").content(
        "{" +
            "productName: \"Name One\"," +
            "productNumber: \"PN1\"," +
            "stock: \"3\"," +
            "desiredStock: \"4\"," +
            "barcode: \"111\"," +
            "department: \"Skipper\"," +
            "dateTime: \"2022-05-11 11:10:01\"" +
            "}"
    )).andExpect(status().is2xxSuccessful());
  }

  @Test
  @WithMockUser(roles = "USER")
  void getInventoryTest() throws Exception {
    mvc
        .perform(post("/api/product/get-inventory").content("{department: \"Skipper\"}"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$", Matchers.hasSize(3)));
  }
}