package fpoly.websitefpoly.dto;

import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Nguyen Hoang Long
 * @create 11/28/2020
 * @project website-fpoly
 */
@Data
@Builder
public class MenuProductDto {
    private Menu menu;
    private Product product;
}
