# Development

Proces developmentu `Turi` zaczyna się od stworzenia brancha podpiętego do wykonywanego zadania. Następnie w konsoli w środowisku lokalnym uruchamiamy komendę `git clone branch` i w pobranym projekcie `git fetch` i `git checkout branch`. 

**Setup**\
Do pracy nad projektem  w środowisku lokalnym potrzebujemy:
* Docker Desktop
* PostgreSQL
* Java
* Node.js

**Praca z bazą danych**
1. Uruchomienie aplikacji Docker Desktop
2. Otworzenie konsoli i przejście do folderu projektu.
3. Puszczenie polecenia uruchamiającego kontener z bazą danych:
    ```sh
    docker compose up
    ```
4. Przejście do np. aplikacji `pgAdmin 4` lub narzędzia `psql`, połączenie się do bazy i praca z nią.
5. Po pracy puszczenie polecenia:
    ```sh
    docker compose down
    ```
