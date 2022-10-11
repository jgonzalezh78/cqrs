package com.manuel.store.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {
	
	public ProductLookupEntity findByProductIdOrTitle(String productId, String title);

}
