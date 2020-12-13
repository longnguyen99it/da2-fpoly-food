package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.CategoryDto;
import fpoly.websitefpoly.entity.Category;
import fpoly.websitefpoly.repository.CategoryRepository;
import fpoly.websitefpoly.request.CreateCategoryRequest;
import fpoly.websitefpoly.request.SearchCategoryRequest;
import fpoly.websitefpoly.request.UpdateCategoryRequest;
import fpoly.websitefpoly.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(EntityManagerFactory entityManagerFactory,
                               EntityManager entityManager,
                               CategoryRepository categoryRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManager;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryDto> search(SearchCategoryRequest seachCategoryRequest, Pageable pageable) throws Exception {
        Page<Category> categoryPage = categoryRepository.findAllByStatus("A",pageable);
        Page<CategoryDto> categoryDtoPage = categoryPage.map(new Function<Category, CategoryDto>() {
            @Override
            public CategoryDto apply(Category category) {
                CategoryDto categoryDto = ModelMapperUtils.map(category, CategoryDto.class);
                return categoryDto;
            }
        });
        return categoryDtoPage;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryDto create(CreateCategoryRequest createCategoryRequest) throws Exception {
        Category category = ModelMapperUtils.map(createCategoryRequest, Category.class);
        category.setStatus("A");
        Category save = categoryRepository.save(category);
        CategoryDto categoryDto = ModelMapperUtils.map(save, CategoryDto.class);
        return categoryDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryDto update(Long id, UpdateCategoryRequest updateCategoryRequest) throws Exception {
        Category category = categoryRepository.findById(id).get();
        if (category == null) {
            throw new Exception("Not Fount");
        }
        category.setCategoryName(updateCategoryRequest.getCategoryName());
        category.setImage(updateCategoryRequest.getImage());
        category.setStatus("A");
        Category save = categoryRepository.save(category);
        return ModelMapperUtils.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto detail(Long id) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new Exception("Not Found");
        }
        return ModelMapperUtils.map(category.get(), CategoryDto.class);
    }

    @Override
    public Boolean delete(Long id) throws Exception {
        Category category = categoryRepository.findByIdAndStatus(id, "A");
        if (category == null) {
            return false;
        }
        category.setStatus("D");
        categoryRepository.save(category);
        return true;
    }
}
