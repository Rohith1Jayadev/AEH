package com.rohith.aeh.hub.util.jwt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.KeyGenerator;

import com.rohith.aeh.hub.authentication.dataobjects.UserProfile;
import com.rohith.aeh.hub.util.token.BearerToken;
import com.rohith.aeh.hub.util.token.Scope;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTUtil {

	private static final String ENCODING = "UTF-8";

	private static final String key = "mysecret";

	public static String createToken(BearerToken token) throws UnsupportedEncodingException {
		
		UserProfile profile = token.getUserProfile();
		
		return Jwts.builder().setExpiration(new Date(token.getExpiryDate())).claim("name", profile.getUserName())
				.claim("desg", profile.getDesignation()).claim("org", profile.getCompanyName())
				.claim("loc", profile.getAddress()).claim("scope", getScopes(profile.getScopes()))
				.claim("sec", token.getClientSecret())
				.signWith(SignatureAlgorithm.HS512, getSecret().getBytes(ENCODING)).compact();
	}

	private static String getScopes(List<Scope> list) {
		StringBuilder builder = new StringBuilder();
		
		for (int i=0;i<list.size();i++) {
			
			builder.append(list.get(i).toScopeString());
			
			if(i!=list.size()-1){
				builder.append("-");
			}
			
		}
		return builder.toString();
	}

	private static Key init() {
		
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyGenerator.generateKey();
	}

	private static String getSecret() {
		return key.toString();
	}

	public static BearerToken parseToken(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
		Jws<Claims> claims = Jwts.parser().setSigningKey(getSecret().getBytes(ENCODING)).parseClaimsJws(jwt);
		BearerToken token = new BearerToken();
		token.setExpiryDate(getExpiryDate(claims));
		token.setClientSecret((String) claims.getBody().get("sec"));
		token.setUserProfile(getUserProfile(claims));
		return token;
	}

	private static UserProfile getUserProfile(Jws<Claims> claims) {
	UserProfile profile = new UserProfile();
	profile.setAddress((String) claims.getBody().get("loc")).setCompanyName((String) claims.getBody().get("org"))
				.setDesignation((String) claims.getBody().get("desg"))
				.setUserName((String) claims.getBody().get("name"));
	List<Scope> scopes = parseScopes(claims);
	profile.setScopes(scopes);
	return profile;
	}

	private static List<Scope> parseScopes(Jws<Claims> claims) {
		String scopes = (String) claims.getBody().get("scope");
		String[] split = scopes.split("-");
		List<Scope> scopeList = new ArrayList<Scope>();
		for (int i = 0; i < split.length; i++) {
			Scope fromScopeString = Scope.fromScopeString(split[i]);
			if (null != fromScopeString) {
				scopeList.add(fromScopeString);
			}
		}
	return scopeList;
	}

	private static long getExpiryDate(Jws<Claims> claims) {
		return (null == claims.getBody().getExpiration()) ? 0L : claims.getBody().getExpiration().getTime();
	}

}
