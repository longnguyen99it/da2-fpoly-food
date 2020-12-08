package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.entity.MenuProduct;
import fpoly.websitefpoly.repository.MenuProductRepository;
import fpoly.websitefpoly.repository.MenuRepository;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.MenuService;
import fpoly.websitefpoly.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private ProductService productService;

    @Autowired
    private MenuProductRepository menuProductRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public ResponeData<Page<Menu>> getAll(Pageable pageable) {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, menuRepository.findAll(pageable));
    }

    @Override
    public ResponeData<Page<ProductDto>> getProductByMenu(Long id, Pageable pageable) {
        Page<MenuProduct> menuProductPage = menuProductRepository.findAllByMenuId(id, pageable);
        Page<ProductDto> dtoPage = menuProductPage.map(new Function<MenuProduct, ProductDto>() {
            @SneakyThrows
            @Override
            public ProductDto apply(MenuProduct menuProduct) {
                ProductDto productDto = productService.detail(menuProduct.getId());
                return productDto;
            }
        });

        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, dtoPage);
    }
}
