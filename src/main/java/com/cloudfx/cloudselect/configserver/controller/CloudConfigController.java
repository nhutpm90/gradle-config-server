package com.cloudfx.cloudselect.configserver.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudfx.cloudselect.configserver.dto.ApiResponseDto;
import com.cloudfx.cloudselect.configserver.entity.CloudConfig;
import com.cloudfx.cloudselect.configserver.entity.CloudConfigDetail;
import com.cloudfx.cloudselect.configserver.service.CloudConfigService;

@RestController
public class CloudConfigController {

	@Autowired
	private CloudConfigService cloudConfigService;
	
	@GetMapping(value = "/api/configs")
	public List<CloudConfig> getAllConfigs() {
		return cloudConfigService.getAllConfigs();
	}

	@GetMapping(value = "/api/configs/{id}")
	public CloudConfig getConfig(@PathVariable("id") Integer id) {
		return cloudConfigService.getConfig(id);
	}
	
	@PostMapping(value = "/api/configs/{id}")
	public ApiResponseDto updateConfig(@PathVariable("id") Integer id,
			@RequestBody CloudConfig config) {
		boolean result = cloudConfigService.updateConfig(id, config);
		return new ApiResponseDto(result);
	}
	
	@GetMapping(value = "/api/configs/{id}/details")
	public Set<CloudConfigDetail> getConfigDetails(@PathVariable("id") Integer id) {
		return cloudConfigService.getConfig(id).getCloudConfigDetails();
	}
	
	@PostMapping(value = "/api/config-details/{id}")
	public ApiResponseDto updateConfigDetail(@PathVariable("id") Integer id,
			@RequestBody CloudConfigDetail configDetail) {
		boolean result = cloudConfigService.updateConfigDetail(id, configDetail);
		return new ApiResponseDto(result);
	}
	
	/**
	 * read all configuration in database
	 * remove all existing configurations in the folder first
	 * then export into folder ${cloud.config.export.path} 
	 */
	@PostMapping(value = "/api/configs/sync-configs")
	public ApiResponseDto syncToFiles() {
		boolean result = cloudConfigService.syncToFiles();
		return new ApiResponseDto(result);
	}
	
	/**
	 * export a specific configuration from database into folder ${cloud.config.export.path} 
	 */
	@PostMapping(value = "/api/configs/sync-configs/{id}")
	public ApiResponseDto syncToFile(@PathVariable("id") Integer id) {
		boolean result = cloudConfigService.syncToFile(cloudConfigService.getConfig(id));
		return new ApiResponseDto(result);
	}
	
	/**
	 * read all configuration files in folder ${cloud.config.export.path}
	 * remove all existing configurations in DB first
	 * then insert into database
	 */
	@PostMapping(value = "/api/configs/sync-to-db")
	public ApiResponseDto syncToDb() {
		boolean result = cloudConfigService.syncToDb();
		return new ApiResponseDto(result);
	}
	
	/**
	 * upload configuration files into folder ${cloud.config.export.path}
	 * @param files
	 */
	@PostMapping("/api/configs/upload")
	public ApiResponseDto uploadFiles(@RequestParam("files") MultipartFile[] files) {
		boolean result = cloudConfigService.uploadConfigs(files);
		return new ApiResponseDto(result);
	}
}
