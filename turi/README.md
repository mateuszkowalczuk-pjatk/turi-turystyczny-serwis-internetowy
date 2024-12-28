# Turi
Projekt zawiera część backendową Turystycznego Serwisu Internetowego.


# Wymagania techniczne
Do pracy z projektem wymagane są:
* **Java** - wersja 17.0.10
* **Spring Boot** - wersja 3.2.5
* **Gradle** - wersja 8.7


# Lokalne uruchomienie
Do uruchomienia projektu potrzebne są:

* poprawnie skonfigurowany plik **application.yml** z:
  * hasłem do **email** google 
  * kluczem **aplikacji**
  * kluczem do systemu płatności **Stripe**
  * kluczem **webhook** do systemu płatności **Stripe**
  * kluczem do **API CEIDG**



* uruchomiona baza danych **PostgreSQL** w **Docker**. Proces uruchomienia:
  * Przejdź do folderu głównego projektu: **./turi-turystyczny-serwis-internetowy**
  * Upewnij się, że posiadasz włączoną aplikacji **Docker Desktop**
  * Uruchom plik **docker-compose.yml** za pomocą polecenia: **docker compose up**
  * Po zakończeniu pracy wyłącz bazę danych za pomocą polecenia: **docker compose down**


# Testowaine
Uruchomiony projekt Turi można testować manualnie za pomocą wbudowanego narzędzia **Swagger UI**. Żeby to zrobić po uruchomieniu aplikacji wpisz w przeglądarce **http://localhost:8080/swagger-ui/index.html**.

Oprócz testów manualnych, projekt posiada **testy integracyjne** i **jednostkowe** dla każdego modułu, które można uruchomić przechodząc do sekcji **Gradle/turi/Tasks/verification** i wywołująć **test**.


# Stripe CLI
Żeby projekt mógł lokalnie pracować z system płatności **Stripe** musi być on uruchomiony przez konsole w postaci **Stripe CLI**. Jak to zrobić:
1. Pracując na systemie Mac OS należy otworzyć konsolę i uruchomić: **brew install stripe/stripe-cli/stripe** 
2. Sprawdzić czy Stripe został pobrany: **stripe --version** 
3. Zautoryzować się przez: **stripe login** -> **enter** -> **allow**
4. Uruchomić w konsoli: **stripe listen --forward-to localhost:8080/api/payment/webhook**
5. Pobrać klucz webhook z **'Ready! Your webhook signing secret is: webhook_key'**
6. Ustawić klucz webhook w **application.yml**: **stripe.webhook-secret-key**


# Zdjęcia
**Lokalnie**, zdjęcia są przechowywane w folderze **./uploads/**. Każdy dodany plik zdjęciowy jest w nim zapisywany, a jego ścieżka w bazie danych, dzięki czemu można następnie pobrać zdjęcie podając ścieżkę do statycznego zasobu przechowywanego w backendzie.

**Produkcyjnie**, zdjęcia będą przechowywane w **Azure Blob Storage**, w skutek czego do poprawnie działającej aplikacji konieczne jest zmienienie w **application.yml**, **image.storage.mode** na **azure**, uzupełnienie pola **azure-url** oraz uzupełnienie pól w sekcji **azure.storage**.