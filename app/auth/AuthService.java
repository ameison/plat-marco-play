package auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import models.Usuario;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.mvc.Http;

import java.text.ParseException;

public class AuthService {


    private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
    private static final String TOKEN_SECRET = "*7#ew867wer657e68r7e7r8e7r8e7r87.?";
    static final String AUTH_HEADER_KEY = "Authorization";

    public static JWTClaimsSet getPayload (Http.RequestHeader r) {
        JWTClaimsSet jwt = null;
        try{
            String authHeader = r.getHeader((AUTH_HEADER_KEY));
            jwt = (JWTClaimsSet)decodeToken(authHeader);
        }catch (ParseException e){
            Logger.error("ParseException  - "+e);
        }catch (JOSEException j){
            Logger.error("JOSEException  - "+j);
        }
        return jwt;

    }

    static AuthToken createToken(String host, Usuario uLogin) throws JOSEException {
        JWTClaimsSet claim = new JWTClaimsSet();
        claim.setSubject(Long.toString(uLogin.id));
        claim.setIssuer(host);
        claim.setIssueTime(DateTime.now().toDate());
        claim.setExpirationTime(DateTime.now().plusDays(14).toDate());
        // ---- Set de atributos -------
        claim.setClaim("id", uLogin.getId());
        claim.setClaim("email", uLogin.getCorreo());
        claim.setClaim("telefono", uLogin.getTelefono());
        claim.setClaim("tipo_usuario", uLogin.getTipoUsuario());
        claim.setClaim("mina_id", uLogin.getMina().getId());
        // ---- Set de atributos -------
        JWSSigner signer = new MACSigner(TOKEN_SECRET);
        SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
        jwt.sign(signer);

        return new AuthToken(jwt.serialize());
    }

    static ReadOnlyJWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
        if (signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
            return signedJWT.getJWTClaimsSet();
        } else {
            throw new JOSEException("Signature verification failed");
        }
    }

    private static String getSerializedToken(String authHeader) {
        return authHeader.split(" ")[1];
    }

    static boolean checkPassword(String plaintext , String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }


}
