package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.request.CreateProductRequest;
import fpoly.websitefpoly.request.SearchProductRequest;
import fpoly.websitefpoly.request.UpdateProductRequest;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
public interface ProductService {

    ResponeData<Page<ProductDto>> search(SearchProductRequest searchProductRequest, Pageable pageable) throws Exception;

    ResponeData<ProductDto> created(CreateProductRequest createProductRequest) throws Exception;

    ResponeData<ProductDto> updated(Long id, UpdateProductRequest updateProductRequest) throws Exception;

    ProductDto detail(Long id) throws Exception;

    ResponeData<Boolean> deleted(Long id) throws Exception;
}
