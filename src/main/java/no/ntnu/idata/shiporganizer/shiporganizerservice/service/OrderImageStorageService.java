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

/**
 * Class represent the external image store service
 * The service is used when an image needs to be uploaded to an external source
 */
@Service
public class OrderImageStorageService {
  private final String FOLDER_NAME = "images/";
  private final DoSpaceConfig spaceConfig;
  private final AmazonS3 client;
  private final OrderRepository orderRepository;

  /**
   * Instantiates a new Order image storage service.
   *
   * @param spaceConfig     the space config class
   * @param client          the client
   * @param orderRepository the order repository
   */
  public OrderImageStorageService(
      DoSpaceConfig spaceConfig,
      AmazonS3 client,
      OrderRepository orderRepository) {
    this.spaceConfig = spaceConfig;
    this.client = client;
    this.orderRepository = orderRepository;
  }

  /**
   * Store the image file.
   *
   * @param multipartFile the image file
   * @param orderId       the order id
   * @throws IOException the io exception
   */
  public void storeFile(MultipartFile multipartFile, int orderId) throws IOException {
    if (multipartFile == null) {
      throw new IOException("MultipartFile cannot be null.");
    }
    uploadToServer(multipartFile);
    linkImageToOrder(multipartFile, orderId);
  }

  /**
   * Uploads the given image to the external server
   * @param multipartFile the image file
   * @throws IOException if some exception occurred
   */
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

  /**
   * Creates an ObjectMetadata with the MultipartFile
   * @param multipartFile the image file
   * @return the ObjectMetadata
   * @throws IOException  if some exception occurred
   */
  private ObjectMetadata createObjectMetadataWithMultipartFile(MultipartFile multipartFile)
      throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(multipartFile.getInputStream().available());
    if (multipartFile.getContentType() != null && !multipartFile.getContentType().isEmpty()) {
      metadata.setContentType(multipartFile.getContentType());
    }
    return metadata;
  }

  /**
   * Gets the image name from the image file and adds it to the desired
   * store path
   * @param multipartFile the image file
   * @return the image store path
   */
  private String getKeyFromMultipartFile(MultipartFile multipartFile) {
    return FOLDER_NAME + getFileName(multipartFile);
  }

  /**
   * Gets the image file name.
   *
   * @param multipartFile the image file
   * @return the file name
   */
  static String getFileName(MultipartFile multipartFile) {
    return FilenameUtils.removeExtension(multipartFile.getOriginalFilename()) + "." +
        FilenameUtils.getExtension(multipartFile.getOriginalFilename());
  }

  /**
   * Links the image to the correct order
   * @param multipartFile the image file
   * @param orderId the order id
   */
  private void linkImageToOrder(MultipartFile multipartFile, int orderId) {
    orderRepository.findById(orderId).ifPresent((order) -> {
      order.setImageName(multipartFile.getOriginalFilename());
      orderRepository.save(order);
    });
  }

  /**
   * Delete a image file.
   *
   * @param id the id to be deleted
   */
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
