package com.promineotech.classManagementApi.service;

import java.security.Key;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import com.promineotech.classManagementApi.entity.Employees;
import com.promineotech.classManagementApi.util.AccountLevel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthenticationService {
	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
	@SuppressWarnings("deprecation")
	public boolean isCorrectAccountLevel(String jwt, Long account) {
		return new Long((Integer)Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwt)
				.getBody()
				.get("account"))
				.equals(account);
			
	}
	
	public String getToken(HttpServletRequest request) throws Exception {
		String header = request.getHeader("Authorization");
		if(header == null) {
			throw new Exception("request contains no token.");
		}
		
		return header.replaceAll("Bearer ", "");
	}
	
	public String generateToken(Employees emp) {
		

		String account = AccountLevel.TEACHER.toString();
		
		
		String jwt = Jwts.builder()
					.claim("role", account)
					.claim("employeeId", emp.getId())
					.setSubject("Class Management")
					.signWith(key)
					.compact();
		
//		String encodeKey = Base64.getEncoder().encodeToString(key.getEncoded());
//		byte[] decodeKey = Base64.getDecoder().decode(encodeKey);
//		String subject = Jwts.parser().setSigningKey(decodeKey).parseClaimsJws(jwt).getBody().getSubject().toString();
		return jwt;
		
	}


}
