# Development

Proces developmentu `Turi` zaczyna się od stworzenia brancha podpiętego do wykonywanego zadania. Następnie w konsoli w środowisku lokalnym uruchamiamy komendę `git clone branch` i w pobranym projekcie `git fetch` i `git checkout branch`. 

**Setup**\
Do pracy nad projektem  w środowisku lokalnym potrzebujemy:
* Docker Desktop
* PostgreSQL
* Java
* Node.js

**Praca z bazą danych**
1. Przejście do folderu projektu.
2. Otworzenie pliku `docker-compose.yml` i zakomentowanie sekcji `services:turi` i `services:turi-ui`.
3. Otworzenie konsoli i przejście do folderu projektu.
4. Puszczenie polecenia uruchamiającego kontener z bazą danych:
    ```sh
    docker compose up
    ```
5. Przejście do np. aplikacji `pgAdmin 4` lub narzędzia `psql`, połączenie się do bazy i praca z nią.
6. Po pracy puszczenie polecenia:
    ```sh
    docker compose down
    ```
7. Na koniec przywrócenie pliku `docker-compose.yml` do stanu początkowego i wrzucenie zmian na brancha.

**Praca z backendem**
1. Przejście do folderu projektu.
2. Otworzenie pliku `docker-compose.yml` i zakomentowanie sekcji `services:turi` i `services:turi-ui`.
3. Otworzenie konsoli i przejście do folderu projektu.
4. Puszczenie polecenia uruchamiającego kontener z bazą danych:
    ```sh
    docker compose up
    ```
5. Przejście do np. aplikacji `Intellij IDEA` i praca z backendem, który podczas lokalnego uruchomienia jest połączony z bazą danych.
6. Po pracy puszczenie polecenia:
    ```sh
    docker compose down
    ```
7. Na koniec przywrócenie pliku `docker-compose.yml` do stanu początkowego i wrzucenie zmian na brancha.

**Praca z frontendem**
1. Przejście do folderu projektu.
2. Otworzenie pliku `docker-compose.yml` i zakomentowanie sekcji `services:turi-ui`.
3. Otworzenie konsoli i przejście do folderu projektu.
4. Puszczenie polecenia uruchamiającego kontenery z bazą danych i backendem:
    ```sh
    docker compose up
    ```
5. Przejście do np. aplikacji `WebStorm` lub `Visual Studio Code`i praca z frontendem, który podczas lokalnego uruchomienia komunikuje się przez REST z backendem.
6. Po pracy puszczenie polecenia:
    ```sh
    docker compose down
    ```
7. Na koniec przywrócenie pliku `docker-compose.yml` do stanu początkowego i wrzucenie zmian na brancha.

**Testy**
1. Otworzenie konsoli i przejście do folderu projektu.
2. Puszczenie polecenia uruchamiającego kontenery z bazą danych, backendem i frontendem:
    ```sh
    docker compose up
    ```
3. Testowanie projektu w środowisku lokalnym.
4. Po pracy puszczenie polecenia:
    ```sh
    docker compose down
    ```