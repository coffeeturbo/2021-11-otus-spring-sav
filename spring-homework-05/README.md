# Домашнее задание 5

Создать приложение хранящее информацию о книгах в библиотеке

## Цель:

Цель: использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных
Результат: приложение с хранением данных в реляционной БД, которое в дальнейшем будем развивать

# Требования:

- Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД). Настоятельно рекомендуем использовать NamedParametersJdbcTemplate
- Предусмотреть таблицы авторов, книг и жанров.
- Предполагается отношение многие-к-одному (у книги один автор и жанр). Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).
- Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).
- Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться с помощью spring-boot-starter-jdbc.
- Написать тесты для всех методов DAO и сервиса работы с книгами.




# Домашнее задание 6

Переписать приложение для хранения книг на ORM

## Цель:

Цель: полноценно работать с JPA + Hibernate для подключения к реляционным БД посредством ORM-фреймворка
Результат: Высокоуровневое приложение с JPA-маппингом сущностей

# Требования:

- Использовать JPA, Hibernate только в качестве JPA-провайдера.
- Для решения проблемы N+1 можно использовать специфические для Hibernate аннотации @Fetch и @BatchSize.
- Добавить сущность "комментария к книге", реализовать CRUD для новой сущности.
- Покрыть репозитории тестами, используя H2 базу данных и соответствующий H2 Hibernate-диалект для тестов.
- Не забудьте отключить DDL через Hibernate
- @Transactional рекомендуется ставить только на методы сервиса.
- Это домашнее задание будет использоваться в качестве основы для других ДЗ
- Данная работа не засчитывает предыдущую!




# Домашнее задание 7

Библиотеку на Spring Data JPA

## Цель:

Цель: максимально просто писать слой репозиториев с применением современных подходов
Результат: приложение со слоем репозиториев на Spring Data JPA

# Требования:

- Переписать все репозитории по работе с книгами на Spring Data JPA репозитории.
- Используйте spring-boot-starter-data-jpa.
- Кастомные методы репозиториев (или с хитрым @Query) покрыть тестами, используя H2.
- @Transactional рекомендуется ставить на методы сервисов, а не репозиториев.
- Это домашнее задание будет использоваться в качестве основы для других ДЗ
- Данная работа не засчитывает предыдущую!