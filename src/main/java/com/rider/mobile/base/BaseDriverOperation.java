package com.rider.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Peng.Zhao on 2016/3/24.
 */
public class BaseDriverOperation {
    public static Logger logger = LogManager.getLogger(BaseDriverOperation.class.getName());

    public static void standByApp(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 15);
        if (locatorType.equalsIgnoreCase("id")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
        } else if (locatorType.equalsIgnoreCase("xpath")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
        } else {
            logger.error("启动页不可识别");
        }
    }

    public static void destroyDriver(AppiumDriver<MobileElement> driver) throws Exception{
        driver.quit();
    }

    public static void waitAction(String dataSet) {
        long sec = Integer.parseInt(dataSet);
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取屏幕高度
    public static int getScreenHeight(AppiumDriver<MobileElement> driver) {
        return driver.manage().window().getSize().height;
    }

    // 获取屏幕宽度
    public static int getScreenWidth(AppiumDriver<MobileElement> driver) {
        return driver.manage().window().getSize().width;
    }

    // 获取屏幕元素
    public static MobileElement getElement(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue) throws Exception {
        String locatorTypeKey = locatorType.toUpperCase();
        MobileElement element = null;
        switch (locatorTypeKey) {
            case "ID":
                element = driver.findElementById(locatorValue);
                break;
            case "ACCESSIBILITYID":
                element = driver.findElementByAccessibilityId(locatorValue);
                break;
            case "XPATH":
                element = driver.findElementByXPath(locatorValue);
                break;
            case "NAME":
                element = driver.findElementByName(locatorValue);
                break;
            case "CSS":
                element = driver.findElementByCssSelector(locatorValue);
                break;
            case "CLASSNAME":
                element = driver.findElementByClassName(locatorValue);
                break;
            case "LINKTEXT":
                element = driver.findElementByLinkText(locatorValue);
                break;
            case "PARTIALLINKTEXT":
                element = driver.findElementByPartialLinkText(locatorValue);
                break;
            case "TAGNAME":
                element = driver.findElementByTagName(locatorValue);
                break;
            default:
                // 临时日志输入
                logger.error("定位错误");
        }

        return element;
    }

    public static void click(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            element = getElement(driver, locatorType, locatorValue);
            wait.until(ExpectedConditions.visibilityOf(element));
            // 需要增加点击前的准备语句
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void input(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            element = getElement(driver, locatorType, locatorValue);
            wait.until(ExpectedConditions.visibilityOf(element));
            // 需要增加输入前的准备语句
            element.sendKeys(dataSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            element = getElement(driver, locatorType, locatorValue);
            wait.until(ExpectedConditions.visibilityOf(element));
            // 长按文本框2秒
            TouchAction action = new TouchAction(driver);
            action.longPress(element, 2).release().perform();
            // 删除文本框中的已有文字
            driver.getKeyboard().sendKeys(Keys.DELETE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void longPress(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            element = getElement(driver, locatorType, locatorValue);
            wait.until(ExpectedConditions.visibilityOf(element));
            TouchAction action = new TouchAction(driver);
            action.longPress(element, Integer.parseInt(dataSet)).release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过元素进行放大
    public static void pinchByElement(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            element = getElement(driver, locatorType, locatorValue);
            wait.until(ExpectedConditions.visibilityOf(element));
            driver.pinch(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过坐标进行放大
    public static void pinchByPosition(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int x = getScreenHeight(driver);
            int y = getScreenWidth(driver);
            driver.pinch(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过元素进行缩小
    public static void zoomByElement(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        MobileElement element = null;
        try {
            element = getElement(driver, locatorType, locatorValue);
            driver.zoom(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过坐标进行缩小
    public static void zoomByPosition(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int x = getScreenHeight(driver);
            int y = getScreenWidth(driver);
            driver.zoom(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向左滑屏
    public static void swipeToLeft(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int height = getScreenHeight(driver);
            int width = getScreenWidth(driver);
            int swipeTimes = Integer.parseInt(dataSet);
            for (int i = 0; i < swipeTimes; ++i) {
                Thread.sleep(1000);
                driver.swipe(width * 4 / 5, height / 2, width / 20, height / 2, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向右滑屏
    public static void swipeToRight(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int height = getScreenHeight(driver);
            int width = getScreenWidth(driver);
            int swipeTimes = Integer.parseInt(dataSet);
            for (int i = 0; i < swipeTimes; ++i) {
                Thread.sleep(1000);
                driver.swipe(width / 5, height / 2, width * 19 / 20, height / 2, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向上滑动
    public static void swipeToUp(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int height = getScreenHeight(driver);
            int width = getScreenWidth(driver);
            int swipeTimes = Integer.parseInt(dataSet);
            for (int i = 0; i < swipeTimes; ++i) {
                Thread.sleep(1000);
                driver.swipe(width / 2, height * 4 / 5, width / 2, height / 20, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 向下滑动
    public static void swipeToDown(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
        try {
            int height = getScreenHeight(driver);
            int width = getScreenWidth(driver);
            int swipeTimes = Integer.parseInt(dataSet);
            for (int i = 0; i < swipeTimes; ++i) {
                Thread.sleep(1000);
                driver.swipe(width / 2, height / 5, width / 2, height * 19 / 20, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 状态: [废弃]可参见https://github.com/appium/appium/issues/6242
    // 滚动到特定元素
//    public static void scrollTo(AppiumDriver<MobileElement> driver, String locatorType, String locatorValue, String dataSet) throws Exception {
//        try {
//            driver.scrollTo(dataSet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
