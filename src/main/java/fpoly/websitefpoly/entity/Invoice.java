package fpoly.websitefpoly.entity;

import lombok.*;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice implements Serializable {
    private static final long serialVersionUID = -1000119078147252957L;

    public static final String NEW = "new";
    public static final String WATCHED = "watched";
    public static final String PROCESSING = "processing";
    public static final String TRANSPORT = "transport";
    public static final String FINISH = "finish";
    public static final String CANCEL = "cancel";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "total_price")
    private Double amountTotal;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_method")
    private String paymentMethods;

    @Column(name = "phone")
    private String phone;

    @Column(name = "receiving_time")
    private String receivingTime;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Users users;
}
