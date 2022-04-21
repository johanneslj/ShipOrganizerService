package no.ntnu.idata.shiporganizer.shiporganizerservice.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idata.shiporganizer.shiporganizerservice.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Integer> {
  Optional<Image> findById(int id);

  List<Image> findAll();
}
