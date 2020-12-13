package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.request.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
public interface ProductService {

    Page<ProductDto> search(SearchProductRequest searchProductRequest, Pageable pageable) throws Exception;

    ProductDto created(CreateProductRequest createProductRequest) throws Exception;

    ProductDto update(Long id, UpdateProductRequest updateProductRequest) throws Exception;

    ProductDto detail(Long id) throws Exception;

    Boolean deleted(Long id) throws Exception;

    ProductToppingDto updateTopptingByProduct(Long id, ToppingRequest toppingRequest) throws Exception;

    ProductToppingDto toppingByProduct(Long id);
}
