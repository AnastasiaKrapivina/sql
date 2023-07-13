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
    void shouldValidLogin(){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLoginPassword(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify((verificationCode.getCode()));
    }

    @Test
    @DisplayName("If the password is entered incorrectly three times, the system will be blocked")
    void shouldInvalidPassword(){
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLoginInvalidPassword();
        loginPage.verifyErrorNotification();
        loginPage.clear();
        loginPage.validLoginInvalidPassword();
        loginPage.verifyErrorNotification();
        loginPage.clear();
        loginPage.validLoginInvalidPassword();
        loginPage.verifyErrorNotification();
        loginPage.getBlockInfo().shouldBe(visible);

    }

}
