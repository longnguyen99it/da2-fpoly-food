package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByIdAndStatus(Long id, String status);

//    @Query("select p from ProductEntity p join CategoryEntity c on p.id = c.id " +
//            "where p.productName like '%?1%' " +
//            "and p.status like '%?2%' and p.createdAt between ?3 and ?4 and p.categoryEntity.id = ?5")
//    Page<ProductEntity> findCategory(String productName, String status, Date fromDate, Date toDate, Long category);
}
