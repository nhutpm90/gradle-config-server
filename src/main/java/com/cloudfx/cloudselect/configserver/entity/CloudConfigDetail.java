package com.cloudfx.cloudselect.configserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cloud_config_detail")
@Getter
@Setter
@NoArgsConstructor
public class CloudConfigDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "KEY", nullable = false)
	private String key;

	@Column(name = "VALUE", nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLOUD_CONFIG_ID", nullable = false)
	@JsonIgnore
	private CloudConfig cloudConfig;

	public CloudConfigDetail(String key, String value, CloudConfig cloudConfig) {
		this.key = key;
		this.value = value;
		this.cloudConfig = cloudConfig;
	}
}