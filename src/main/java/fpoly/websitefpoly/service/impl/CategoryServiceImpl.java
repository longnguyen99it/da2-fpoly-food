package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.CategoryDto;
import fpoly.websitefpoly.entity.Category;
import fpoly.websitefpoly.repository.CategoryRepository;
import fpoly.websitefpoly.request.CreateCategoryRequest;
import fpoly.websitefpoly.request.SearchCategoryRequest;
import fpoly.websitefpoly.request.UpdateCategoryRequest;
import fpoly.websitefpoly.response.ResponeData;
import fpoly.websitefpoly.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponeData<Page<CategoryDto>> search(SearchCategoryRequest seachCategoryRequest, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Object[] queryObjs = this.createNotificationRootPersist(cb, query, seachCategoryRequest);
        Root<Category> root = (Root<Category>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<Category> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<Category> objects = typedQuery.getResultList();
        List<CategoryDto> notificationDtos = ModelMapperUtils.mapAll(objects, CategoryDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(Category.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, new PageImpl<>(notificationDtos, pageable, total));
    }

    private Object[] createNotificationRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchCategoryRequest resource) {
        final Root<Category> rootPersist = query.from(Category.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (resource.getCategoryName() != null
                && !StringUtils.isEmpty(resource.getCategoryName())) {
            predicates.add(cb.and(cb.or(cb.like(cb.upper(rootPersist.get("categoryName")), "%" + resource.getCategoryName() + "%"))));
        }
        predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), "A")));
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponeData<CategoryDto> created(CreateCategoryRequest createCategoryRequest) throws Exception {
        Category category = new Category();
        BeanUtils.copyProperties(createCategoryRequest, category);
        Category storedCategory = categoryRepository.save(category);

        CategoryDto returnValue = new CategoryDto();
        BeanUtils.copyProperties(storedCategory, returnValue);
        if (returnValue != null) {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, returnValue);
        } else {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, null);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponeData<CategoryDto> updated(Long id, UpdateCategoryRequest updateCategoryRequest) throws Exception {
        CategoryDto returnValue = new CategoryDto();
        Category category = categoryRepository.findById(id).get();
        BeanUtils.copyProperties(updateCategoryRequest, category);
        Category updateCategory = categoryRepository.save(category);
        BeanUtils.copyProperties(updateCategory, returnValue);
        if (returnValue != null) {
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, returnValue);
        } else {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, returnValue);
        }
    }

    @Override
    public ResponeData<CategoryDto> detail(Long id) throws Exception {
        CategoryDto returnValue = new CategoryDto();
        if (id != null) {
            Category category = categoryRepository.findById(id).get();
            BeanUtils.copyProperties(category, returnValue);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.ERROR_MESSAGE, returnValue);
        } else {
            return new ResponeData<>(AppConstant.ERROR_CODE, AppConstant.ERROR_MESSAGE, null);
        }
    }

    @Override
    public ResponeData<Boolean> deleted(Long id) throws Exception {
        Category category = categoryRepository.findByIdAndStatus(id, "A");
        if (category != null) {
            category.setStatus("D");
            categoryRepository.save(category);
            return new ResponeData<>(AppConstant.SUCCESSFUL_CODE, AppConstant.SUCCESSFUL_MESAGE, true);
        }
        return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, false);
    }
}
