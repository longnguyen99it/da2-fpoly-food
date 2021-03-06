package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyen Hoang Long on 10/31/2020
 * @created 10/31/2020
 * @project fpoly-food
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    @Query("SELECT inv FROM Invoice inv WHERE inv.status in (?1)")
    Page<Invoice> searchInvoice(String[] status, Pageable pageable);

    Invoice findByIdAndStatus(Long id,String status);

    List<Invoice> findAllByUser(User user);

    long countAllByStatus(String status);
}