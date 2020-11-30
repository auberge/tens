import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiI0NTYiLCJpYXQiOjE1ODkzODY3OTcsImV4cCI6MTU4OTM4Njg1Nn0.2_hudeN3fAGqlChOKIq_6hE0cjszHqD7ozFu9uLmIZY";
        parser(token);
        create();
    }

    private static void parser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("用户id"+claims.getId());
        System.out.println("用户名"+claims.getSubject());
        System.out.println("登录时间"+claims.getIssuedAt());
        System.out.println("过期时间"+claims.getExpiration());
    }

    private static void create() {
        long time = Calendar.getInstance().getTimeInMillis();;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("123")
                .setSubject("456")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"itcast")
                .setExpiration(new Date(time+60000));
        System.out.println(new Date(time+60000));
        System.out.println(jwtBuilder.compact());
    }
}
