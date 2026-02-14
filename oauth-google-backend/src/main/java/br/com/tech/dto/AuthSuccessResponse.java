package br.com.tech.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthSuccessResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String familyName;
	private String email;
	private String urlPicture;
	private String token;
	private String role;
}