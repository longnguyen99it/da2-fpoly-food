package fpoly.websitefpoly.controller;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.dto.CategoryDto;
import fpoly.websitefpoly.request.CreateCategoryRequest;
import fpoly.websitefpoly.request.SearchCategoryRequest;
import fpoly.websitefpoly.request.UpdateCategoryRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.CategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/", ""})
    public ResponeData<Page<CategoryDto>> search(@ModelAttribute SearchCategoryRequest searchCategoryRequest,
                                                 @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    categoryService.search(searchCategoryRequest, pageable));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @PostMapping("/")
    public ResponeData<CategoryDto> create(@RequestBody CreateCategoryRequest createCategoryRequest) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    categoryService.create(createCategoryRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @PutMapping("/{id}")
    public ResponeData<CategoryDto> update(@PathVariable("id") Long id, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    categoryService.update(id, updateCategoryRequest));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @GetMapping("/{id}")
    public ResponeData<CategoryDto> detailById(@PathVariable("id") Long id) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    categoryService.detail(id));
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error(e.toString());
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_CODE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE,
                    categoryService.delete(id));
        } catch (Exception e) {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, false);
        }
    }
}
