package com.flipkart.logistics.services;

import com.flipkart.logistics.controllers.OnboardingMerchantController;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by vishal.bhandari on 17/08/15.
 */
public class MainService extends Service<ConfigurationService> {

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

    @Override
    public void run(ConfigurationService configuration, Environment environment) throws Exception {
        environment.addResource(new OnboardingMerchantController());
    }
}
