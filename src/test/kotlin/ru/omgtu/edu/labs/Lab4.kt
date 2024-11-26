package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Lab4 : AbstractTest() {


    @Test
    @DisplayName(
        value = """
        Написать 10 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Alerts, Frame & Windows'
        3. Выбрать пункт 'Alerts'
        4. Нажать 1 кнопку Click Me
        5. Проверить текст в модальном окне
        6. Нажать ОК
        7. Нажать 2 кнопку Click Me
        8. Проверить текст в модальном окне
        9. Нажать ОК
        10. Нажать 3 кнопку Click Me
        11. Проверить текст в модальном окне
        12. Нажать Отмена
        13. Нажать 4 кнопку Click Me
        14. Ввести текст в модальном окне
        15. Нажать ОК
    """
    )
    fun testClickMe() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        driver.manage().window().maximize()

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Alerts']"))).click()

        wait.until(ExpectedConditions.elementToBeClickable(By.id("alertButton"))).click()
        var alert = wait.until(ExpectedConditions.alertIsPresent())
        assertEquals("You clicked a button", alert.text)
        alert.accept()

        wait.until(ExpectedConditions.elementToBeClickable(By.id("timerAlertButton"))).click()
        alert = wait.until(ExpectedConditions.alertIsPresent())
        assertEquals("This alert appeared after 5 seconds", alert.text)
        alert.accept()

        wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmButton"))).click()
        alert = wait.until(ExpectedConditions.alertIsPresent())
        assertEquals("Do you confirm action?", alert.text)
        alert.dismiss()

        wait.until(ExpectedConditions.elementToBeClickable(By.id("promtButton"))).click()
        alert = wait.until(ExpectedConditions.alertIsPresent())
        alert.sendKeys("Test message")
        alert.accept()
    }

    @Test
    @DisplayName(
        value = """
        Написать 11 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Alerts, Frame & Windows'
        3. Выбрать пункт 'Frames'
        4. Проверить наличие текста 'This is a sample page' в 1 frame
        5. Проверить наличие текста 'This is a sample page' в 2 frame
    """
    )
    fun testFrames() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Frames']"))).click()

        driver.switchTo().frame("frame1")
        val frame1Text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading"))).text
        assertEquals("This is a sample page", frame1Text)

        driver.switchTo().defaultContent()

        driver.switchTo().frame("frame2")
        val frame2Text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading"))).text
        assertEquals("This is a sample page", frame2Text)

        driver.switchTo().defaultContent()
    }

    @Test
    @DisplayName(
        value = """
        Написать 12 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Alerts, Frame & Windows'
        3. Выбрать пункт 'Nested Frames'
        4. Проверить наличие текста 'Child Iframe' в 1 frame
        5. Проверить наличие текста 'Parent frame' в 2 frame"""
    )
    fun testNestedFrames() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Nested Frames']"))).click()

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("frame1")))
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")))
        val childFrameText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p"))).text
        assertTrue(childFrameText == "Child Iframe")

        driver.switchTo().parentFrame()

        val parentFrameText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).text
        assertContains("Parent frame", parentFrameText, "Error")

        driver.switchTo().defaultContent()
    }


}