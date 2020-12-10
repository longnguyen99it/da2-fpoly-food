package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.MenuDto;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.dto.UpdateMenuDtoRequest;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.entity.MenuProduct;
import fpoly.websitefpoly.entity.Product;
import fpoly.websitefpoly.repository.MenuProductRepository;
import fpoly.websitefpoly.repository.MenuRepository;
import fpoly.websitefpoly.repository.ProductRepository;
import fpoly.websitefpoly.request.CreateMenuDtoRequest;
import fpoly.websitefpoly.service.MenuService;
import fpoly.websitefpoly.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ProductRepository productRepository;
    @Autowired
    private MenuProductRepository menuProductRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Page<Menu> getAll(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDto> getProductByMenu(Long id, Pageable pageable) {
        Page<MenuProduct> menuProductPage = menuProductRepository.findAllByMenuId(id, pageable);
        Page<ProductDto> dtoPage = menuProductPage.map(new Function<MenuProduct, ProductDto>() {
            @SneakyThrows
            @Override
            public ProductDto apply(MenuProduct menuProduct) {
                Product product = productRepository.findByIdAndStatus(menuProduct.getProduct().getId(),"A");
                if(product == null){

                }
                ProductDto productDto = productService.detail(menuProduct.getId());
                return productDto;
            }
        });
        return dtoPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuDto create(CreateMenuDtoRequest createMenuDtoRequest) throws Exception {
        Menu menu = ModelMapperUtils.map(createMenuDtoRequest, Menu.class);
        Menu save = menuRepository.save(menu);
        return ModelMapperUtils.map(save, MenuDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuDto update(Long id, UpdateMenuDtoRequest createMenuDto) throws Exception {
        return null;
    }

    @Override
    public MenuDto detail(Long id) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) throws Exception {
        return null;
    }
}
