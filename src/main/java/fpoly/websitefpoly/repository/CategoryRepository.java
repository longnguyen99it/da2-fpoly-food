package fpoly.websitefpoly.repository;


import fpoly.websitefpoly.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByIdAndStatus(Long id, String status);
}
