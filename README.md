### Описание:

Простое CRUD-приложение, которое работает с базой данных PostgreSQL.
Использованы инструменты: Maven, Spring Boot, Spring JDBC.
Программа представляет собой телефонную книгу и набор команд, 
с помощью которых можно получать, добавлять и изменять данные в ней.


### Команды:

**Create:**

    curl -X POST -H 'Content-type: application/json' -d '
    {
        "name":"Ivan Petrov",
        "phone":"1234567890",
        "email":"petr@mail.com"
    }
    ' http://localhost:8080/phonebook/contact


**Read:**

    curl -X GET -H 'Content-type: application/json' http://localhost:8080/phonebook/contact?phone=1234567890


**Update:**

    curl -X PUT -H 'Content-type: application/json' -d '
    {
        "phone":"1234567890",
        "name":"Alex Sokolov"
    }
    ' http://localhost:8080/phonebook/contact


**Delete:**

    curl -X DELETE http://localhost:8080/phonebook/contact?phone=1234567890


**Get all contacts:**

    curl -X GET -H 'Content-type: application/json' http://localhost:8080/phonebook/contacts

