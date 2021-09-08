## Техническое задание
Создать сервис, который обращается к сервису курсов валют, и отдает gif в ответ:
если курс по отношению к рублю за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
если ниже - отсюда https://giphy.com/search/broke

###Ссылки
REST API курсов валют - https://docs.openexchangerates.org/

REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide

###Must Have
Сервис на Spring Boot 2 + Java / Kotlin
Запросы приходят на HTTP endpoint, туда передается код валюты
Для взаимодействия с внешними сервисами используется Feign
Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
Для сборки должен использоваться Gradle
Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
###Nice to Have
Сборка и запуск Docker контейнера с этим сервисом
***
## Запуск
- Склонировать репозиторий, выполнив команду:   
  `git clone https://github.com/Anastasiya-Zhalnina/alfa-bank-currency.git`
- Перейдя в корневую папку проекта собрать проект:    
  `gradle build`
- Собрать докер-образ с произвольным именем, в нашем случае alfa-bank-currency:    
  `docker image build -t alfa-bank-currency .`
- Запустить контейнер с нашим образом:   
  `docker run -p 8080:8080 docker.io/library/alfa-bank-currency`
***
## Endpoints
- `/getCurrencyCodes`  
  Возвращает список доступных кодов валют.  
  **_Пример_**   
  `http://localhost:8080/getCurrencyCodes`
------
- `/getgif/{currencyCode}`  
  Возвращает гифку в зависимости от курса валюты.    
  **_Пример_**   
  `http://localhost:8080/getgif/AMD`

