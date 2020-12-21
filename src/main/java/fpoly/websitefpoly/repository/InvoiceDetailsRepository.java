package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Invoice;
import fpoly.websitefpoly.entity.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @created 11/11/2020
 * @project website-fpoly
 */
@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {
    List<InvoiceDetails> findAllByInvoice(Invoice invoice);

    List<InvoiceDetails> findAllByInvoiceId(Long id);
}
