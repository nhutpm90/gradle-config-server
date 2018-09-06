package com.cloudfx.cloudselect.configserver.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cloud_config")
@Getter
@Setter
@NoArgsConstructor
public class CloudConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cloudConfig")
	private Set<CloudConfigDetail> cloudConfigDetails = new HashSet<CloudConfigDetail>(0);

	public CloudConfig(String name) {
		this.name = name;
	}
}