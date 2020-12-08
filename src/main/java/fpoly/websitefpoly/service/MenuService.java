package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
public interface MenuService {

    ResponeData<Page<Menu>> getAll(Pageable pageable);

    ResponeData<Page<ProductDto>> getProductByMenu(Long id, Pageable pageable);
}
