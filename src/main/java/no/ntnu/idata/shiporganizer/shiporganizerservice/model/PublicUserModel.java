package no.ntnu.idata.shiporganizer.shiporganizerservice.model;

import java.util.ArrayList;
import java.util.List;

public class PublicUserModel {
  private final String name;
  private final String email;
  private List<String> departments;

  public PublicUserModel(String name, String email) {
    this.name = name;
    this.email = email;
    this.departments = new ArrayList<>();
  }

  public PublicUserModel(String name, String email, List<String> departments) {
    this.name = name;
    this.email = email;
    this.departments = departments;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public List<String> getDepartments() {
    return departments;
  }

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
