package com.flipkart.logistics.services;

import com.flipkart.logistics.controllers.OnboardingMerchantController;
import com.flipkart.logistics.controllers.RequestController;
import com.flipkart.logistics.controllers.RequestProcessController;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


/**
 * Created by vishal.bhandari on 17/08/15.
 */
public class MainService extends Application<ConfigurationService> {

    public static void main(String[] args) {
        try {
            new MainService().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(Bootstrap bootstrap) {

    }


   /* @Override
    public void initialize(ConfigurationService service, Environment env) {
    }*/



    @Override
    public void run(ConfigurationService configurationService, Environment environment) throws Exception {

        environment.jersey().register(new OnboardingMerchantController());
        environment.jersey().register(new RequestController());
        environment.jersey().register(new RequestProcessController());
    }
}
