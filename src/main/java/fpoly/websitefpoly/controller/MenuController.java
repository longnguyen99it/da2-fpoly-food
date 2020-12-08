package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.dto.MenuProductDto;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.entity.Menu;
import fpoly.websitefpoly.entity.Product;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping(value = {"/",""})
    public ResponeData<Page<Menu>> getAllMenu(Pageable pageable){
        return menuService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponeData<Page<ProductDto>> details(@PathVariable Long id,
                                                     @PageableDefault(size = 10,page = 0,sort = "id",
                                                         direction = Sort.Direction.ASC) Pageable pageable){
       return menuService.getProductByMenu(id,pageable);
    }
}
