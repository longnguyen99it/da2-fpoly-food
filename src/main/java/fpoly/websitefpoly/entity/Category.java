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
@Table(name = "categorys")
public class Category implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private String status;

}
