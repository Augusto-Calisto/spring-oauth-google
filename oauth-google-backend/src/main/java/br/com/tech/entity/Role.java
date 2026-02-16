package br.com.tech.entity;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "roles", schema = "public")
@Data
@Builder
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	public Role() {}
	
	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + name;
	}
}