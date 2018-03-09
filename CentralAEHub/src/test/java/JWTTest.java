import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.rohith.aeh.hub.util.date.AEHHubDateUtil;
import com.rohith.aeh.hub.util.jwt.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTest {

	public static void main(String[] args) throws UnsupportedEncodingException {

//		String jwt = Jwts.builder()
//		  .setSubject("users/TzMUocMF4p")
//		  .setExpiration(new Date(AEHHubDateUtil.addMinutes(System.currentTimeMillis(), 10)))
//		  .claim("name", "Robert Token Man")
//		  .claim("scope", "self groups/admins")
//		  .signWith(
//		    SignatureAlgorithm.HS256,
//		    "secret".getBytes("UTF-8")
//		  )
//		  .compact();
//		
//		
//		Jws<Claims> claims = Jwts.parser()
//				  .setSigningKey("secret".getBytes("UTF-8"))
//				  .parseClaimsJws(jwt);
//				String scope = (String) claims.getBody().get("scope");
//				
//		
//		System.out.println(scope);

	
		System.out.println(JWTUtil.createToken(null));
		
		JWTUtil.parseToken(JWTUtil.createToken(null));
		
	
	}
}
