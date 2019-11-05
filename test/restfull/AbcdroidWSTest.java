package restfull;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import java.io.File;
import static play.test.Helpers.*;

public class AbcdroidWSTest extends WithApplication {

    private ObjectMapper mapper = new ObjectMapper();
    private String token;

    @Before
    public void loginJwt() throws Exception {
        File file = new File(ClassLoader.getSystemResource("data/usuario.json").toURI());
        JsonNode jsonNode = mapper.readTree(file);

        Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(jsonNode).uri(auth.routes.AuthWS.login().url());
        Result result = route(request);
        JsonNode node = mapper.readValue(contentAsString(result), JsonNode.class);
        token = node.get("token").asText();
        Logger.debug("token > "+node.get("token"));
    }

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().configure("play.http.router", "my.test.Routes").build();
    }

}