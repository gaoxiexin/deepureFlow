package com.tasly.deepureflow.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.deepureflow.domain.system.Station;
import com.tasly.deepureflow.util.DeepureResult;

public interface IStationService {
	List<Station> queryAllStation();

	public Integer findOfficeIdById(Integer stationId);
	
	public DeepureResult importExcel(String targetPath, MultipartFile upFile);

	PageList<Station> findStationForPage(int curPageSize, int limit);

	List<Station> queryStationByRole();

	DeepureResult validateStation(String userId,String stationId);
}
