package ru.netology.sql.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.data.SQLHelper;
import ru.netology.sql.page.LoginPage;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;


public class TestForm {


    @Test
    @DisplayName("Should receive a username and password to enter your personal account")
    void shouldValidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var login = DataHelper.getAuthInfoWithTestData().getLogin();
        var password = DataHelper.getAuthInfoWithTestData().getPassword();
        var verificationPage = loginPage.validLoginPassword(login, password);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify((verificationCode.getCode()));
    }

    @Test
    @DisplayName("Login attempt by an unregistered user")
    void shouldUnregisteredUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var login = DataHelper.generateRandomLogin();
        var password = DataHelper.generateRandomPassword();
        loginPage.loginPassword(login, password);
        loginPage.verifyErrorNotification();
    }

    @Test
    @DisplayName("If the password is entered incorrectly three times, the system will be blocked")
    void shouldInvalidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var login = DataHelper.getAuthInfoWithTestData().getLogin();
        var password = DataHelper.generateRandomPassword();
        loginPage.loginPassword(login, password);
        loginPage.verifyErrorNotification();
        loginPage.clear();
        loginPage.loginPassword(login, password);
        loginPage.verifyErrorNotification();
        loginPage.clear();
        loginPage.loginPassword(login, password);
        loginPage.verifyErrorNotification();
        loginPage.getBlockInfo();

    }

}
