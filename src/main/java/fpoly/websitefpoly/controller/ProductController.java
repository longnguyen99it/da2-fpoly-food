package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.request.CreateProductRequest;
import fpoly.websitefpoly.request.SearchProductRequest;
import fpoly.websitefpoly.request.UpdateProductRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = {"", "/"})
    private ResponeData<Page<ProductDto>> search(@ModelAttribute SearchProductRequest searchProductRequest, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return productService.search(searchProductRequest, pageable);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @PostMapping("/")
    public ResponeData<ProductDto> create(@RequestBody CreateProductRequest createProductRequest) {
        try {
            return productService.created(createProductRequest);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @PutMapping("/{id}")
    public ResponeData<ProductDto> update(@PathVariable Long id, @RequestBody UpdateProductRequest updateProductRequest) {
        try {
            return productService.updated(id, updateProductRequest);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @GetMapping("/{id}")
    public ResponeData<ProductDto> details(@PathVariable Long id) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE,AppConstant.SUCCESSFUL_MESAGE,productService.detail(id));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return productService.deleted(id);
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, false);
        }
    }


}
