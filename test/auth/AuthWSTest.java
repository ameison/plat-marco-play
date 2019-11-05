package auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.File;

import static org.junit.Assert.*;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

public class AuthWSTest extends WithApplication {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().configure("play.http.router", "my.test.Routes").build();
    }

    @Test
    public void login() throws Exception {
        File file = new File(ClassLoader.getSystemResource("data/usuario.json").toURI());
        JsonNode jsonNode = mapper.readTree(file);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(jsonNode)
                .uri(routes.AuthWS.login().url());


        Result result = route(request);
        JsonNode node = mapper.readValue(contentAsString(result), JsonNode.class);
        Logger.debug("r>token : "+node.get("token"));
        Logger.debug("r>token : "+node.get("loginMessage"));

        assertEquals(node.get("token").asText().isEmpty(), false);
    }
}