package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.OrderByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderByDateRepository extends JpaRepository<OrderByDate, Long> {
    List<OrderByDate> findAllByUsersEmail(String email);

    OrderByDate findAllByInvoiceId(Long id);
}
