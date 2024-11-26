package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Lab5 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Написать 13 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Alerts, Frame & Windows'
        3. Выбрать пункт 'Modal Dialogs'
        4. Нажать Small Modal
        5. В открывшемся окне сверить заголовок
        6. В открывшемся окне сверить основной текст
        7. Нажать Close
    """
    )
    fun testSmallModal() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Modal Dialogs']"))).click()

        wait.until(ExpectedConditions.elementToBeClickable(By.id("showSmallModal"))).click()

        val modalTitle =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-sm"))).text
        assertEquals("Small Modal", modalTitle)

        val modalBodyText = driver.findElement(By.cssSelector(".modal-body")).text
        val expectedBodyText = "This is a small modal. It has very less content"
        assertEquals(expectedBodyText, modalBodyText)

        driver.findElement(By.id("closeSmallModal")).click()
    }

    @Test
    @DisplayName(
        value = """
        Написать 14 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Accordian'
        4. Раскрыть аккордион 'What is Lorem Ipsum?'
        5. Проверить наличие текста
        6. Раскрыть аккордион 'Where does it come from?'
        7. Проверить наличие текста
        8. Раскрыть аккордион 'Why do we use it?'
        9. Проверить наличие текста
    """
    )
    fun testAccordian() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Accordian']"))).click()

        val section1Header = driver.findElement(By.cssSelector("#section1Heading"))
        section1Header.click()
        val section1Text =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#section1Content>p"))).text
        assertTrue(section1Text.isNotEmpty(), "Текст пустой")

        val section2Header = driver.findElement(By.cssSelector("#section2Heading"))
        section2Header.click()
        val section2Text =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#section2Content>p"))).text
        assertTrue(section2Text.isNotEmpty(), "Текст пустой")

        val section3Header = driver.findElement(By.cssSelector("#section3Heading"))
        section3Header.click()
        val section3Text =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#section3Content>p"))).text
        assertTrue(section3Text.isNotEmpty(), "Текст пустой")
    }

    @Test
    @DisplayName(
        value = """
        Написать 15 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Auto Complete'
        4. В поле 'Type multiple color names' выбрать значения: Black, Red, Magenta
        5. В поле 'Type single color name' выбрать значения: Black
        6. В поле 'Type single color name' заменить значения на Red
    """
    )
    fun testAutoComplete() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']"))).click()

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Auto Complete']"))).click()

        val multiColorInput =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#autoCompleteMultipleInput")))
        multiColorInput.sendKeys("Black")
        multiColorInput.sendKeys(Keys.ENTER)
        multiColorInput.sendKeys("Red")
        multiColorInput.sendKeys(Keys.ENTER)
        multiColorInput.sendKeys("Magenta")
        multiColorInput.sendKeys(Keys.ENTER)

        Thread.sleep(1000)

        val singleColorInput =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#autoCompleteSingleInput")))
        singleColorInput.sendKeys("Black")
        singleColorInput.sendKeys(Keys.ENTER)

        Thread.sleep(1000)

        singleColorInput.clear()
        singleColorInput.sendKeys("Red")
        singleColorInput.sendKeys(Keys.ENTER)

        Thread.sleep(1000)
    }
}