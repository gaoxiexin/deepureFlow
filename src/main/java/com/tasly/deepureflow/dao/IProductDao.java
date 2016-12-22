package com.tasly.deepureflow.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.product.Product;

public interface IProductDao {
	 /** 
     * @param uid 
     * @param address 
     */  
    void save(Product product);  
  
    /** 
     * @param uid 
     * @return 
     */  
    Product read(String skuId);  
  
    /** 
     * @param uid 
     */  
    void delete(String skuId); 
    
    List<Product> findProductsByCategoryCode(List<String> ids);

	List<Product> findProductForPage(PageBounds pageBounds);

	Product findProductById(String skuId);

	Product findProductByCode(String skuCode);
	//产品导入功能
	public boolean deleteAllProduct();
	public boolean insertProduct(String code,String name,String evpUnit,
			                     String erpMinUnit,Date createAt,
			                     String createBy,String categoryCode,
			                     Integer unitQuantity,BigDecimal unitPrice);

	List<Product> findAllProducts();

	boolean insertProduct(Product product);
    
}
