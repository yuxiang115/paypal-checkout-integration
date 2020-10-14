package com.javatechie.spring.paypal.api.Util.paypal.hyperwallet;

import com.hyperwallet.clientsdk.Hyperwallet;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class HyperwalletCredential {

    @Value("${hyperwallet.client.id}")
    private String CLIENT_ID;
    @Value("${hyperwallet.client.secret}")
    private String SECRET;
    @Value("${hyperwallet.program.token}")
    private String PROGRAM_PARENT_TOKEN;
    @Value("${hyperwallet.program.portal.token}")
    private String PROGRAM_PORTAL_TOKEN;
    @Value("${hyperwallet.client.api}")
    private String API;

    private static Hyperwallet client;
    private static Hyperwallet portalClient;

    public Hyperwallet client(){
        if(client == null) {
            return new Hyperwallet(CLIENT_ID, SECRET, PROGRAM_PARENT_TOKEN, API);
        }
        else
            return client;
    }

    public Hyperwallet portalClient(){
        if(portalClient == null) {
            return new Hyperwallet(CLIENT_ID, SECRET, PROGRAM_PORTAL_TOKEN, API);
        }
        else
            return portalClient;
    }



}
