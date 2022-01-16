1. Create

curl -X POST -H 'Content-type: application/json' -d '
{
    "name":"Petr Petrov",
    "phone":"1234567890",
    "email":"petr@mail.com"
}
' http://localhost:8080/phonebook/contact


2. Read:

curl -X GET -H 'Content-type: application/json' http://localhost:8080/phonebook/contact?phone=1234567890

3. Update
curl -X PUT -H 'Content-type: application/json' -d '
{
    "phone":"1234567890",
    "name":"Dima Ten"
}
' http://localhost:8080/phonebook/contact


4. Delete
curl -X DELETE http://localhost:8080/phonebook/contact?phone=1234567890


5. Get All contacts

curl -X GET -H 'Content-type: application/json' http://localhost:8080/phonebook/contacts

