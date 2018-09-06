package com.cloudfx.cloudselect.configserver.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloudfx.cloudselect.configserver.entity.CloudConfig;
import com.cloudfx.cloudselect.configserver.entity.CloudConfigDetail;

public interface CloudConfigService {

	public List<CloudConfig> getAllConfigs();
	
	public CloudConfig getConfig(Integer id);
	
	public CloudConfigDetail getConfigDetail(Integer id);
	
	public boolean updateConfig(Integer id, CloudConfig cloudConfig);
	
	public boolean updateConfigDetail(Integer id, CloudConfigDetail configDetail);
	
	public boolean syncToFile(CloudConfig config);
	
	public boolean syncToFiles();

	public boolean syncToDb();
	
	public boolean uploadConfigs(MultipartFile[] files);
}
