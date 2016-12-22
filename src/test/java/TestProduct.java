import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tasly.deepureflow.dao.IProductCategoryDao;
import com.tasly.deepureflow.domain.product.ProductCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class TestProduct {
	
	private static Logger logger = Logger.getLogger(TestProduct.class);
	@Resource
	private IProductCategoryDao productCategoryDao = null;
	
	@Test  
    public void crud() {  
		List<Integer> ids=new LinkedList<Integer>();
		ids.add(11);
		ids.add(12);
//		List<ProductCategory> productCategoreList= productCategoryDao.findByIds(ids);
		
//		for(ProductCategory productCategory:productCategoreList){
//			System.out.println(productCategory);
//		}
    }  
}
