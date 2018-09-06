package com.cloudfx.cloudselect.configserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudfx.cloudselect.configserver.entity.CloudConfigDetail;

@Repository
public interface CloudConfigDetailDAO extends JpaRepository<CloudConfigDetail, Integer> {

}