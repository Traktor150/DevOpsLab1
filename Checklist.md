# Checklista för Journalsystem-Labb

## Projektstruktur

- [ ] **Projektuppsättning:**
  - Backend i Spring Boot
  - Frontend i React.js
  - Relationsdatabas (MySQL eller PostgreSQL)
  - Projektet körs i Docker med tre olika containrar:
    - [ ] Backend-container
    - [ ] Frontend-container
    - [ ] Databas-container

## Backend (Spring Boot)

- [ ] **API & Databas:**

  - Implementera ett REST API i Spring Boot för att hantera data mellan backend och frontend.
  - Skapa databasmodeller för följande entiteter:
    - [ ] **Patient**
    - [ ] **Observation** (enskild observation)
    - [ ] **Encounter** (möte med patient)
    - [ ] **Condition** (diagnos)
    - [ ] **Practitioner** (sjukvårdspersonal)
    - [ ] **Organization** (organisation)
    - [ ] **Location** (plats)
    - [ ] Eventuella ytterligare entiteter för användare och meddelanden
  - Skapa CRUD-endpoints för varje entitet där det är relevant.

- [ ] **Användarhantering & Roller:**
  - Skapa användarroller: **Patient**, **Läkare**, **Övrig personal**
  - Implementera autentisering och inloggning
  - Rollspecifika behörigheter för olika typer av användare:
    - [ ] **Läkare & Övrig personal** kan skapa patientnoteringar och fastställa diagnoser.
    - [ ] **Läkare** kan se all information om en specifik patient.
    - [ ] **Patient** kan se sin egen information.

## Frontend (React.js)

- [ ] **Användargränssnitt:**

  - Skapa inloggnings- och registreringssidor för användare med olika roller.
  - Implementera sidor för att visa och hantera:
    - [ ] Patientens information (för läkare och patienten själv)
    - [ ] Noteringar och diagnoser (för läkare och övrig personal)
  - Lägg till funktionalitet för att visa olika vyer och funktioner baserat på användarroll.

- [ ] **Meddelandesystem:**
  - Implementera ett meddelandesystem där:
    - [ ] **Patient** kan skicka meddelanden till läkare och övrig personal samt se svar.
    - [ ] **Läkare & Övrig personal** kan se och svara på meddelanden från patienter.

## Databas (MySQL/PostgreSQL)

- [ ] **Databasstruktur:**
  - Skapa relationsdatabas med tabeller för varje entitet.
  - Använd lämpliga relationer mellan entiteter (ex. en-to-många och många-till-många där det behövs).
  - Säkerställ att tabellerna kan lagra relevant information för alla funktioner ovan.

## Docker

- [ ] **Docker-konfiguration:**
  - Sätt upp Docker Compose med tre containrar:
    - [ ] **Backend-container**: kör Spring Boot-applikationen.
    - [ ] **Frontend-container**: kör React.js-applikationen.
    - [ ] **Databas-container**: kör MySQL eller PostgreSQL.
  - Säkerställ att containrarna kan kommunicera med varandra genom nätverk.

## Testning och Validering

- [ ] **Systemtestning:**

  - Testa användarregistrering och inloggning för alla roller.
  - Verifiera att behörigheter fungerar korrekt för olika roller:
    - [ ] Läkare och övrig personal kan skapa noteringar och diagnoser.
    - [ ] Läkare kan se all patientinformation.
    - [ ] Patient kan se sin egen information.
  - Kontrollera att meddelandesystemet fungerar för att skicka och ta emot meddelanden mellan patient och vårdpersonal.

- [ ] **Funktionstestning:**
  - Testa CRUD-operationer för alla relevanta entiteter via API:et.
  - Testa att frontend-komponenter och användargränssnitt fungerar för alla användare och roller.
