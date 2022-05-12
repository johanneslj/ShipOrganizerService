package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class represent an Image.
 */
@Entity
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String name;
  private String fileExtension;

  /**
   * Instantiates a new Image.
   */
  public Image() {
  }

  /**
   * Instantiates a new Image.
   *
   * @param name          the image name
   * @param fileExtension the image file extension
   */
  public Image(String name, String fileExtension) {
    this.name = name;
    this.fileExtension = fileExtension;
  }

  /**
   * Gets image id.
   *
   * @return the images id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets a new image id.
   *
   * @param id the new image id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the image name.
   *
   * @return the image name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets a new image name.
   *
   * @param name the new image name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets file extension.
   *
   * @return the file extension
   */
  public String getFileExtension() {
    return fileExtension;
  }

  /**
   * Sets a new file extension.
   *
   * @param fileExtension the new file extension
   */
  public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }
}
