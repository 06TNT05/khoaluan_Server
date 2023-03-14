/**
 * Time creation: Feb 7, 2023, 6:50:04 PM
 *
 * Pakage name: com.exam.common
 */
package com.exam.common;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.exam.model.LecturerModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Tien Tran
 *
 * class JwtTokenUtil
 */
@Component
public class JwtTokenUtil {
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
	
    private String SECRET_KEY = "abcdefghijklmnOPQRSTUVWXYZ";
     
    public String generateAccessToken(LecturerModel account) {
        
    	return Jwts.builder()
                .setSubject(account.getEmail())
                .setIssuer("ExpiredToken")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();    
    }
    
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            //LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            //LOGGER.error("Signature validation failed");
        }
         
        return false;
    }
     
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
