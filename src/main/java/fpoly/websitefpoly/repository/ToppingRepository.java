package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Topping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
    Page<Topping> findByNameContaining(String name, Pageable pageable);

    Topping findByIdAndStatus(Long id, String status);

    List<Topping> findAllByStatus(String status);
}
