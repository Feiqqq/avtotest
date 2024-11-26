package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import ru.omgtu.edu.AbstractTest

class Lab1 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Тест 1
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Text Box'
        4. Заполнить все поля форме валидными значениями
        5. Нажать кнопку Submit
        6. Проверить что ниже появились введенные данные
    """
    )
    fun testTextBox() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val textBoxMenu = driver.findElement(By.xpath("//span[text()='Text Box']"))
        textBoxMenu.click()

        val fullNameInput = driver.findElement(By.id("userName"))
        val emailInput = driver.findElement(By.id("userEmail"))
        val currentAddressInput = driver.findElement(By.id("currentAddress"))
        val permanentAddressInput = driver.findElement(By.id("permanentAddress"))

        val fullName = "Пётр Петров"
        val email = "petrov.p@inbox.ru"
        val currentAddress = "Красный путь 28"
        val permanentAddress = "Красный путь 28"

        fullNameInput.sendKeys(fullName)
        emailInput.sendKeys(email)
        currentAddressInput.sendKeys(currentAddress)
        permanentAddressInput.sendKeys(permanentAddress)

        val submitButton = driver.findElement(By.xpath("//*[@id='submit']"))
        submitButton.click()

        val outputName = driver.findElement(By.id("name"))
        val outputEmail = driver.findElement(By.id("email"))
        val outputCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']"))
        val outputPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']"))

        assertContains(fullName, outputName.text, "Имя не совпадает")
        assertContains(email, outputEmail.text, "Email не совпадает")
        assertContains(currentAddress, outputCurrentAddress.text, "Current Address не совпадает")
        assertContains(permanentAddress, outputPermanentAddress.text, "Permanent Address не совпадает")
    }

    @Test
    @DisplayName(
        value = """
        Тест 2
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Check Box'
        4. Нажать на +
        5. Поставить чек боксы на пункты: Notes, Veu, Private
        6. Нажать -
    """
    )
    fun testCheckBox() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val checkBoxMenu = driver.findElement(By.xpath("//span[text()='Check Box']"))
        checkBoxMenu.click()

        val expandButton = driver.findElement(By.cssSelector(".rct-option-expand-all"))
        expandButton.click()

        val notesCheckBox = driver.findElement(By.xpath("//span[text()='Notes']/preceding-sibling::span"))
        val veuCheckBox = driver.findElement(By.xpath("//span[text()='Veu']/preceding-sibling::span"))
        val privateCheckBox =
            driver.findElement(By.xpath("//span[text()='Private']/preceding-sibling::span"))

        notesCheckBox.click()
        veuCheckBox.click()
        privateCheckBox.click()

        val collapseButton = driver.findElement(By.cssSelector(".rct-option-collapse-all"))
        collapseButton.click()
        val notes = driver.findElement(By.xpath("//*[@id='result']/span[2]"))
        val veu = driver.findElement(By.xpath("//*[@id='result']/span[3]"))
        val private = driver.findElement(By.xpath("//*[@id='result']/span[4]"))

        assertContains("notes", notes.text, "Notes не совпадает")
        assertContains("veu", veu.text, "Veu не совпадает")
        assertContains("private", private.text, "Private не совпадает")
    }

    @Test
    @DisplayName(
        value = """
        Тест 3    
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Radio Button'
        4. Выбрать Yes
        5. Проверить что появилась надпись Yes
        6. Выбрать Impressive
        7. Проверить что появилась надпись Impressive
        8. Проверить что кнопка No недоступна
    """
    )
    fun testRadioButton() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val radioButtonMenu = driver.findElement(By.xpath("//span[text()='Radio Button']"))
        radioButtonMenu.click()

        val yesRadioButton = driver.findElement(By.xpath("//label[text()='Yes']"))
        yesRadioButton.click()

        val resultText = driver.findElement(By.cssSelector(".text-success"))
        assertTrue(resultText.text == "Yes", "Yes не появилось")

        val impressiveRadioButton = driver.findElement(By.xpath("//label[text()='Impressive']"))
        impressiveRadioButton.click()

        assertTrue(
            resultText.text == "Impressive",
            "Impressive не появилось"
        )

        val noRadioButton = driver.findElement(By.id("noRadio"))
        assertFalse(noRadioButton.isEnabled, "Кнопка No доступна")
    }
}