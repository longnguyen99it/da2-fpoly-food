package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Query("select sum(inv.amountTotal) from Invoice inv where inv.user = ?1 and inv.status = 'Hoàn_thành' group by inv.user.id")
    double sumTotalInvoice(User user);

    @Query(value = "SELECT e FROM Invoice e WHERE e.createdAt BETWEEN :startDate AND :endDate",nativeQuery = true)
    Double chartInvoice(@Param(value = "startDate") Date startDate,@Param(value = "endDate") Date endDate);
}