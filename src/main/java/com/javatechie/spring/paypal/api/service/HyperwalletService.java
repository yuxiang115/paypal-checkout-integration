package com.javatechie.spring.paypal.api.service;

import com.hyperwallet.clientsdk.Hyperwallet;
import com.hyperwallet.clientsdk.model.*;
import com.javatechie.spring.paypal.api.Util.paypal.hyperwallet.HyperwalletCredential;
import com.javatechie.spring.paypal.api.request.*;
import com.javatechie.spring.paypal.api.response.BankAccountResponse;
import com.javatechie.spring.paypal.api.response.HyperPaymentResponse;
import com.javatechie.spring.paypal.api.response.UserResponse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class HyperwalletService {
    @Autowired
    private HyperwalletCredential credential;

    @Autowired
    private Mapper mapper;

    public UserResponse createUser(UserRequest request) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
        HyperwalletUser user = mapper.map(request, HyperwalletUser.class);
        return mapper.map(credential.portalClient().createUser(user), UserResponse.class);
    }

    public UserResponse getUser(String userToken) {
        return mapper.map(credential.client().getUser(userToken), UserResponse.class);
    }

    public List<UserResponse> getListUsers() {
        Hyperwallet client = credential.client();
        List<UserResponse> res = new ArrayList<>();
        List<HyperwalletUser> list = client.listUsers().getData();
        if(list != null && !list.isEmpty()){
            list.forEach(e->{
                res.add(mapper.map(e, UserResponse.class));
            });
        }
        return res;
    }

    public UserResponse updateUser(UserRequest request) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
        HyperwalletUser user = mapper.map(request, HyperwalletUser.class);
        return mapper.map(credential.client().updateUser(user), UserResponse.class);
    }

    public List<BankAccountResponse> getAllBankAccountByUser(String userToken) {
        List<BankAccountResponse> res = new ArrayList<>();
        List<HyperwalletBankAccount> banks =  credential.client().listBankAccounts(userToken).getData();
        if(banks != null && !banks.isEmpty()){
            banks.stream().forEach(bank->{
                res.add(mapper.map(bank, BankAccountResponse.class));
            });
        }
        return res;
    }

    public BankAccountResponse createBankAccount(BankAccountRequest request) {
        HyperwalletBankAccount account = mapper.map(request, HyperwalletBankAccount.class);
        return mapper.map(credential.client().createBankAccount(account), BankAccountResponse.class);
    }

    public BankAccountResponse getBankAccountByUser(String userToken, String bankAccountToken) {
        return mapper.map(credential.client().getBankAccount(userToken, bankAccountToken), BankAccountResponse.class);
    }

    public BankAccountResponse updateBankAccount(BankAccountRequest request) {
        HyperwalletBankAccount account = mapper.map(request, HyperwalletBankAccount.class);
        return mapper.map(credential.client().updateBankAccount(account), BankAccountResponse.class);
    }

    public HyperwalletList<HyperwalletBankCard> getAllBankCardsByUser(String userToken) {
        return credential.client().listBankCards(userToken);
    }

    public HyperwalletBankCard createBankCard(BankCardRequest request) {
        HyperwalletBankCard card = mapper.map(request, HyperwalletBankCard.class);
        return credential.client().createBankCard(card);
    }

    public HyperwalletBankCard getBankCardByUser(String userToken, String cardToken) {
        return credential.client().getBankCard(userToken, cardToken);
    }

    public HyperwalletBankCard updateBankCard(BankCardRequest request){
        HyperwalletPayPalAccount account;
        HyperwalletBankCard card = mapper.map(request, HyperwalletBankCard.class);
        return credential.client().updateBankCard(card);
    }

    public HyperwalletList<HyperwalletPayPalAccount> getAllPaypalAccountByUser(String userToken){
        return credential.client().listPayPalAccounts(userToken);
    }

    public HyperwalletPayPalAccount createPaypalAccount(PaypalAccountRequest request){
        HyperwalletPayPalAccount account = mapper.map(request, HyperwalletPayPalAccount.class);
        return credential.client().createPayPalAccount(account);
    }

    public HyperwalletPayPalAccount getPayPalAccountByUser(String userToken, String payPalToken){
        return credential.client().getPayPalAccount(userToken, payPalToken);
    }

    public HyperwalletList<HyperwalletPrepaidCard> getAllPrepaidCardsByUser(String userToken){
        return credential.client().listPrepaidCards(userToken);
    }

    public HyperwalletPrepaidCard createPrepaidCard(PrepaidCardRequest request){
        HyperwalletPrepaidCard card = mapper.map(request, HyperwalletPrepaidCard.class);
        return credential.client().createPrepaidCard(card);
    }

    public HyperwalletPrepaidCard updatePrepaidCard(PrepaidCardRequest request){
        HyperwalletPrepaidCard card = mapper.map(request, HyperwalletPrepaidCard.class);
        return credential.client().updatePrepaidCard(card);
    }

    public HyperwalletPrepaidCard getPrepaidCardByUser(String userToken, String cardToken ){
        return credential.client().getPrepaidCard(userToken, cardToken);
    }

    public HyperwalletList<HyperwalletPaperCheck> getAllPaperCheckByUser(String userToken){
        return credential.client().listPaperChecks(userToken);
    }

    public HyperwalletPaperCheck createPaperCheck(HyperPaymentRequest request){
        HyperwalletPaperCheck check = mapper.map(request, HyperwalletPaperCheck.class);
        return credential.client().createPaperCheck(check);
    }

    public HyperwalletPaperCheck getPaperCheckByUser(String userToken, String paperCheckToken){
        return credential.client().getPaperCheck(userToken, paperCheckToken);
    }

    public HyperwalletPaperCheck updatePaperCheckByUser(HyperPaymentRequest request){
        HyperwalletPaperCheck check = mapper.map(request, HyperwalletPaperCheck.class);
        return credential.client().updatePaperCheck(check);
    }

    public HyperwalletList<HyperwalletTransferMethodConfiguration> getAllTransferMethodConfigurationsByUser(String userToken){
        return credential.client().listTransferMethodConfigurations(userToken);
    }

    public HyperwalletTransferMethodConfiguration getTransferMethodConfigurationByUser(String userToken, String county, String currency, String type, String profileType){
        return credential.client().getTransferMethodConfiguration(userToken, county, currency, HyperwalletTransferMethod.Type.valueOf(type), HyperwalletUser.ProfileType.valueOf(profileType));
    }

    public List<HyperPaymentResponse> getAllPayments(){
        List<HyperwalletPayment> list = credential.client().listPayments().getData();
        List<HyperPaymentResponse> res = new ArrayList<>();
        if(list != null && !list.isEmpty()){
            list.forEach(e->{
                res.add(mapper.map(e, HyperPaymentResponse.class));
            });
        }
        return res;
    }

    public HyperPaymentResponse createPayment(HyperPaymentRequest requet){
        HyperwalletPayment payment = mapper.map(requet, HyperwalletPayment.class);
        HyperwalletPayment paymentRes = credential.client().createPayment(payment);
        HyperPaymentResponse res = mapper.map(paymentRes, HyperPaymentResponse.class);
        return res;
    }

    public HyperPaymentResponse getPayment(String token){
        return mapper.map(credential.client().getPayment(token), HyperPaymentResponse.class);
    }



}
