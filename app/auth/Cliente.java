package auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import java.text.ParseException;

public class Cliente extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {

        // Para validar, nos aseguramos que el token recibido exista y este disponible.
        String status = null;
        String authHeader = ctx.request().getHeader((AuthService.AUTH_HEADER_KEY));

        if (StringUtils.isBlank(authHeader) || authHeader.split(" ").length != 2) {
            Logger.debug("Auth - Error: No se recibio token");
        } else {
            status = authHeader;
            JWTClaimsSet claimSet = null;
            try {

                claimSet = (JWTClaimsSet) AuthService.decodeToken(authHeader);
                Logger.info(claimSet.toString());

                String tipoUsuario = claimSet.getStringClaim("tipo_usuario");
                Logger.info(tipoUsuario);

                String path = ctx.request().path();
                Logger.info(path);


            } catch (ParseException e) {
                Logger.error("Auth - Error : No se ha podido parsear JWT");
                status = null;
            } catch (JOSEException e) {
                Logger.error("Auth - Error : Invalido JWT token");
                status = null;
            }

            // ensure that the token is not expired
            if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
                Logger.debug("Auth - Expired : Token ha expirado");
            } else {
                //Todo bien
                //Logger.debug("Auth - Expired Time : "+claimSet.getExpirationTime());
            }
        }

        return status;
    }

    // Este metodo solo es invocado CUANDO NO SE RECIBIO TOKEN desde el request
    @Override
    public Result onUnauthorized(Context ctx) {
        return notFound();
    }



}