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

    public static final String CHUA_SU_LY = "Chưa_sử_lý";
    public static final String ĐANG_XU_LY = "Đang_xử_lý";
    public static final String ĐANG_CHẾ_BIẾN = "Đang_chế_biến";
    public static final String VAN_CHUYEN = "Vận_chuyển";

    public static final String ĐA_DUOC_XU_LY = "Đã_được_xử_lý";
    public static final String BI_HUY_BO = "Bị_hủy_bỏ";

    public static final String HOAN_LAI_ = "Hoàn_lại";
    public static final String HOAN_THANH = "Hoàn_thành";
    public static final String TU_CHOI_ = "Từ_chối";
    public static final String THAT_BAI_ = "Thất_bại";
    public static final String HET_HAN_ = "Hết_hạn";

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
    private Date receivingTime;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
