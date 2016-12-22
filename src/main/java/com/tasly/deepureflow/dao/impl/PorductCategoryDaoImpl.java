package com.tasly.deepureflow.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IProductCategoryMapper;
import com.tasly.deepureflow.dao.IProductCategoryDao;
import com.tasly.deepureflow.domain.product.ProductCategory;
import com.tasly.deepureflow.domain.product.ProductCategoryExample;
import com.tasly.deepureflow.domain.product.ProductCategoryExample.Criteria;
import com.tasly.deepureflow.domain.user.User;

@Repository("porductCategoryDao")
public class PorductCategoryDaoImpl implements IProductCategoryDao {
	
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(PorductCategoryDaoImpl.class.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public ProductCategory findOneById(Integer id) {
		IProductCategoryMapper mapper = sqlSession.getMapper(IProductCategoryMapper.class);
		ProductCategoryExample productCategoryExample=new ProductCategoryExample();

		Criteria criteria = productCategoryExample.createCriteria();
		productCategoryExample.or(criteria.andIdEqualTo(id));

		List<ProductCategory> result=mapper.selectByExample(productCategoryExample);
		
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findByIds(List<String> ids) {
		if(CollectionUtils.isEmpty(ids)){
			return ListUtils.EMPTY_LIST;
		}
		
		List<Integer> categoryIds=new LinkedList<Integer>();
		for(String categoryId:ids){
			categoryIds.add(Integer.parseInt(categoryId));
		}
		
		IProductCategoryMapper mapper = sqlSession.getMapper(IProductCategoryMapper.class);
		ProductCategoryExample productCategoryExample=new ProductCategoryExample();

		Criteria criteria = productCategoryExample.createCriteria();
		productCategoryExample.or(criteria.andIdIn(categoryIds));

		return mapper.selectByExample(productCategoryExample);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findManyByLayer(Integer layer,Integer activeFlag){
		if(layer<0||activeFlag<0){
			return ListUtils.EMPTY_LIST;
		}
		
		IProductCategoryMapper mapper = sqlSession.getMapper(IProductCategoryMapper.class);
		ProductCategoryExample productCategoryExample=new ProductCategoryExample();

		productCategoryExample.or().andLayerEqualTo(layer).andActivateFlagEqualTo(activeFlag);
		
		return mapper.selectByExample(productCategoryExample);
	}

	@Override
	public List<ProductCategory> findProductCategoryForPage(
			PageBounds pageBounds) {
		Map<String, Object> params =new HashMap<String, Object>();  
		return sqlSession.selectList(IProductCategoryMapper.class.getName()+".productCategoryListForPage", params, pageBounds);
	}
	@Override
	public String selectDaoParentId(String code){
		IProductCategoryMapper productCategoryMapper = sqlSession.getMapper(IProductCategoryMapper.class);
		return productCategoryMapper.selectParentId(code);
		
	}
	//产品组入功能 （删除）
	@Override
	public boolean deleteAllProductCategory() {
		IProductCategoryMapper productCategoryMapper = sqlSession.getMapper(IProductCategoryMapper.class);
		ProductCategoryExample productCategoryExample=new ProductCategoryExample();
		productCategoryExample.or().andIdGreaterThan(7);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setActivateFlag(0);
		int count = 0;
		if(CollectionUtils.isNotEmpty(this.findManyByLayer(1, 0))){
			count = productCategoryMapper.deleteByExample(productCategoryExample);
		}else{
			return true;
		}
		
		//int count=productCategoryMapper.updateByExampleSelective(productCategory, productCategoryExample);
		return count>0?true:false;
	}

	@Override
	public boolean addProductCategory(ProductCategory productCategory) {
		IProductCategoryMapper productCategoryMapper = sqlSession.getMapper(IProductCategoryMapper.class);
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		productCategory.setCreateBy(currentUser.getUsername()+"_excel");
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time=format.format(new Date());
		
		try {
			productCategory.setCreateAt(format.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		productCategory.setLayer(1);
		productCategory.setActivateFlag(0);
		int count=productCategoryMapper.insert(productCategory);
		return count>=0?true:false;
	}
	

}
