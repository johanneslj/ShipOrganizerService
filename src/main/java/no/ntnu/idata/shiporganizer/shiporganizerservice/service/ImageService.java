package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.List;
import no.ntnu.idata.shiporganizer.shiporganizerservice.config.DoSpaceConfig;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Image;
import no.ntnu.idata.shiporganizer.shiporganizerservice.repository.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
  private final String FOLDER_NAME = "shiporg/";
  private final DoSpaceConfig spaceConfig;
  private final AmazonS3 client;
  private final ImageRepository imageRepository;

  public ImageService(
      DoSpaceConfig spaceConfig,
      AmazonS3 client,
      ImageRepository imageRepository) {
    this.spaceConfig = spaceConfig;
    this.client = client;
    this.imageRepository = imageRepository;
  }

  public void storeFile(MultipartFile multipartFile) throws IOException {
    uploadToServer(multipartFile);
    saveImageToRepository(multipartFile);
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
    return FOLDER_NAME + FilenameUtils.removeExtension(multipartFile.getOriginalFilename()) + "." +
        FilenameUtils.getExtension(multipartFile.getOriginalFilename());
  }

  private void saveImageToRepository(MultipartFile multipartFile) {
    Image image = new Image(
        FilenameUtils.removeExtension(multipartFile.getOriginalFilename()),
        FilenameUtils.getExtension(multipartFile.getOriginalFilename())
    );
    imageRepository.save(image);
  }

  public void deleteFile(int id) {
    imageRepository
        .findById(id)
        .ifPresent((image) -> {
          client.deleteObject(
              new DeleteObjectRequest(spaceConfig.getBucket(), getKeyFromImage(image)));
        });
  }

  private String getKeyFromImage(Image image) {
    return FOLDER_NAME + image.getName() + "." + image.getFileExtension();
  }

  public List<Image> getAllImages() {
    return imageRepository.findAll();
  }
}
