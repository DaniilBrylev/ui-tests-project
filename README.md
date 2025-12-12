Проект представляет собой Maven-проект с автоматизированными UI-тестами, реализованными на языке Java (11) с использованием TestNG.
В рамках проекта разработаны автотесты для:

веб-приложения — демо-магазина Demoblaze;

мобильного Android-приложения — Wikipedia (с использованием Appium).

Проект создан в учебных целях, но по структуре и подходам приближен к реальным задачам автоматизированного тестирования и может быть использован как часть портфолио.

Используемый стек и зависимости
Основные технологии

Java 11

Maven

TestNG 7.10.2

Web-тестирование

Selenium WebDriver 4.20.0 (подключение через BOM)

WebDriverManager 5.8.0

Google Chrome

Mobile-тестирование

Appium Java Client 9.2.2

Appium Server 2.x

UiAutomator2

Android SDK / Emulator

Структура проекта
Конфигурация

pom.xml
Содержит зависимости (Selenium BOM, Appium Java Client), а также плагины:

maven-compiler-plugin (release 11)

maven-surefire-plugin (запуск через testng.xml)

testng.xml
Описывает два набора тестов:

tests.web.WebUiTests

tests.mobile.WikipediaMobileTests

src/test/java/config

WebDriverFactory.java
Инициализация ChromeDriver с использованием WebDriverManager.
Поддерживаются параметры:

запуск браузера с опцией --start-maximized;

headless-режим через -Dheadless=true.

AppiumDriverFactory.java
Создание AndroidDriver с автоматизацией UiAutomator2.
Используются параметры:

appPackage = org.wikipedia

appActivity = org.wikipedia.main.MainActivity

noReset = true

URL Appium-сервера задаётся через -Dappium.server.url
(по умолчанию http://127.0.0.1:4723).

BaseWebTest.java
Базовый класс веб-тестов: инициализация WebDriver и WebDriverWait.

BaseMobileTest.java
Базовый класс мобильных тестов: инициализация AndroidDriver,
WebDriverWait и implicit wait (10 секунд).

Web-тесты
src/test/java/pages/web

HomePage.java
Загрузка главной страницы, выбор категории, открытие первого товара, работа с формой Contact.

ProductPage.java
Получение и проверка заголовка товара.

src/test/java/tests/web

WebUiTests.java
Реализовано 4 стабильных сценария:

Загрузка главной страницы.

Переход в категорию.

Открытие товара и проверка его названия.

Отправка формы Contact с проверкой появления alert-сообщения.

Mobile-тесты (Wikipedia)
src/test/java/pages/mobile

WikipediaSearchPage.java
Пропуск онбординга, выполнение поиска, открытие первого результата, проверка его видимости.

WikipediaArticlePage.java
Получение заголовка статьи, прокрутка страницы с помощью UiScrollable, проверка наличия текста References.

src/test/java/tests/mobile

WikipediaMobileTests.java
Реализовано 3 стабильных сценария:

Поиск статьи и проверка первого результата.

Открытие статьи и проверка заголовка.

Прокрутка статьи до блока References и проверка его отображения.

Запуск тестов
Все тесты
mvn test

Только веб-тесты
mvn test -Dtest=tests.web.WebUiTests


Headless-режим:

mvn test -Dtest=tests.web.WebUiTests -Dheadless=true

Только мобильные тесты
mvn test -Dtest=tests.mobile.WikipediaMobileTests


Перед запуском мобильных тестов необходимо:

запустить Appium Server;

запустить Android-эмулятор или подключить устройство;

убедиться, что приложение Wikipedia (org.wikipedia) установлено.

Примечания

WebDriverManager автоматически загружает браузерные драйверы.

Для Appium используется UiAutomator2 с параметром noReset=true, что позволяет пропускать онбординг Wikipedia.

В тестах используются явные ожидания (WebDriverWait), Thread.sleep не применяется.

Проект реализован с использованием Page Object Model, код структурирован и расширяем.