package ru.omgtu.edu

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.AssertionFailureBuilder
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

abstract class AbstractTest {

    protected lateinit var driver: WebDriver

    @BeforeEach
    protected fun beforeEach() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        val tesUa =
            "Mozilla/5.0 (Windows NT 4.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
        options.addArguments("--start-maximized")
        options.addArguments("--user-agent={$tesUa}")
        options.addArguments("--remote-allow-origins=*")
        options.addArguments("eager")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-extensions")
        driver = ChromeDriver(options)
    }

    @AfterEach
    protected fun afterEach() {
        driver.quit()
    }

    protected fun assertContains(expected: String, actual: String, messageOnError: String) {
        if (!actual.contains(expected)) {
            failNotEqual(expected, actual, messageOnError)
        }
    }

    private fun failNotEqual(expected: Any, actual: Any, messageOrSupplier: Any) {
        AssertionFailureBuilder.assertionFailure().message(messageOrSupplier).expected(expected).actual(actual)
            .buildAndThrow()
    }
}