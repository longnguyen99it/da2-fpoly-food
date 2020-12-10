package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.CategoryDto;
import fpoly.websitefpoly.request.CreateCategoryRequest;
import fpoly.websitefpoly.request.SearchCategoryRequest;
import fpoly.websitefpoly.request.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDto create(CreateCategoryRequest createCategoryRequest) throws Exception;

    CategoryDto update(Long id, UpdateCategoryRequest updateCategoryRequest) throws Exception;

    Page<CategoryDto> search(SearchCategoryRequest searchCategoryRequest, Pageable pageable) throws Exception;

    CategoryDto detail(Long id) throws Exception;

    Boolean delete(Long id) throws Exception;
}
