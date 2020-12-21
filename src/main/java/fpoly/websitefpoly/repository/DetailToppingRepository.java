package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.DetailTopping;
import fpoly.websitefpoly.entity.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface DetailToppingRepository extends JpaRepository<DetailTopping, Long> {
    List<DetailTopping> findAllByInvoiceDetails(InvoiceDetails invoiceDetails);
}
