## Технологии
  - Java 17
  - Gradle 8.4
  - Lombok
  - Mapstruct
  - JDBC
  - SnakeYAML
  - PostgreSql
  - Aspectj
  - Jackson 
  - Jaxb 
  - Mockito
  - Junit 5
  - Assertj
  - Validation (org.glassfish.javax.el with hibernate-validator)

## Подключение
- В файле [application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml) 
  - настройки подключения к БД
      ```yaml 
      url: jdbc:postgresql://localhost:3254/refl-task
      username: postgres
      password: postgres
      ```
  - настройки кэша 
    ```yaml
    cache:
        algorithm-type: LFU
        capacity: 13
        cache-value-id-field-name: id
    ```
    *cache-value-id-field-name - это __имя поля__ уникального идентификатора объекта, по которому будут кэшироваться данные, 
  значения берутся с помощью рефлексии
- Скрипт создания таблицы БД: [schema.sql](src%2Fmain%2Fresources%2Fschema.sql)

## Запуск
[JsonUtil.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Futil%2FJsonUtil.java) и [XmlUtil.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Futil%2FXmlUtil.java)
содержат методы для прочтения данных из [resources/json](src%2Fmain%2Fresources%2Fjson) и [resources/xml](src%2Fmain%2Fresources%2Fxml). В классе [Main.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2FMain.java)
вызываются методы и заполняется БД

## Описание
- [YamlApplicationProperties.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Futil%2Fproperty%2FYamlApplicationProperties.java) - для считывания настроек из application.yaml
- [JsonUtil.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Futil%2FJsonUtil.java) - для получения данных из json 
- [XmlUtil.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Futil%2FXmlUtil.java) - для получения данных их xml
  - [UserDto.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fdto%2FUserDto.java) содержит __@XmlRootElement__ для работы с xml
- [UserValidator.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fvalidator%2FUserValidator.java) - валидация данных, которые идут в сервис
- Класс сущности: [User.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fmodel%2FUser.java)
- Dto: [UserDto.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fdto%2FUserDto.java)
- [UserServiceImpl.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fservice%2Fimpl%2FUserServiceImpl.java) - сервисный слой работы с данными
- Реализация кэша:
  - [LFUCacheHandler.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fcache%2Fhandler%2FLFUCacheHandler.java)
  - [LRUCacheHandler.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fcache%2Fhandler%2FLRUCacheHandler.java)
- Аспекты в [cache/proxy/](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fcache%2Fproxy)
  - [CacheAspect.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fcache%2Fproxy%2FCacheAspect.java)
  - Post, Put, Get, Delete
- [CacheFactory.java](src%2Fmain%2Fjava%2Fby%2Fkihtenkoolga%2Fcache%2FCacheFactory.java) - для регистрирования алгоритма кэша в памяти

## Идея 2
Была вторая идея:
Использовать какойто базовый класс: "BaseEntity" c уникальным полем "id", от которого будут наследоваться другие модели.
Тогда в кэше можно было бы избавиться от дженериков и рефлексии.

На суть задания это никак не влияет просто интересный вариант решения 
