package ru.omgtu.edu.independentWork

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ru.omgtu.edu.AbstractTest
import java.time.Duration

class Ind1 : AbstractTest() {

    private val startUrl = "https://www.drom.ru"

    @Test
    fun testMainPage() {
        driver.get(startUrl)
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val expectedTitle = "Дром - цены на машины"
        wait.until(ExpectedConditions.titleIs(expectedTitle))
        assertEquals(expectedTitle, driver.title, "Заголовок страницы не соответствует ожидаемому")
    }

    @Test
    fun testNavigation() {
        driver.get(startUrl)
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        val showMoreBtn =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-ftid='component_cars-list']//div[text()='Показать все']")))
        showMoreBtn.click()

        val lincolnBtn =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-ftid='component_cars-list']//a[text()='BMW']")))
        lincolnBtn.click()

        wait.until(ExpectedConditions.urlToBe("https://auto.drom.ru/bmw/"))
        assertContains("bmw", driver.currentUrl, "URL не соответствует ожидаемому")
    }

    @Test
    fun testCheckVin() {
        driver.get(startUrl)
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        var vinInput =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-ftid='autostory-widget_input']")))
        vinInput.sendKeys("32143")
        vinInput.sendKeys(Keys.ENTER)

        val errorMessage =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-ftid='autostory-widget']//span[@data-ftid='error_message']")))
        assertTrue(errorMessage.text.contains("Введите корректный VIN"), "Сообщение об ошибке не отображается")

        vinInput =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-ftid='autostory-widget_input']")))
        vinInput.clear()
        vinInput.sendKeys("Х927Х055")
        vinInput.sendKeys(Keys.ENTER)

        wait.until(ExpectedConditions.urlContains("vin.drom.ru"))
        assertTrue(driver.currentUrl.startsWith("https://vin.drom.ru"), "URL не изменился на ожидаемый")
    }

    @Test
    fun testCarousel() {
        driver.get(startUrl)
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        val carousel =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-ftid='component_premium-carousel']")))
        assertTrue(carousel.isDisplayed, "Карусель с объявлениями не отображается")

        val firstSlide =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-ftid='component_premium-carousel']//a[@data-ftid='component_premium-carousel_item'][1]")))
        val oldAlt = firstSlide.findElement(By.cssSelector("img")).getAttribute("alt")
        assertTrue(firstSlide.isDisplayed, "Первый слайд карусели не отображается")

        val nextBtn =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-ftid='component_premium-carousel']//button[@aria-label='листать вперед']")))
        (driver as JavascriptExecutor).executeScript("arguments[0].click();", nextBtn)

        nextBtn.click()
        Thread.sleep(1000)

        val newFirstSlide =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-ftid='component_premium-carousel']//a[@data-ftid='component_premium-carousel_item'][1]")))
        val newAlt = newFirstSlide.findElement(By.cssSelector("img")).getAttribute("alt")

        assertNotEquals(oldAlt, newAlt, "Первый слайд карусели не изменился после перетаскивания")
    }

    @Test
    fun openAdFromCarousel() {
        driver.get(startUrl)
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        val carousel =
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-ftid='component_premium-carousel']")))
        assertTrue(carousel.isDisplayed, "Карусель с объявлениями не отображается")

        val ad =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[7]/div[4]/div/div/div[2]")))
        ad.click()

        val leasing =
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[4]/div[1]/div[1]/div[2]/div[2]")))

        assertTrue(leasing.isDisplayed)
    }
}