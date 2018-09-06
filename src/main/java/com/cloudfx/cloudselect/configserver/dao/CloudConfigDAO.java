package com.cloudfx.cloudselect.configserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudfx.cloudselect.configserver.entity.CloudConfig;

@Repository
public interface CloudConfigDAO extends JpaRepository<CloudConfig, Integer> {

}