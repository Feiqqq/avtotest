package ru.omgtu.edu.labs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Lab6 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Написать 16 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Date Picker'
        4. В поле 'Select Date' выбрать 1 декабря 2023 годя
        5. В поле 'Date And Time' 2 ноября 2022 года 20:00
    """
    )
    fun testDatePicker() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Date Picker']"))).click()

        val dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#datePickerMonthYearInput")))
        dateInput.click()

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-datepicker")))

        val yearSelector =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__year-select")))
        yearSelector.click()
        yearSelector.sendKeys("2023")

        Thread.sleep(500)

        val monthSelector =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__month-select")))
        monthSelector.click()
        monthSelector.sendKeys("December")

        Thread.sleep(500)

        val daySelector =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__day.react-datepicker__day--001")))
        daySelector.click()
        val datetimeInput =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#dateAndTimePickerInput")))
        datetimeInput.click()

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-datepicker")))

        val yearSelector2 =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__year-read-view")))
        yearSelector2.click()
        val yearOption =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='react-datepicker__year-dropdown']//div[text()='2022']")))
        yearOption.click()

        Thread.sleep(500)

        val monthSelector2 =
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__month-read-view")))
        monthSelector2.click()
        val monthOption =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='react-datepicker__month-dropdown']//div[text()='November']")))
        monthOption.click()

        Thread.sleep(500)

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__day--002"))).click()

        Thread.sleep(500)

        val timeInput =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='react-datepicker__time-list']//li[text()='20:00']")))
        timeInput.click()
        Thread.sleep(500)
    }

    @Test
    @DisplayName(
        value = """
        Написать 17 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Slider'
        4. Сдвинуть слайдер на значение 50
        5. Проверить что в окне справа значение 50
    """
    )
    fun testSlider() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Slider']"))).click()

        val slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".range-slider__wrap")))
        val sliderWidth = slider.size.width
        val moveTo = (sliderWidth * 0.5).toInt()

        Thread.sleep(1000) // не убирать

        val actions = Actions(driver)
        actions.clickAndHold(slider).moveByOffset(moveTo - (sliderWidth / 2), 0).release().perform()

        val outputValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sliderValue")))
            .getAttribute("value")
        assertEquals("50", outputValue, "Слайдер не в позиции 50")
    }

    @Test
    @DisplayName(
        value = """
        Написать 18 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Tabs'
        4. Открыть вкладку What
        5. Проверить что на ней есть текст
        6. Открыть вкладку Origin
        7. Проверить что на ней есть текст
        8. Открыть вкладку Use
        9. Проверить что на ней есть текст
        10. Проверить что вкладка More недоступна
    """
    )
    fun testTabs() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        val widgets = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']")))
        widgets.click()

        Thread.sleep(1000)

        val tabsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Tabs']")))
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", tabsTab)
        tabsTab.click()

        val whatTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#demo-tab-what")))
        whatTab.click()
        val whatText =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#demo-tabpane-what.show")))
        assertTrue(whatText.text.isNotEmpty())
        val originTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#demo-tab-origin")))
        originTab.click()
        val originText =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#demo-tabpane-origin.show")))
        assertTrue(originText.text.isNotEmpty())
        val useTab = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#demo-tab-use")))
        useTab.click()
        val useText =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#demo-tabpane-use.show")))
        assertTrue(useText.text.isNotEmpty())
        val moreTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#demo-tab-more")))
        assertTrue(
            moreTab.getAttribute("class")
                .contains("disabled")
        )
    }
}