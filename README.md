# cloud-storage

Приложение - REST-сервис.

Предоставляет REST интерфейс для возможности загрузки файлов и вывода списка уже загруженных файлов пользователя.

server.port=5500

origins = "http://192.168.0.111:8080"

Client interface - [FRON](https://github.com/roman-rebrov/jd-homeworks/tree/master/diploma/netology-diplom-frontend)

VUE_APP_BASE_URL=http://localhost:5500/cloud

---
Cloud-Storage - это сервис для хранения и управления файлами в облачном хранилище.<br>
Доступ к хранилищу только для зарегестрированных пользователей.<br>
Файлы пользователей хранятся в индивидуальных директориях.<br>

Функции:
* Загрузить файл в облако.
* Изменить имя файла.
* Скачать файл.
* Удалить файл из облака.
---
Программа использует:
* Фреймворк - Spring Boot, Spring Data Jpa;
* Unit тесты;
* Интеграционные тесты;
* Сборщик - Maven;
* Запускается на Docker;
* СУБД MySql;
---
Приложение состоит из 8 основных компонентов:

- ClientController
```
Клиентский интерфейс для входа в аккаунт.
```
- StorageController
```
Клиентский интерфейс для доступа к хранилищу.
```
- ClientService
```
Сервис проверяет пользователя на наличие аккаунта в базе данных,
регистрирует его данные в ClientRepository.
```
- StorageService
```
Сервис проверяет входные данные, 
управляет облачным хранилищем.
```
- ClientRepository
```
Репозиторий хранит данные клиента in-memory.
```
- StorageRepository
```
Репозиторий выполняет операции с файлами в директории.
```
- UserJpaRepository
```
Подключается к базе данных и проводит операции 
в пользовательской таблице.
```
- FilesJpaRepository
```
Подключается к базе данных и проводит операции 
в файловой таблице.
```