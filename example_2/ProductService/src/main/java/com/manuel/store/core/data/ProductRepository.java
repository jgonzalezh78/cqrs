package com.manuel.store.core.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

	public List<ProductEntity> findProductsByProductId(String productId);
	
	public List<ProductEntity> findProductsByProductIdOrTitle(String productId, String title);
}
