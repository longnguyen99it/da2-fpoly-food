package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.MenuDto;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.dto.UpdateMenuDtoRequest;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.request.CreateMenuDtoRequest;
import fpoly.websitefpoly.request.ProductRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nguyen Hoang Long
 * @create 11/27/2020
 * @project website-fpoly
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = {"/", ""})
    public ResponeData<Page<Menu>> getAllMenu(Pageable pageable) {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, menuService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponeData<Page<ProductDto>> details(@PathVariable Long id,
                                                 @PageableDefault(size = 10, page = 0, sort = "id",
                                                         direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, menuService.getProductByMenu(id, pageable));

    }

    @PostMapping("/")
    public ResponeData<MenuDto> create(@RequestBody CreateMenuDtoRequest createMenuDtoRequest) throws Exception {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    menuService.create(createMenuDtoRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @PutMapping("/{id}")
    public ResponeData<MenuDto> update(@PathVariable Long id, @RequestBody UpdateMenuDtoRequest updateMenuDtoRequest) throws Exception {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    menuService.update(id, updateMenuDtoRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    menuService.delete(id));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @PutMapping("/add-product/{id}")
    public ResponeData<MenuDto> updateProductToMenu(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws Exception {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, menuService.addProductoMenu(id, productRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }
}
