package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.Users;
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
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT inv FROM Invoice inv WHERE inv.status in (?1) and inv.type = ?2")
    Page<Invoice> searchInvoice(String[] status, String type, Pageable pageable);


    Invoice findByIdAndStatus(Long id, String status);

    List<Invoice> findAllByUsers(Users users);

    long countAllByStatus(String status);

    @Query("select sum(inv.amountTotal) from Invoice inv where inv.users = ?1 and inv.status = 'Hoàn_thành' group by inv.users.id")
    double sumTotalInvoice(Users users);

    @Query(value = "SELECT e FROM Invoice e WHERE e.createdAt BETWEEN :startDate AND :endDate", nativeQuery = true)
    Double chartInvoice(@Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
}