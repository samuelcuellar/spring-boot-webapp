package com.skynet.example.config;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.skynet.example.constants.Constants;
import com.skynet.example.controller.dto.TokenResponse;
import com.skynet.example.exception.SessionExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationHandler {

  public static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationHandler.class);

  static final long EXPIRATIONTIME = 86_400_000; // 1 day
  static final String SECRET = "SecretKeyWord";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";
  static final String CLAIM_NAME = "name";
  static final String CLAIM_SCOPE = "scope";
  static final String ADMIN_SCOPE = "ADMIN";
  static final String USER_SCOPE = "USER";

  public static TokenResponse generateToken(String userId, String userName, boolean isAdmin) {
    String JWT = Jwts.builder()
        .setSubject(userId.toString())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .claim(CLAIM_NAME, userName)
        .claim(CLAIM_SCOPE, isAdmin ? ADMIN_SCOPE : USER_SCOPE)
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    return TokenResponse.builder().access_token(JWT).token_type(TOKEN_PREFIX).build();
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    try {
      if (token != null) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();
        String user = claims.getSubject();
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (claims.get(CLAIM_SCOPE).equals(ADMIN_SCOPE)) {
          authorities.add(new SimpleGrantedAuthority(Constants.ROLE_ADMIN));
        }
        authorities.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
        return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities): null;
      }
      return null;
    } catch (ExpiredJwtException e) {
      logger.info("Session expired for token {}", token);
      throw new SessionExpiredException();
    }
  }
}
