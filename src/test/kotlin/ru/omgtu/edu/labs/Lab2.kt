package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Lab2 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Тест 4
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Buttons'
        4. Нажать на кнопку Click Me
        5. Проверить что появилась надпись You have done a dynamic click
        6. Сделать двойной клик на кнопку Double Click Me
        7. Проверить что появилась надпись You have done a double click
        8. Сделать клик правой кнопкой кнопку Right Click Me
        9. Проверить что появилась надпись You have done a right click
    """
    )
    fun testButtonClicks() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val buttonsMenu = driver.findElement(By.xpath("//span[text()='Buttons']"))
        buttonsMenu.click()

        val clickMeButton = driver.findElement(By.xpath("//button[text()='Click Me']"))
        clickMeButton.click()

        val dynamicClickMessage = driver.findElement(By.id("dynamicClickMessage"))
        assertEquals(
            "You have done a dynamic click",
            dynamicClickMessage.text,
            "Тест некорректен"
        )

        val doubleClickMeButton = driver.findElement(By.id("doubleClickBtn"))
        val actions = Actions(driver)
        actions.doubleClick(doubleClickMeButton).perform()

        val doubleClickMessage = driver.findElement(By.id("doubleClickMessage"))
        assertEquals(
            "You have done a double click",
            doubleClickMessage.text,
            "Тест некорректен"
        )

        val rightClickMeButton = driver.findElement(By.id("rightClickBtn"))
        actions.contextClick(rightClickMeButton).perform()

        val rightClickMessage = driver.findElement(By.id("rightClickMessage"))
        assertEquals(
            "You have done a right click",
            rightClickMessage.text,
            "Тест некорректен"
        )
    }

    @Test
    @DisplayName(
        value = """
        Tecт 5
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Links'
        4. Нажать на кнопку Home
        5. Переключиться на открывшееся окно
        6. Проверить адрес открывшегося окна
        7. Переключиться на первое окно
        8. Нажать Moved
        9. Проверить что появилась надпись Link has responded with staus 301 and status text Moved Permanently
    """
    )
    fun testLinks() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val linksMenu = driver.findElement(By.xpath("//span[text()='Links']"))
        linksMenu.click()

        val homeLink = driver.findElement(By.id("simpleLink"))
        homeLink.click()

        val originalWindow = driver.windowHandle
        val allWindows = driver.windowHandles
        for (window in allWindows) {
            if (window != originalWindow) {
                driver.switchTo().window(window)
                break
            }
        }

        val currentUrl = driver.currentUrl
        assertEquals("https://demoqa.com/", currentUrl, "URL некорректен")

        driver.close()
        driver.switchTo().window(originalWindow)

        val movedLink = driver.findElement(By.id("moved"))
        movedLink.click()

        val wait = WebDriverWait(driver, Duration.ofSeconds(5))
        val linkResponseMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("linkResponse"))
        )

        assertContains(
            "Link has responded with staus 301 and status text Moved Permanently",
            linkResponseMessage.text,
            "Тест некорректен"
        )
    }


    @Test
    @DisplayName(
        value = """
        Тест 6
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Elements'
        3. Выбрать пункт 'Upload and Download'
        4. Загрузить файл
        5. Проверить в поле с именем файла его имя
        6. Проверить в поле с полным именем директории имя всего пути        
    """
    )
    fun testFileUpload() {
        driver.get("https://demoqa.com/")

        val elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"))
        elementsSection.click()

        val uploadDownloadMenu = driver.findElement(By.xpath("//span[text()='Upload and Download']"))
        uploadDownloadMenu.click()

        val uploadButton = driver.findElement(By.id("uploadFile"))
        // TODO: Почему-то IO некорректно вытягивает путь к файлу, захардкодил
        val filePath = "/Users/hq-my506mvyhc/Documents/avtotest/src/test/resources/"
        val fileName = "testFile.txt"
        uploadButton.sendKeys("$filePath$fileName")

        val uploadedFilePathField = driver.findElement(By.id("uploadedFilePath"))
        val uploadedFilePath = uploadedFilePathField.text
        // Проверяем и путь и имя 5 и 6 пункт
        assertContains("C:\\fakepath\\testFile.txt", uploadedFilePath, "Путь к файлу некорректен")
    }
}