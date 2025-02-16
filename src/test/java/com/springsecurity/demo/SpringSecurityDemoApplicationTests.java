package com.springsecurity.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@SpringBootTest
class SpringSecurityDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
////		byte[] key= Decoders.BASE64.decode("eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ");
////		System.out.println(new String(key));
//		Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//
//		System.out.println(key);

		MacAlgorithm SECRET_KEY = Jwts.SIG.HS256;
		byte[] bytes = Base64.getDecoder()
				.decode("kjbkjasfkbjb");
		System.out.println(new SecretKeySpec(bytes, "HmacSHA256").getEncoded());
	}

}
