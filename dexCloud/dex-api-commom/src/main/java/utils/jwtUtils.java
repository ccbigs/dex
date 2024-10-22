package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class jwtUtils {

    /*token过期时间*/
    private static long expire =  604800;
    /*加盐*/
    private static String slat = "aaaa";


    //生成jwt
    public static String generateToken(String username){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+ 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate) //7天过期
                .signWith(SignatureAlgorithm.HS256,slat)
                .compact();

    }

    //解析jwt
    public static Claims getClaimByToken(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(slat)
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (Exception e){
            return null;
        }

    }


    //jwt是否过期
    public static boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
