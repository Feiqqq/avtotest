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

class Lab3 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
         Тест 7 
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Dynamic Properties'
        4. Подождать пока текст кнопки 'Color Change' не сменит цвет
        5. Обновить страницу // Зачем обновлять?
        6. Подождать пока кнопка 'Visible After 5 Seconds' не появится
        """
    )
    fun testDynamicProperties() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val dynamicPropertiesMenu = driver.findElement(By.xpath("//span[text()='Dynamic Properties']"))
        dynamicPropertiesMenu.click()

        val wait = WebDriverWait(driver, Duration.ofSeconds(8))
        val enableAfter = driver.findElement(By.id("enableAfter"))
        wait.until(ExpectedConditions.elementToBeClickable(enableAfter))

        // Не обновляем страницу

        val visibleAfter5SecondsButton = driver.findElement(By.id("visibleAfter"))
        wait.until(ExpectedConditions.visibilityOf(visibleAfter5SecondsButton))

        assertTrue(visibleAfter5SecondsButton.isDisplayed, "Кнопка не появилась")
    }

    @Test
    @DisplayName(
        value = """
            Написать 8 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Forms'
        3. Выбрать пункт 'Practice Form'
        4. Заполнить поля Name
        5. Заполнить поле Email валидным значение
        6. Выбрать Gender
        7. Заполнить поле Mobile валидным значением
        8. Заполнить поле Date of Birth
        9. Выбрать в поле Subject 3 любых значения
        10. Выбрать Hobbies
        11. Заполнить Current Addres
        12. Выбрать State and City
        13. Нажать Submit
        14. Проверить что в открывшимся окне есть введенные данные.
        15. Нажать Close
        """
    )
    fun testPracticeForm() {
        driver.get("https://demoqa.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(5))

        val formsSection = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Forms']"))
        )
        formsSection.click()

        val practiceFormOption = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Practice Form']"))
        )
        practiceFormOption.click()

        val firstNameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("firstName"))
        )
        val lastNameInput = driver.findElement(By.id("lastName"))
        firstNameInput.sendKeys(FIRST_NAME)
        lastNameInput.sendKeys(LAST_NAME)

        val emailInput = driver.findElement(By.id("userEmail"))
        emailInput.sendKeys(EMAIL)

        val genderRadio = driver.findElement(By.xpath("//label[text()='Male']"))
        genderRadio.click()

        val mobileInput = driver.findElement(By.id("userNumber"))
        mobileInput.sendKeys("1234567890")

        val dateOfBirthInput = driver.findElement(By.id("dateOfBirthInput"))
        dateOfBirthInput.click()
        dateOfBirthInput.sendKeys(Keys.CONTROL, "a")
        dateOfBirthInput.sendKeys("10 Jan 1990")
        dateOfBirthInput.sendKeys(Keys.ENTER)

        val subjectsInput = driver.findElement(By.id("subjectsInput"))
        listOf("Math", "Physics", "Computer Science").forEach { subject ->
            subjectsInput.sendKeys(subject)
            subjectsInput.sendKeys(Keys.TAB)
        }

        val hobbiesCheckbox = driver.findElement(By.xpath("//label[text()='Sports']"))
        hobbiesCheckbox.click()

        val currentAddressInput = driver.findElement(By.id("currentAddress"))
        currentAddressInput.sendKeys("123 Main Street, City, Country")

        val stateDropdown = driver.findElement(By.id("react-select-3-input"))
        stateDropdown.sendKeys("NCR")
        stateDropdown.sendKeys(Keys.ENTER)

        val cityDropdown = driver.findElement(By.id("react-select-4-input"))
        cityDropdown.sendKeys("Delhi")
        cityDropdown.sendKeys(Keys.ENTER)

        val submitButton = driver.findElement(By.id("submit"))
        submitButton.click()

        val modalContent = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.className("modal-content"))
        )
        val modalText = modalContent.text
        println(modalText)
        assertContains(FIRST_NAME, modalText, "Имя Error")
        assertContains(LAST_NAME, modalText, "Фамилия Error")
        assertContains(EMAIL, modalText, "Почта Error")
        assertTrue(modalText.contains("1234567890"))
        assertTrue(modalText.contains("January"))
        assertTrue(modalText.contains("Math"))
        assertTrue(modalText.contains("Physics"))
        assertTrue(modalText.contains("Computer Science"))
        assertTrue(modalText.contains("Sports"))
        assertTrue(modalText.contains("123 Main Street"))
        assertTrue(modalText.contains("NCR Delhi"))

        val closeButton = driver.findElement(By.id("closeLargeModal"))
        closeButton.click()
    }

    @Test
    @DisplayName(value = """
        Написать 9 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Alerts, Frame & Windows'
        3. Выбрать пункт 'Browser Windows'
        4. Нажать New Tab
        5. Переключиться в открывшуюся вкладку
        6. Проверить адрес вкладки
        7. Вернуться на первоначальную вкладку
        8. Нажать New Window
        9. Переключиться в открывшуюся вкладку
        10. Проверить адрес вкладки
        11. Вернуться на первоначальную вкладку
    """)
    fun testBrowserWindows() {
        driver.get("https://demoqa.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(5))

        val alertsFrameWindowsSection = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Alerts, Frame & Windows']"))
        )
        alertsFrameWindowsSection.click()

        val browserWindowsOption = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Browser Windows']"))
        )
        browserWindowsOption.click()

        val initialWindow = driver.windowHandle

        val newTabButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("tabButton"))
        )
        newTabButton.click()

        wait.until(ExpectedConditions.numberOfWindowsToBe(2))
        val newTab = driver.windowHandles.first { it != initialWindow }
        driver.switchTo().window(newTab)

        assertEquals("https://demoqa.com/sample", driver.currentUrl)

        driver.close()
        driver.switchTo().window(initialWindow)

        val newWindowButton = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("windowButton"))
        )
        newWindowButton.click()

        wait.until(ExpectedConditions.numberOfWindowsToBe(2))
        val newWindow = driver.windowHandles.first { it != initialWindow }
        driver.switchTo().window(newWindow)

        assertEquals("https://demoqa.com/sample", driver.currentUrl)

        driver.close()
        driver.switchTo().window(initialWindow)
    }

    private companion object {
        const val FIRST_NAME = "Пётр"
        const val LAST_NAME = "Петрова"
        const val EMAIL = "petrov@email.ru"
    }
}