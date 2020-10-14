package com.javatechie.spring.paypal.api.Util.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;

public class Credentials {
    static String CLIENT_ID = "AddKpwEoz5WU_1dfW3RdIKj3wg7ObnLinO5Reec2cf_GPu_c8D4TO1WgKJ6IhxBSZmhZ6fIhvPzIpAe8";
    static String SECRET = "EGvPgOKFdAmJ7O-dIi1XW8IPxawhEG0JzlNYseixCbP5AoIIUD9K6qBB7PIlsI8JnLrPkKgRK3BmhNc2";

    static String MODE = "live";

/*
    static String CLIENT_ID = "AQ8MMXPaMD1TysomxOzH0yIO08O8DGCzssN3_O84Moh4zeLIR4a84AKPPmBBTDrfS0dFenezfrsv0TV-";
    static String SECRET = "EPhiih_R8L9JJ8FQAOS2l66XOeYGuSldiHLB4NTwcDnuRw9YnmMx-kTqR-_oMnfUSqogM315B6sfBKaj";

    static String MODE = "sanbox";
*/

    private static PayPalEnvironment environment;
    static {
        if (MODE.equals("sanbox"))
            environment = new PayPalEnvironment.Sandbox(CLIENT_ID, SECRET);
        else
            environment = new PayPalEnvironment.Live(CLIENT_ID, SECRET);
    }

    public static PayPalHttpClient CLIENT = new PayPalHttpClient(environment);


}
