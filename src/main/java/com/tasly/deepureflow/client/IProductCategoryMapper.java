package com.tasly.deepureflow.client;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.domain.product.ProductCategoryExample;

public interface IProductCategoryMapper {
    int countByExample(ProductCategoryExample example);

    int deleteByExample(ProductCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    List<ProductCategory> selectByExample(ProductCategoryExample example);

    ProductCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);
    public String selectParentId(String code);
    
}