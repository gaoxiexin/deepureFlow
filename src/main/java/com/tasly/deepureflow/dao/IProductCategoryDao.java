package com.tasly.deepureflow.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.domain.product.ProductCategory;


public interface IProductCategoryDao {
	
	  ProductCategory findOneById(Integer id);
	  String selectDaoParentId(String code);
	  List<ProductCategory> findByIds(List<String> productCategoryIds);

	  List<ProductCategory> findManyByLayer(Integer layer, Integer activeFlag);

	  List<ProductCategory> findProductCategoryForPage(PageBounds pageBounds);
	  
	  //产品组导入功能（删除）
	  public boolean deleteAllProductCategory();
	  //添加
	  public boolean addProductCategory(ProductCategory productCategory);

}
