package fpoly.websitefpoly.service;

import fpoly.websitefpoly.dto.CategoryDto;
import fpoly.websitefpoly.request.CreateCategoryRequest;
import fpoly.websitefpoly.request.SearchCategoryRequest;
import fpoly.websitefpoly.request.UpdateCategoryRequest;
import fpoly.websitefpoly.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    ResponeData<CategoryDto> created(CreateCategoryRequest createCategoryRequest) throws Exception;

    ResponeData<CategoryDto> updated(Long id, UpdateCategoryRequest updateCategoryRequest) throws Exception;

    ResponeData<Page<CategoryDto>> search(SearchCategoryRequest searchCategoryRequest, Pageable pageable) throws Exception;

    ResponeData<CategoryDto> detail(Long id) throws Exception;

    ResponeData<Boolean> deleted(Long id) throws Exception;
}
