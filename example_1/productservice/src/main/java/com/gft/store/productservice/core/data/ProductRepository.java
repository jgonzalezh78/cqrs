package com.gft.store.productservice.core.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.store.productservice.core.data.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity,String> {
    public List<ProductEntity> findProductsByProductId(String productId);
}
