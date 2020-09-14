package kr.bistroad.authservice.token.infrastructure

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*


@Component
class JwtSigner(
    @Value("\${security.jwt.token.private-key}")
    privateKeyStr: String,

    @Value("\${security.jwt.token.expire-length}")
    val validTime: Long
) {
    private val privateKey: PrivateKey

    init {
        val bytes = Base64.getDecoder().decode(
            privateKeyStr
                .replace("\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .toByteArray()
        )
        val keySpec = PKCS8EncodedKeySpec(bytes)
        val keyFactory = KeyFactory.getInstance("RSA")

        privateKey = keyFactory.generatePrivate(keySpec)
    }

    fun createToken(subject: String): String {
        val claims: Claims = Jwts.claims().setSubject(subject)
        val now = Date()
        val validity = Date(now.time + validTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()
    }
}