package fpoly.websitefpoly.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nguyen Hoang Long on 10/25/2020
 * @created 10/25/2020
 * @project fpoly-food
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "unit")
    private String unit;

    @Column(name = "warehouses")
    private Integer warehouses;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
