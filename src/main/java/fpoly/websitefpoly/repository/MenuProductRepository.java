package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.MenuProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
@Service
public interface MenuProductRepository extends JpaRepository<MenuProduct,Long> {
    Page<MenuProduct> findAllByMenuId(Long id, Pageable pageable);
}
