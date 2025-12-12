package config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

public final class AppiumDriverFactory {

    private static final String DEFAULT_SERVER_URL = "http://127.0.0.1:4723";

    private AppiumDriverFactory() {
    }

    public static AndroidDriver createAndroidDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(System.getProperty("deviceName", "Android Emulator"))
                .setAppPackage("org.wikipedia")
                .setAppActivity("org.wikipedia.main.MainActivity")
                .setNoReset(true);

        String serverUrl = System.getProperty("appium.server.url", DEFAULT_SERVER_URL);
        try {
            return new AndroidDriver(new URL(serverUrl), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL: " + serverUrl, e);
        }
    }
}
