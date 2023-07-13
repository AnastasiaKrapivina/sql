package ru.netology.sql.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.page.VerificationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private SelenideElement blockInfo = $ (Selectors.withText("Доступ заблокирован"));

    public void verifyErrorNotification() {
        errorNotification.shouldBe(visible);
    }
    public VerificationPage validLoginPassword(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
    public void validLoginInvalidPassword() {
        loginField.setValue(DataHelper.getAuthInfoWithTestData().getLogin());
        passwordField.setValue(DataHelper.generateRandomPassword());
        loginButton.click();
      }
    public void clear() {
        loginField.sendKeys(Keys.CONTROL + "A");
        loginField.sendKeys(Keys.BACK_SPACE);
        passwordField.sendKeys(Keys.CONTROL + "A");;
        passwordField.sendKeys(Keys.BACK_SPACE);
    }

    public SelenideElement getBlockInfo() {
        return blockInfo;
    }
}
