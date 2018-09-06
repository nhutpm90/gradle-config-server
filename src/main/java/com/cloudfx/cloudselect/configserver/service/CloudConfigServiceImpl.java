package com.cloudfx.cloudselect.configserver.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudfx.cloudselect.configserver.dao.CloudConfigDAO;
import com.cloudfx.cloudselect.configserver.dao.CloudConfigDetailDAO;
import com.cloudfx.cloudselect.configserver.entity.CloudConfig;
import com.cloudfx.cloudselect.configserver.entity.CloudConfigDetail;
import com.cloudfx.cloudselect.configserver.utils.Utils;

@Service
public class CloudConfigServiceImpl implements CloudConfigService {

	@Value("${cloud.config.export.path}")
	private String cloudConfigExportPath;

	@Autowired
	private CloudConfigDAO cloudConfigDAO;
	
	@Autowired
	private CloudConfigDetailDAO cloudConfigDetailDAO;

	@Override
	public List<CloudConfig> getAllConfigs() {
		return cloudConfigDAO.findAll();
	}
	
	@Override
	public CloudConfig getConfig(Integer id) {
		return this.cloudConfigDAO.findById(id).orElse(null);
	}
	
	@Override
	public CloudConfigDetail getConfigDetail(Integer id) {
		return this.cloudConfigDetailDAO.findById(id).orElse(null);
	}
	
	@Override
	public boolean updateConfig(Integer id, CloudConfig cloudConfig) {
		boolean result = false;
		try {
			CloudConfig config = this.getConfig(id);
			if(config != null) {
				// update config
				config.setName(cloudConfig.getName());
				this.cloudConfigDAO.save(config);
				result = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean updateConfigDetail(Integer id, CloudConfigDetail configDetail) {
		boolean result = false;
		try {
			CloudConfigDetail configDetailToSave = this.getConfigDetail(id);
			if(configDetailToSave != null) {
				// update config detail
				configDetailToSave.setKey(configDetail.getKey());
				configDetailToSave.setValue(configDetail.getValue());
				this.cloudConfigDetailDAO.save(configDetailToSave);
				result = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean syncToFile(CloudConfig config) {
		boolean result = false;
		try {
			String name = config.getName();
			Map<String, String> properties = new HashMap<String, String>();
			Set<CloudConfigDetail> cloudConfigDetails = config.getCloudConfigDetails();
			for (CloudConfigDetail configDetail : cloudConfigDetails) {
				String key = configDetail.getKey();
				String value = configDetail.getValue();
				properties.put(key, value);
			}
			Utils.writeProperty(cloudConfigExportPath, name, new TreeMap<String, String>(properties));
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean syncToFiles() {
		boolean result = false;
		boolean cleanConfigurationFolder = Utils.deleteAllFiles(cloudConfigExportPath);
		if(!cleanConfigurationFolder) {
			List<CloudConfig> configs = cloudConfigDAO.findAll();
			for(CloudConfig config : configs) {
				syncToFile(config);
			}
			result = true;
		}
		return result;
	}
	
	@Override
	public boolean syncToDb() {
		boolean result = false;
		try {
			clearAllConfigs();
			List<File> files = Utils.getAllFiles(cloudConfigExportPath);
			for(File file : files) {
				String fileName = file.getName();
				CloudConfig cloudConfig = new CloudConfig(fileName);
				cloudConfigDAO.save(cloudConfig);
				
				Map<String, String> properties = Utils.readProperty(file);
				for(Map.Entry<String, String> entry : properties.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					CloudConfigDetail cloudConfigDetail = new CloudConfigDetail(key, value, cloudConfig);
					cloudConfigDetailDAO.save(cloudConfigDetail);
				}
			}
			result = true;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean uploadConfigs(MultipartFile[] files) {
		boolean result = false;
		try {
			for (MultipartFile file : files) {
				uploadConfig(file);
			}
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	private boolean uploadConfig(MultipartFile file) {
		boolean result = false;
		Path fileStorageLocation = Paths.get(cloudConfigExportPath).toAbsolutePath().normalize();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = fileStorageLocation.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	private void clearAllConfigs() {
		cloudConfigDetailDAO.deleteAll();
		cloudConfigDAO.deleteAll();
	}
}