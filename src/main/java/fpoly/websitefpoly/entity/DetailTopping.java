package fpoly.websitefpoly.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_topping")
public class DetailTopping implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_topping")
    private String nameTopping;

    @Column(name = "price_topping")
    private Double priceTopping;

    @ManyToOne
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetails invoiceDetails;
}
