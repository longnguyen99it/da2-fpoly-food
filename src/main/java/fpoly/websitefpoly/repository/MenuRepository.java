package fpoly.websitefpoly.repository;

import fpoly.websitefpoly.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
@Service
public interface MenuRepository  extends JpaRepository<Menu,Long> {
    Page<Menu> findAllByStatus(String status, Pageable pageable);
}
