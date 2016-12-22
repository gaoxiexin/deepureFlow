package com.tasly.deepureflow.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Office;
import com.tasly.deepureflow.util.DeepureResult;

public interface IOfficeService {
	public PageList<Office> findOfficeForPage(int curPageSize, int limit);

	public boolean addOffice(String officeId,String officeName, String hierarchyId);

	public boolean delOfficeByArray(Integer[] officeList);

	public boolean editOffice(Integer officeId, String officeName,
			String zoneId);

	public List<Office> queryAllOffice();
	
	public DeepureResult importExcel(String targetPath, MultipartFile upFile);

	public Integer findZoneIdById(String officeId);
}
