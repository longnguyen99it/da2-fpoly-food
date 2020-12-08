package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.ProductTopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
@Repository
public interface ProductToppingRepository extends JpaRepository<ProductTopping,Long> {
    List<ProductTopping> findAllByProductId(Long id);
}
