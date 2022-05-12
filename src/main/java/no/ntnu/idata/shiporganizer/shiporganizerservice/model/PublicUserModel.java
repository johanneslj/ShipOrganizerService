package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a public user.
 * Object only containing information that can be displayed
 */
public class PublicUserModel {
  private final String name;
  private final String email;
  private List<String> departments;

  /**
   * Instantiates a Public user model.
   *
   * @param name  the name
   * @param email the email
   */
  public PublicUserModel(String name, String email) {
    this.name = name;
    this.email = email;
    this.departments = new ArrayList<>();
  }

  /**
   * Instantiates a Public user model.
   *
   * @param name        the name
   * @param email       the email
   * @param departments the departments
   */
  public PublicUserModel(String name, String email, List<String> departments) {
    this.name = name;
    this.email = email;
    this.departments = departments;
  }

  /**
   * Gets users name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets users email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets users departments.
   *
   * @return List of the departments
   */
  public List<String> getDepartments() {
    return departments;
  }

  /**
   * Sets new /updates departments.
   *
   * @param departments the new/updated departments
   */
  public void setDepartments(List<String> departments) {
    this.departments = departments;
  }

  @Override
  public String toString() {
    return "PublicUserModel{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", departments=" + departments +
        '}';
  }
}
