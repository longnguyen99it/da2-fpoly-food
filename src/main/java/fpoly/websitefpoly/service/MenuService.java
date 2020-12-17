package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.MenuDto;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.dto.UpdateMenuDtoRequest;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.entity.Product;
import fpoly.websitefpoly.request.CreateMenuDtoRequest;
import fpoly.websitefpoly.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {

    Page<Menu> getAll(Pageable pageable);

    Page<ProductDto> getProductByMenu(Long id, Pageable pageable);

    MenuDto create(CreateMenuDtoRequest createMenuDtoRequest) throws Exception;

    MenuDto update(Long id, UpdateMenuDtoRequest createMenuDto) throws Exception;

    MenuDto detail(Long id);

    Boolean delete(Long id) throws Exception;

    MenuDto addProductoMenu(Long id, ProductRequest productRequest) throws Exception;
}
