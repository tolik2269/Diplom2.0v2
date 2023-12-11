# Дипломная работа профессии «Тестировщик ПО»
https://github.com/tolik2269/Diplom2.0v2.git

## Документы
* [План автоматизации](https://github.com/tolik2269/Diplom2.0v2/blob/main/documents/%D0%BF%D0%BB%D0%B0%D0%BD.md)
* [Отчёт по автоматизации](https://github.com/tolik2269/Diplom2.0v2/blob/main/documents/%D0%BE%D1%82%D1%87%D0%B5%D1%82%20%D0%BE%D0%B1%20%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0%D1%82%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8.md)
* [Отчёт по итогам тестирования](https://github.com/tolik2269/Diplom2.0v2/blob/main/documents/%D0%BE%D1%82%D1%87%D0%B5%D1%82%20%D0%BE%20%D1%82%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B8.md)
* [Скриншот о проведении автотестов](https://github.com/tolik2269/Diplom2.0v2/blob/main/documents/%D1%81%D0%BA%D1%80%D0%B8%D0%BD%D1%88%D0%BE%D1%82%20%D0%BE%20%D1%82%D0%B5%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B8%20%D0%B0%D0%B2%D1%82%D0%BE%D1%82%D0%B5%D1%81%D1%82%D0%BE%D0%B2.png)


## Описание приложения
Приложение предстовляёт собой веб-сервис, который предлагает купить тур по определённой цене двумя способами:
Обычная оплата по дебетовой карте.
Уникальная технология: выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* сервису платежей, далее Payment Gate;

* кредитному сервису, далее Credit Gate.
  
Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.
## Инуструкция по запуску тестов

1. Склонировать проект с https://github.com/tolik2269/Diplom2.0v2.git
   
3. Открыть проект в IntelliJ IDEA
 
5. Запустить контейнеры командой `docker-compose up --build`
   
7. Для запуска сервиса с указанием пути к базе данных использовать следующие команды:
   
* для mysql `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar`
  
* для postgresql `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar`
  
5. Sut открывается по адресу `http://localhost:8080/`
  
6. Запуск тестов стоит выполнить с параметрами, указав путь к базе данных в командной строке:
   
  * для mysql `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app -Dsut.url=http://localhost:8080 -Dselenide.browser=firefox"`
    
  * для postgresql `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:3306/app -Dsut.url=http://localhost:8080 -Dselenide.browser=firefox"
    
7. Для формирования отчета (Allure), после выполнения тестов выполнть команду `./gradlew allureReport`
   
8. После заврешения тестирования завершить работу приложения (CTRL+C) и остановить контейнеры командой `docker-compose down`


