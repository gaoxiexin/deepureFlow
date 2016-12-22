package com.tasly.deepureflow.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.deepureflow.client.IHierarchyMapper;
import com.tasly.deepureflow.dao.IHierarchyDao;
import com.tasly.deepureflow.domain.system.Hierarchy;
import com.tasly.deepureflow.domain.system.HierarchyExample;
import com.tasly.deepureflow.enums.DeleteStatus;

@Repository("hierarchyDao")
public class HierarchyDaoImpl implements IHierarchyDao {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(HierarchyDaoImpl.class
			.getName());
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	@Override
	public List<Hierarchy> findAllHierarchy() {
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		HierarchyExample hierarchyExample = new HierarchyExample();
		hierarchyExample.or().andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Hierarchy> hierarchyList = hierarchyMapper
				.selectByExample(hierarchyExample);
		return hierarchyList;
	}

	@Override
	public List<Hierarchy> findHierarchyForPage(String hierarchyName,PageBounds pageBounds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hierarchyName", hierarchyName);
		return sqlSession.selectList(IHierarchyMapper.class.getName()
				+ ".hierarchyListForPage", params, pageBounds);
	}

	@Override
	public boolean insertHierarchy(String hierarchyName, String hierarchyNick) {
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);

		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setHierarchyName(hierarchyName);
		hierarchy.setHierarchyNick(hierarchyNick);
		hierarchy.setIsDelete(DeleteStatus.NODELETE.getValue());
		int count = hierarchyMapper.insert(hierarchy);
		return count > 0 ? true : false;
	}

	@Override
	public boolean deleteHierarchyByArray(Integer[] hierarchyList) {
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		HierarchyExample hierarchyExample=new HierarchyExample();
		hierarchyExample.or().andHierarchyIdIn(Arrays.asList(hierarchyList));
		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setIsDelete(1);
		
		int count=hierarchyMapper.updateByExampleSelective(hierarchy, hierarchyExample);
		return count>0?true:false;
	}

	@Override
	public boolean editHierarchy(Integer hierarchyId, String hierarchyName,
			String hierarchyNick) {
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setHierarchyId(hierarchyId);
		hierarchy.setHierarchyName(hierarchyName);
		hierarchy.setHierarchyNick(hierarchyNick);
		//hierarchy.setIsDelete(DeleteStatus.NODELETE.getValue());
		int count=hierarchyMapper.updateByPrimaryKeySelective(hierarchy);
		return count>0?true:false;
	}
	
	@Override
	public List<Hierarchy> queryHierarchyByName(String hierarchyName,PageBounds pageBounds){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hierarchyName", hierarchyName);
		return sqlSession.selectList(IHierarchyMapper.class.getName()
				+ ".queryHierarchyByName", params, pageBounds);
		
	}

	@Override
	public Hierarchy findHierarchyByName(String hierarchyName) {
		HierarchyExample hierarchyExample=new HierarchyExample();
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		hierarchyExample.or().andHierarchyNameEqualTo(hierarchyName).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
		List<Hierarchy> hierarchyList = hierarchyMapper.selectByExample(hierarchyExample);
		if(CollectionUtils.isNotEmpty(hierarchyList)){
			return hierarchyList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Hierarchy findHierarchyById(String hierarchyId) {
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		
		return hierarchyMapper.selectByPrimaryKey(Integer.parseInt(hierarchyId));
	}

	@Override
	public boolean validateName(String hierarchyName, String hierarchyId) {
		HierarchyExample hierarchyExample=new HierarchyExample();
		IHierarchyMapper hierarchyMapper = sqlSession
				.getMapper(IHierarchyMapper.class);
		if(hierarchyId!=""&&hierarchyId!=null){
			hierarchyExample.or().andHierarchyNameEqualTo(hierarchyName).andHierarchyIdNotEqualTo(Integer.valueOf(hierarchyId)).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
			List<Hierarchy> hierarchyList = hierarchyMapper.selectByExample(hierarchyExample);
			if(CollectionUtils.isNotEmpty(hierarchyList)){
				return false;
			}else{
				return true;
			}
		}else{
			hierarchyExample.or().andHierarchyNameEqualTo(hierarchyName).andIsDeleteEqualTo(DeleteStatus.NODELETE.getValue());
			List<Hierarchy> hierarchyList = hierarchyMapper.selectByExample(hierarchyExample);
			if(CollectionUtils.isNotEmpty(hierarchyList)){
				return false;
			}else{
				return true;
			}
		}
		
	}

}
