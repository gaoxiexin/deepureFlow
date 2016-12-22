package com.tasly.deepureflow.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IProductMapper;
import com.tasly.deepureflow.dao.IProductDao;
import com.tasly.deepureflow.domain.product.Product;
import com.tasly.deepureflow.domain.product.ProductExample;
import com.tasly.deepureflow.domain.product.ProductExample.Criteria;
import com.tasly.deepureflow.domain.user.User;

@Service("productDao") 
public class ProductDaoImpl implements IProductDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(ProductDaoImpl.class.getName());
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	public void save(final Product product) {
		redisTemplate.execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection conn)
					throws DataAccessException {
				conn.set(
						redisTemplate.getStringSerializer().serialize(
								"product.skuid."+ product.getCode()),
						redisTemplate.getStringSerializer().serialize(
								product.getName()));
				return null;
			}

		});
	}

	public Product read(final String skuId) {
		return redisTemplate.execute(new RedisCallback<Product>() {

			public Product doInRedis(RedisConnection conn)
					throws DataAccessException {
				byte[] key=redisTemplate.getStringSerializer().serialize("product.skuid."+skuId);
				
				if(conn.exists(key)){
					byte[] value=conn.get(key);
					String skuName=redisTemplate.getStringSerializer().deserialize(value);
					
					Product product=new Product();
					product.setCode(skuId);
					product.setName(skuName);
					
					return product;
				}
				
				return null;
			}
		});
	}

	public void delete(final String skuId) {
		redisTemplate.execute(new RedisCallback<Object>() {  
	        public Object doInRedis(RedisConnection connection) {  
	            connection.del(redisTemplate.getStringSerializer().serialize(  
	                    "product.skuid." + skuId));  
	            return null;  
	        }  
	    });  
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findProductsByCategoryCode(List<String> ids) {
		if(CollectionUtils.isEmpty(ids)){
			return ListUtils.EMPTY_LIST;
		}
		
		IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
		ProductExample productExample=new ProductExample();

		Criteria criteria = productExample.createCriteria();
		productExample.or(criteria.andCategoryCodeIn(ids));

		return mapper.selectByExample(productExample);
	}

	@Override
	public List<Product> findProductForPage(PageBounds pageBounds) {
		Map<String, Object> params =new HashMap<String, Object>();  
		return sqlSession.selectList(IProductMapper.class.getName()+".productListForPage", params, pageBounds);
	}

	@Override
	public Product findProductById(String skuId) {
		IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);

		return mapper.selectByPrimaryKey(Integer.valueOf(skuId));
	}

	@Override
	public Product findProductByCode(String skuCode) {
		IProductMapper mapper=sqlSession.getMapper(IProductMapper.class);
		ProductExample example=new ProductExample();
		example.or().andCodeEqualTo(skuCode);
		List<Product> productList=mapper.selectByExample(example);
		return CollectionUtils.isNotEmpty(productList)?productList.get(0):null;
	}
	
	@Override
	public boolean deleteAllProduct() {
		IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
//		ProductExample productExample=new ProductExample();
//		Product product = new Product();
//		product.setActivateFlag(1);
		if(CollectionUtils.isNotEmpty(this.findAllProducts())){
			int count=productMapper.deleteByExample(new ProductExample());
			return count>0?true:false;
		}else{
			return true;
		}
		
	}
	
	
	@Override
	public boolean insertProduct(String code,String name,
			                     String evpUnit,String erpMinUnit,
			                     Date createAt,String createBy,
			                     String categoryCode,Integer unitQuantity,
			                     BigDecimal unitPrice){
		IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		createBy = currentUser.getUsername();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String time=format.format(date);
		
		try {
			createAt = format.parse(time);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Product product = new Product();
		product.setCode(code);
		product.setName(name);
		product.setProductType(0);
		product.setActivateFlag(1);
		product.setErpUnit(evpUnit);
		product.setErpMinUnit(erpMinUnit);
		product.setCreateAt(createAt);
		product.setCreateBy(createBy);
		product.setCategoryCode(categoryCode);
		product.setUnitQuantity(unitQuantity);
		product.setUnitPrice(unitPrice);
		
		int count=productMapper.insert(product);
		return count>=0?true:false;
	}

	@Override
	public List<Product> findAllProducts() {
		IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
		ProductExample productExample=new ProductExample();
		productExample.or().andActivateFlagEqualTo((byte) 1);
		List<Product> productList = productMapper.selectByExample(productExample);
		return productList;
	}

	@Override
	public boolean insertProduct(Product product) {
		IProductMapper productMapper = sqlSession.getMapper(IProductMapper.class);
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("user");
		product.setCreateBy(currentUser.getUsername()+"_excel");
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		try {
			product.setCreateAt(format.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		product.setProductType(0);
		product.setActivateFlag(1);
		int count=productMapper.insert(product);
		return count>=0?true:false;
	}

	

}
