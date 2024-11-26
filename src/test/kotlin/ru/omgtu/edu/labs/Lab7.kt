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

class Lab7 : AbstractTest() {

    @Test
    @DisplayName(
        value = """
        Написать 19 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Widgets'
        3. Выбрать пункт 'Select Menu'
        4. В поле 'Select Value' выбрать 'A root option'
        5. В поле 'Select One' выбрать 'Ms.'
        6. В поле 'Old Style Select Menu' выбрать 'Black'
        7. В поле 'Multiselect drop down' выбрать: Black, Red
        8. В поле 'Standard multi select' выбрать 'Opel'
    """
    )
    fun testSelectMenu() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val widgetsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']")))
        widgetsSection.click()

        val selectMenuTab =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Select Menu']")))
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", selectMenuTab)
        selectMenuTab.click()

        val selectValue = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#withOptGroup")))
        selectValue.click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='A root option']"))).click()

        val selectOne = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#selectOne")))
        selectOne.click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Ms.']"))).click()

        val oldStyleSelect = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#oldSelectMenu")))
        oldStyleSelect.click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()='Black']"))).click()

        val multiSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Select...']")))
        multiSelect.click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Black']"))).click()
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Red']"))).click()

        val standardMultiSelect = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cars")))
        standardMultiSelect.click()
        wait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[@value='opel']")))
        driver.findElement(By.id("selectMenuContainer")).click()
    }

    @Test
    @DisplayName(
        value = """
        Написать 20 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Interaction'
        3. Выбрать пункт 'Sortable'
        4. Перейти на вкладку List
        5. Отсортировать значения в убывающем порядке
    """
    )
    fun testSortableList() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val interactionSection = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Interactions']"))
        )
        interactionSection.click()
        val sortableTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sortable']"))
        )
        sortableTab.click()

        val listTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("#demo-tab-list"))
        )
        listTab.click()

        val sortedNumbers = listOf("One", "Two", "Three", "Four", "Five", "Six")
        val actions = Actions(driver)

        val yStart = driver.findElement(By.cssSelector(".tab-content")).location.getY()
        val root = driver.findElement(By.id("app"))

        sortedNumbers.forEach { sn ->
            val listItem =
                driver.findElement(By.xpath("//div[@class='list-group-item list-group-item-action' and text()='$sn']"))
            val yOffset = listItem.location.y.minus(yStart)

            actions.dragAndDropBy(listItem, 0, -yOffset).perform()
            root.click()
            Thread.sleep(500)
        }

        val newList = driver.findElements(By.cssSelector(".list-group-item"))
        val texts = newList.map { it.text.trim() }.filter { it.isNotEmpty() }

        assertTrue(texts == sortedNumbers.reversed(), "Сортировка некорректна: ${texts.joinToString(", ")}")
    }

    @Test
    @DisplayName(
        value = """
        Написать 21 тест
        1. Перейти на страницу https://demoqa.com/
        2. Перейти в раздел 'Interaction'
        3. Выбрать пункт 'Selectable'
        4. Перейти на вкладку Grid
        5. Выбрать все значения
        6. Снять все значения
        7. Выбрать только Five
    """
    )
    fun testSelectable() {
        driver.get("https://demoqa.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val interactionSection = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Interactions']"))
        )
        interactionSection.click()

        val selectableTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Selectable']"))
        )
        selectableTab.click()

        val gridTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("#demo-tab-grid"))
        )
        gridTab.click()

        val allGridItems = driver.findElements(By.cssSelector("#gridContainer .list-group-item"))
        val allGridItemsLen = allGridItems.size

        for (i in 0 until allGridItemsLen) {
            val element = wait.until(ExpectedConditions.elementToBeClickable(allGridItems[i]))
            ((driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", element))
            wait.until(ExpectedConditions.elementToBeClickable(element)).click()
            Thread.sleep(200)
        }

        for (i in 0 until allGridItemsLen) {
            wait.until(ExpectedConditions.elementToBeClickable(allGridItems[i])).click()
            Thread.sleep(200)
        }

        val fiveItem = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='gridContainer']//li[text()='Five']"))
        )
        fiveItem.click()

        val selectedItems = driver.findElements(By.cssSelector(".list-group-item.active"))
        assertEquals(1, selectedItems.size, "Только один элемент должен быть выбран")
        assertEquals("Five", selectedItems[0].text, "Выбранный элемент должен быть 'Five'")
    }
}