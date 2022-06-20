/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ito.dsos.compras.authentication;

import ito.dsos.compras.constans.AuthenticationConstans;
import ito.dsos.compras.enums.TipoRespuestaParseEnum;
import ito.dsos.compras.exceptions.ExternalMicroserviceException;
import ito.dsos.compras.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *
 * @author Oscar
 */
@Component
public class Authentication {
     @Autowired
    private HttpRequest http;

    private final Integer HTTP_OK = 200, HTTP_UNAUTHORIZED = 401;

    public void auth(HttpServletRequest request) throws UnauthorizedException, ExternalMicroserviceException, IOException {
        Optional<String> tokenOptional = Optional.ofNullable(request.getHeader("Authorization"));
   
            if (!tokenOptional.isPresent()) {
                throw new UnauthorizedException();
            }
            
            Map<String, Object> result = validateRequestTokenVerficacion(tokenOptional.get());
            int httpCode = Integer.parseInt(String.valueOf(result.get("status")));
            
            if (httpCode == HTTP_UNAUTHORIZED) {
                throw new UnauthorizedException();
            }

            if (httpCode != HTTP_OK) {
                throw new ExternalMicroserviceException(AuthenticationConstans.ERROR_EXTERNAL_MENSAJE_EXCEPTION);
            }
       
    }

    private Map<String, Object> validateRequestTokenVerficacion(String token) throws IOException {
        Map<String, Object> basicRequest = http.createBasicDataRequest(TipoRespuestaParseEnum.MAP);
        basicRequest.put("url", AuthenticationConstans.URL_AUTH + token);

        Map<String, String> body = new HashMap();
        basicRequest.put("body", body);

        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        basicRequest.put("headers", headers);

        Map<String, Object> result = http.post(basicRequest);
        return result;
    }
}
