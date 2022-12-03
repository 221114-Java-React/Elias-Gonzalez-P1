package com.revature.reimbursementSystem.services;

import com.revature.reimbursementSystem.dtos.responses.Principal;
import com.revature.reimbursementSystem.utils.JwtConfig;
import com.revature.reimbursementSystem.utils.customExceptions.InvalidUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class TokenService {
    private JwtConfig jwtConfig;

    public TokenService() {
        super();
    }

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal subject) {
        long now = System.currentTimeMillis();
        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(subject.getUser_id())
                .setIssuer("reimbursementSystem")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .setSubject(subject.getUsername())
                .claim("active", subject.getIs_active())
                .claim("role", subject.getRole_id())
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return tokenBuilder.compact();
    }

    public Principal extractRequesterDetails(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return new Principal(claims.getId(), claims.getSubject(),claims.get("active", Boolean.class), claims.get("role", String.class));
        } catch (Exception e) {
            return null;
        }
    }

    public static void validateEmployeeLogin(String token, Principal principal)throws InvalidUserException{
        if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
        if (principal == null) throw new InvalidUserException("Invalid token");
        if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
    }

    public static void validateAdministratorLogin(String token, Principal principal) throws InvalidUserException{
        if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
        if (principal == null) throw new InvalidUserException("Invalid token");
        if (!principal.getRole_id().equals("2")) throw new InvalidUserException("Not an administrator");
        if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
    }

    public static void validateFinanceManagerLogin(String token, Principal principal) throws InvalidUserException{
        if (token == null || token.equals("")) throw new InvalidUserException("Not signed in");
        if (principal == null) throw new InvalidUserException("Invalid token");
        if (!principal.getRole_id().equals("2")) throw new InvalidUserException("Not an administrator");
        if(!principal.getIs_active()) throw new InvalidUserException("Check with administrator about account access.");
    }
}
