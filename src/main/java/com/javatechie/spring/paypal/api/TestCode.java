package com.javatechie.spring.paypal.api;

import com.hyperwallet.clientsdk.model.HyperwalletBankAccount;
import com.javatechie.spring.paypal.api.request.BankAccountRequest;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.http.serializer.Json;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class TestCode {
    private static Mapper mapper = new DozerBeanMapper();
    public static void main(String[] args) throws SerializeException, JSONException {
        BankAccountRequest bankAccountRequest =
                new BankAccountRequest(
                        "trm-56b976c5-26b2-42fa-87cf-14b3366673c6",
                        "usr-c4292f1a-866f-4310-a289-b916853939de",
                        "INDIVIDUAL",
                        "US",
                        "USD",
                        "BANK_ACCOUNT",
                        "101089292",
                        "7861012345",
                        "SAVINGS",
                        "John",
                        "Smith",
                        "US",
                        "WA",
                        "123 Main Street",
                        "Hometown",
                        "12345",
                        "SELF");



        HyperwalletBankAccount account = mapper.map(bankAccountRequest, HyperwalletBankAccount.class);

        System.out.println(new JSONObject(new Json().serialize(account)).toString());

    }
}
