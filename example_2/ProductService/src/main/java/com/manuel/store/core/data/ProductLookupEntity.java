package com.manuel.store.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productlookup")
public class ProductLookupEntity {
	
	@Id
	private String productId;
	@Column(unique=true)
	private String title;

}
