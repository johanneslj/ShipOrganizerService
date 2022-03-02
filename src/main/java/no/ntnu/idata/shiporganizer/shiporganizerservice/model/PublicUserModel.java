package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

public class PublicUserModel {
  private final String name;
  private final String email;

  public PublicUserModel(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "PublicUserModel{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
