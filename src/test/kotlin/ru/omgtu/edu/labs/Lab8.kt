package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Lab8 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Написать 22 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Book Store Application'
        3. Выбрать пункт 'Login'
        4. Ввести UserName
        5. Ввести Password
        6. Нажать Login
        7. Проверить что появилась надпись Invalid username or password!
        8. Нажать NewUser
        9. Заполнить First Name
        10. Заполнить Last Name
        11. Заполнить UserName
        12. Заполнить Password 1 символом
        13. Нажать Register
        14. Проверить что появилась надпись Please verify reCaptcha to register!
        15. Поставить чек бокс I`m not a robot
        16. Нажать Register
        17. Проверить что появилась надпись Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.
        18. Заполнить Password валидным значением
        19. Поставить чек бокс I`m not a robot
        20. Нажать Register
        21. В модальном окне нажать ОК
    """
    )
    // TODO: работает через раз из-за капчи
    fun testBookStoreApplication() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        val bookStoreAppElement =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Book Store Application']")))
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView();", bookStoreAppElement)
        bookStoreAppElement.click()

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Login']"))).click()

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='userName']")))
            .sendKeys("invalidUser")

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']")))
            .sendKeys("invalidPassword")

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='login']"))).click()

        val errorMessage =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#output #name"))).text
        assertEquals("Invalid username or password!", errorMessage)
        val newUser = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#newUser")))
        ((driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", newUser))
        wait.until(ExpectedConditions.elementToBeClickable(newUser)).click()

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstname']")))
            .sendKeys("FirstName")

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='lastname']")))
            .sendKeys("LastName")

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='userName']")))
            .sendKeys("NewUserName")

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']"))).sendKeys("a")

        val register1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#register")))
        ((driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", register1))
        wait.until(ExpectedConditions.elementToBeClickable(register1)).click()

        val errorMessage2 =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#output #name"))).text
        assertEquals("Please verify reCaptcha to register!", errorMessage2)

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='reCAPTCHA']")))
        Thread.sleep(5000)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".recaptcha-checkbox-border"))).click()
        Thread.sleep(5000)

        driver.switchTo().defaultContent()

        val registerButton = driver.findElement(By.id("register"))
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", registerButton)
        Thread.sleep(500)
        registerButton.click()

        val errorMessage3 =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#output #name"))).text
        assertTrue(errorMessage3.contains("Passwords must have at least one non alphanumeric character"))
        assertTrue(errorMessage3.contains("one digit ('0'-'9')"))
        assertTrue(errorMessage3.contains("one uppercase ('A'-'Z')"))
        assertTrue(errorMessage3.contains("one lowercase ('a'-'z')"))
        assertTrue(errorMessage3.contains("one special character"))
        assertTrue(errorMessage3.contains("Password must be eight characters or longer"))

        val validPassword = "Password1!@343"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']"))).clear()
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']")))
            .sendKeys(validPassword)

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='reCAPTCHA']")))
        Thread.sleep(5000)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".recaptcha-checkbox-border"))).click()
        Thread.sleep(5000)

        driver.switchTo().defaultContent()

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#register"))).click()

        val alert = wait.until(ExpectedConditions.alertIsPresent())
        Thread.sleep(1000)
        alert.accept()
    }
}