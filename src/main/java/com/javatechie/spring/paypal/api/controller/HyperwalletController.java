package com.javatechie.spring.paypal.api.controller;

import com.javatechie.spring.paypal.api.request.BankAccountRequest;
import com.javatechie.spring.paypal.api.request.HyperPaymentRequest;
import com.javatechie.spring.paypal.api.request.UserRequest;
import com.javatechie.spring.paypal.api.response.BankAccountResponse;
import com.javatechie.spring.paypal.api.response.HyperPaymentResponse;
import com.javatechie.spring.paypal.api.response.UserResponse;
import com.javatechie.spring.paypal.api.service.HyperwalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value="/hyperwallet")
public class HyperwalletController {
    @Autowired
    private HyperwalletService hyperwalletService;

    @PostMapping(value = "/user/create")
    public UserResponse createUser(@RequestBody UserRequest request){
        try {
            return hyperwalletService.createUser(request);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping(value = "/user/all")
    public List<UserResponse> getUsers(){

        return hyperwalletService.getListUsers();
    }

    @GetMapping(value = "/user/{userToken}")
    public UserResponse getUser(@PathVariable String userToken){
       return hyperwalletService.getUser(userToken);
    }

    @PostMapping(value = "/user/update")
    public UserResponse updateUser(@RequestBody UserRequest user) {
        UserResponse userResponse = null;
        try {
            userResponse = hyperwalletService.updateUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userResponse;
    }


    @PostMapping(value = "/bank-account/register")
    public BankAccountResponse registerBankAccount(@RequestBody BankAccountRequest request){
        BankAccountResponse response = null;
        response = hyperwalletService.createBankAccount(request);
        return response;
    }

    @GetMapping(value = "{userToken}/bank-account/all")
    public List<BankAccountResponse> getAllBankAccountByUser(@PathVariable String userToken){
        List<BankAccountResponse> res = null;
        res = hyperwalletService.getAllBankAccountByUser(userToken);
        return res;
    }

    @GetMapping(value = "{userToken}/bank-account/{bankAccountToken}")
    public BankAccountResponse getAllBankAccountByUser(@PathVariable String userToken, @PathVariable String bankAccountToken) {
        BankAccountResponse response = hyperwalletService.getBankAccountByUser(userToken, bankAccountToken);
        return response;
    }

    @PostMapping(value = "/bank-account/update")
    public BankAccountResponse updateBankAccount(@RequestBody BankAccountRequest request) {
        BankAccountResponse response = hyperwalletService.updateBankAccount(request);
        return response;
    }

    @GetMapping(value = "payment/all")
    public List<HyperPaymentResponse> allPyaments(){
        return hyperwalletService.getAllPayments();
    }

    @GetMapping(value = "payment/{paymentToken}")
    public HyperPaymentResponse getPayment(@PathVariable String paymentToken){
        return hyperwalletService.getPayment(paymentToken);
    }

    @PostMapping(value = "payment/create")
    public HyperPaymentResponse createPayment(@RequestBody HyperPaymentRequest request){
        return hyperwalletService.createPayment(request);
    }





}
