package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @MockBean
  MailService mailService;

  @MockBean
  OrderImageStorageService imageService;

  @Autowired
  OrderService underTest;

  @BeforeEach
  void setUp() throws IOException {
    Mockito
        .doNothing()
        .when(imageService)
        .storeFile(any(), anyInt());
    underTest.insertNewOrder("Skipper", new TestMultipartFile("image-test"));
  }

  @AfterEach
  void tearDown() {
    underTest.deleteAllOrders();
  }

  @Test
  void getPendingOrders() {
    assertEquals(1, underTest.getPendingOrders("Skipper").size());
  }

  @Test
  void getConfirmedOrders() {
    underTest.updateOrder("Skipper", "image-test.jpg", 1);
    assertEquals(1, underTest.getConfirmedOrders("Skipper").size());
  }

  private class TestMultipartFile implements MultipartFile {

    private final String name;

    public TestMultipartFile(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getOriginalFilename() {
      return name + ".jpg";
    }

    @Override
    public String getContentType() {
      return null;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public long getSize() {
      return 0;
    }

    @Override
    public byte[] getBytes() {
      return new byte[0];
    }

    @Override
    public InputStream getInputStream() {
      return null;
    }

    @Override
    public Resource getResource() {
      return MultipartFile.super.getResource();
    }

    @Override
    public void transferTo(File dest) throws IllegalStateException {

    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
      MultipartFile.super.transferTo(dest);
    }
  }
}