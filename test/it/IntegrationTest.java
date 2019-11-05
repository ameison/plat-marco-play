package it;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;



public class IntegrationTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }




}
