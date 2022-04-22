package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.DoSpaceConfig;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Order;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.OrderRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrderImageStorageService {
  private final String FOLDER_NAME = "images/";
  private final DoSpaceConfig spaceConfig;
  private final AmazonS3 client;
  private final OrderRepository orderRepository;

  public OrderImageStorageService(
      DoSpaceConfig spaceConfig,
      AmazonS3 client,
      OrderRepository orderRepository) {
    this.spaceConfig = spaceConfig;
    this.client = client;
    this.orderRepository = orderRepository;
  }

  public void storeFile(MultipartFile multipartFile, int orderId) throws IOException {
    if (multipartFile == null) {
      throw new IOException("MultipartFile cannot be null.");
    }
    uploadToServer(multipartFile);
    linkImageToOrder(multipartFile, orderId);
  }

  private void uploadToServer(MultipartFile multipartFile) throws IOException {
    ObjectMetadata metadata = createObjectMetadataWithMultipartFile(multipartFile);

    client.putObject(
        new PutObjectRequest(
            spaceConfig.getBucket(),
            getKeyFromMultipartFile(multipartFile),
            multipartFile.getInputStream(),
            metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
  }

  private ObjectMetadata createObjectMetadataWithMultipartFile(MultipartFile multipartFile)
      throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(multipartFile.getInputStream().available());
    if (multipartFile.getContentType() != null && !multipartFile.getContentType().isEmpty()) {
      metadata.setContentType(multipartFile.getContentType());
    }
    return metadata;
  }

  private String getKeyFromMultipartFile(MultipartFile multipartFile) {
    return FOLDER_NAME + getFileName(multipartFile);
  }

  static String getFileName(MultipartFile multipartFile) {
    return FilenameUtils.removeExtension(multipartFile.getOriginalFilename()) + "." +
        FilenameUtils.getExtension(multipartFile.getOriginalFilename());
  }

  private void linkImageToOrder(MultipartFile multipartFile, int orderId) {
    orderRepository.findById(orderId).ifPresent((order) -> {
      order.setImageName(multipartFile.getOriginalFilename());
      orderRepository.save(order);
    });
  }

  public void deleteFile(int id) {
    orderRepository
        .findById(id)
        .ifPresent((order) -> client.deleteObject(
            new DeleteObjectRequest(spaceConfig.getBucket(), getKeyFromOrder(order))));
  }

  private String getKeyFromOrder(Order order) {
    return FOLDER_NAME + order.getImageName();
  }
}
