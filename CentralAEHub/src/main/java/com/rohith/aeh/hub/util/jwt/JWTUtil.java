package com.rohith.aeh.hub.util.jwt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rohith.aeh.hub.authentication.dataobjects.UserProfile;
import com.rohith.aeh.hub.util.token.BearerToken;
import com.rohith.aeh.hub.util.token.RefreshToken;
import com.rohith.aeh.hub.util.token.RefreshTokenUtil;
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

	/**
	 * API creates a JWT String token from the BearerToken Object
	 * 
	 * @param token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createToken(BearerToken token) throws UnsupportedEncodingException {

		UserProfile profile = token.getUserProfile();
		return Jwts.builder().setExpiration(new Date(token.getExpiryDate())).claim("name", profile.getUserName())
				.claim("desg", profile.getDesignation()).claim("org", profile.getCompanyName())
				.claim("loc", profile.getAddress()).claim("scope", getScopes(profile.getScopes()))
				.claim("sec", token.getClientSecret()).claim("ref", getRefreshToken(token.getRefreshToken()))
				.signWith(SignatureAlgorithm.HS512, getSecret().getBytes(ENCODING)).compact();
	}

	private static String getSecret() {
		return key.toString();
	}
	
	/**
	 * API creates a Beartoken Object from the JWT token String
	 * 
	 * @param jwt
	 * @return
	 * @throws ExpiredJwtException
	 * @throws UnsupportedJwtException
	 * @throws MalformedJwtException
	 * @throws SignatureException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static BearerToken parseToken(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
	
		Jws<Claims> claims = Jwts.parser().setSigningKey(getSecret().getBytes(ENCODING)).parseClaimsJws(jwt);
		BearerToken token = new BearerToken();
		token.setExpiryDate(getExpiryDate(claims));
		token.setClientSecret((String) claims.getBody().get("sec"));
		token.setUserProfile(getUserProfile(claims));
		token.setRefreshToken(buildRefreshToken(claims));
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
	/**
	 * Method maps the Scope Claims to a List<Scope> internal logic
	 * 
	 * @param claims
	 * @return
	 */
	// TODO Internal Implementation is a little clumsy
	// Need to check it
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

	/**
	 * Method is called to get the Refresh Token to be appended the JWT token
	 * 
	 * @param refreshToken
	 * @return
	 */
	private static String getRefreshToken(RefreshToken refreshToken) {

		return RefreshTokenUtil.getRefreshToken(refreshToken);
	}
    
	/**
	 * Method builds the RefreshToken from String value
	 * @param claims
	 * @return
	 */
	private static RefreshToken buildRefreshToken(Jws<Claims> claims) {

		return RefreshTokenUtil.parseRefreshToken((String) claims.getBody().get("ref"));
	}
	
	/**
	 * API for get a delimiter separated scopes from a list of scope objects
	 * 
	 * @param list
	 * @return
	 */
	private static String getScopes(List<Scope> list) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			builder.append(list.get(i).toScopeString());
			if (i != list.size() - 1) {
				builder.append("-");
			}
		}
		return builder.toString();
	}
}
