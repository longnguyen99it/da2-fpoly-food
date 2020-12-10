package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.common.ModelMapperUtils;
import fpoly.websitefpoly.dto.ProductDto;
import fpoly.websitefpoly.entity.Category;
import fpoly.websitefpoly.entity.Product;
import fpoly.websitefpoly.entity.ProductTopping;
import fpoly.websitefpoly.entity.Topping;
import fpoly.websitefpoly.repository.CategoryRepository;
import fpoly.websitefpoly.repository.ProductRepository;
import fpoly.websitefpoly.repository.ProductToppingRepository;
import fpoly.websitefpoly.request.CreateProductRequest;
import fpoly.websitefpoly.request.SearchProductRequest;
import fpoly.websitefpoly.request.UpdateProductRequest;
import fpoly.websitefpoly.service.ProductService;
import org.apache.commons.lang.StringUtils;
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

/**
 * @author Nguyen Hoang Long on 11/5/2020
 * @created 11/5/2020
 * @project website-fpoly
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductToppingRepository productToppingRepository;

    @Override
    public Page<ProductDto> search(SearchProductRequest searchProductRequest, Pageable pageable) throws Exception {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Object[] queryObjs = this.searchProductRootPersist(cb, query, searchProductRequest);
        Root<Product> root = (Root<Product>) queryObjs[0];
        query.select(root);
        query.where((Predicate[]) queryObjs[1]);

        TypedQuery<Product> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<Product> objects = typedQuery.getResultList();
        List<ProductDto> productDtoList = ModelMapperUtils.mapAll(objects, ProductDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(Product.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(productDtoList, pageable, total);
    }

    private Object[] searchProductRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, SearchProductRequest resource) {
        final Root<Product> rootPersist = query.from(Product.class);
        final List<Predicate> predicates = new ArrayList<>();

        if (resource.getProductName() != null
                && !StringUtils.isEmpty(resource.getProductName().trim())) {
            predicates.add(cb.and(cb.like(cb.upper(rootPersist.get("productName")), "%" + resource.getProductName().toUpperCase() + "%")));
        }

        predicates.add(cb.and(cb.equal(rootPersist.<String>get("status"), "A")));

        if (resource.getCategoryId() != null
                && !StringUtils.isEmpty(resource.getCategoryId().toString())) {
            Category category = categoryRepository.findByIdAndStatus(resource.getCategoryId(), "A");
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("category"), category)));
        }

        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDto created(CreateProductRequest createProductRequest) throws Exception {
        Category category = categoryRepository.findByIdAndStatus(createProductRequest.getCategoryId(), "A");
        Product product = ModelMapperUtils.map(createProductRequest, Product.class);
        product.setId(null);
        product.setCategory(category);
        Product save = productRepository.save(product);
        return ModelMapperUtils.map(save, ProductDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDto update(Long id, UpdateProductRequest updateProductRequest) throws Exception {
        Product product = productRepository.findByIdAndStatus(id, "A");
        if (product == null) {
            throw new Exception("Không tìm thấy product");
        }
        Category category = categoryRepository.findByIdAndStatus(updateProductRequest.getCategoryId(), "A");
        if (category == null) {
            throw new Exception("Không tìm thấy category");
        }
        product = setUpdate(product, updateProductRequest);
        product.setCategory(category);
        Product update = productRepository.save(product);
        return ModelMapperUtils.map(update, ProductDto.class);
    }

    private Product setUpdate(Product product, UpdateProductRequest updateProductRequest) {
        if (!StringUtils.isEmpty(updateProductRequest.getProductName())) {
            product.setProductName(updateProductRequest.getProductName());
        }
        if (!StringUtils.isEmpty(updateProductRequest.getDescription())) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (!StringUtils.isEmpty(updateProductRequest.getImage())) {
            product.setImage(updateProductRequest.getImage());
        }
        if (!StringUtils.isEmpty(updateProductRequest.getPrice().toString())) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (!StringUtils.isEmpty(updateProductRequest.getWarehouses().toString())) {
            product.setWarehouses(updateProductRequest.getWarehouses());
        }
        if (!StringUtils.isEmpty(updateProductRequest.getUnit())) {
            product.setUnit(updateProductRequest.getUnit());
        }
        return product;
    }

    @Override
    public ProductDto detail(Long id) throws Exception {
        Product product = productRepository.findById(id).get();
        ProductDto productDto = ModelMapperUtils.map(product, ProductDto.class);
        List<ProductTopping> productToppingList = productToppingRepository.findAllByProductId(id);
        List<Topping> toppingList = new ArrayList<>();
        if (!productToppingList.isEmpty()) {
            for (ProductTopping productTopping : productToppingList) {
                toppingList.add(productTopping.getTopping());
            }
        }
        productDto.setToppingList(toppingList);
        return productDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleted(Long id) throws Exception {
        Product product = productRepository.findByIdAndStatus(id, "A");
        if (product != null) {
//            productEntity.setDeletedBy(Flag.userEntityFlag.getEmail());
            product.setStatus("D");
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
