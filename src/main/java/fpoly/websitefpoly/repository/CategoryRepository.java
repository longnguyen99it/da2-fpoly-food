package fpoly.websitefpoly.repository;


import fpoly.websitefpoly.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select cate from Category cate where cate.categoryName like ?1 and cate.status like ?2")
    Page<Category> getCategoryByStatus(String name, String status, Pageable pageable);

    Page<Category> findAllByStatus(String status, Pageable pageable);

    Category findByIdAndStatus(Long id, String status);
}
