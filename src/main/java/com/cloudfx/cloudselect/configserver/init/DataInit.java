package com.cloudfx.cloudselect.configserver.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cloudfx.cloudselect.configserver.dao.CloudConfigDAO;
import com.cloudfx.cloudselect.configserver.dao.CloudConfigDetailDAO;

@Component
public class DataInit implements ApplicationRunner {


	@Autowired
	private CloudConfigDAO cloudConfigDAO;
	
	@Autowired
	private CloudConfigDetailDAO cloudConfigDetailDAO;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	}

}