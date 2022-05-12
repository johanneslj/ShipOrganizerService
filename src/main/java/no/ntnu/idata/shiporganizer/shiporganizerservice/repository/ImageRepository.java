package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Image;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface representing the repository for Images
 */
public interface ImageRepository extends CrudRepository<Image, Integer> {
  /**
   * Gets an image based on id search
   * @param id the id of desired image to fetch
   * @return Object of an image
   */
  Optional<Image> findById(int id);

  /**
   * Saves a new image
   * @param image image to save
   * @return saved image
   */
  Image save(Image image);

  /**
   * Gets all images
   * @return A list of images
   */
  List<Image> findAll();
}
