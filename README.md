[[_TOC_]]
# OmegaSoftware

Je radni zadatak prvog koraka selekcijskog postupka za Omega software
>https://www.omega-software.eu/
>
## Zadatak:

```text
Zadatak – Java developer
Zadatak je kreirati CRUD aplikaciju za kupoprodajne ugovore i povezane artikle, koristeći Javu i
SpringBoot.
Prije rješavanja zadatka javite na mail procjenu koliko vremena trebate za rješavanje zadatka, i do kada
ćete ga završiti. Ukoliko nešto niste odradili, a trebalo bi odraditi kako bi rješenje bilo spremno za
produkciju, označite to S TODO komentarima.
Opis funkcionalnosti
Aplikacija treba omogućiti spremanje u bazu, dohvat i uređivanje kupoprodajnih ugovora i artikala kroz
sljedeće APIje:
- API za dohvat svih kupoprodajnih ugovora uz mogućnost filtriranja ugovora prema imenu i
aktivnosti. Primjer odgovora je u Prilogu 1.
- Dohvat jednog kupoprodajnog ugovora. Odgovor treba sadržavati sve podatke o
kupoprodajnom ugovoru, uključujući i povezane artikle.
- Kreiranje novog kupoprodajnog ugovora. Svi podaci o kupoprodajnom ugovoru su obavezni.
- Uređivanje kupoprodajnog ugovora.
- Soft delete kupoprodajnog ugovora.
- CRUD artikala kupoprodajnog ugovora.
Dostupni artikli se nalaze u unaprijed unesenom šifrarniku. Aktivni ugovori su oni koji su u statusu
„KREIRANO“ ili „NARUČENO“, neaktivni ugovori su oni koji su u statusu „ISPORUČENO“. Novom
kupoprodajnom ugovoru se automatski dodjeljuje status „KREIRANO“. Rok isporuke je moguće mijenjati
samo aktivnom kupoprodajnom ugovoru. Status „KREIRANO“ može prijeći samo u status „NARUČENO“,
a status „NARUČENO“ može prijeći samo u status „ISPORUČENO“. Moguće je brisati samo kupoprodajne
ugovore koji su u statusu „KREIRANO“. Broj ugovora se kreira iz dva dijela, prvi je sekvenca rednih
brojeva, a drugi je godina u kojoj je ugovor kreiran. Potrebno je impelmentirati sve logične validacije.
Potrebno je omogućiti autentifikaciju putem JWT tokena, i kreirati barem jedan unit test.
Aplikacija treba koristiti PostgreSQL bazu podataka. Uz kod aplikacije potrebno je isporučiti i upute za
pokretanje aplikacije u Dockeru.
Prilog 1. Kupoprodajni ugovori
[
    {
        id: 1,
        kupac: &quot;Petra Kranjčar&quot;,
        broj_ugovora: &quot;1/2024&quot;,
        datum_akontacije: &quot;2024-01-04&quot;,
        rok_isporuke: &quot;2024-04-20&quot;

        status: &quot;KREIRANO&quot;
    },
    {
        id: 2,
        kupac: &quot;Franko Kasun&quot;,
        broj_ugovora: &quot;2/2024&quot;,
        datum_akontacije: &quot;2024-03-01&quot;,
        rok_isporuke: &quot;2024-05-01&quot;
        status: &quot;ISPORUČENO&quot;
    },
    {
        id: 3,
        kupac: &quot;Stjepan Babić&quot;,
        broj_ugovora: &quot;3/2024&quot;,
        datum_akontacije: &quot;2024-03-03&quot;,
        rok_isporuke: &quot;2024-04-15&quot;
        status: &quot;NARUČENO&quot;
    },
    {
        id: 4,
        kupac: &quot;Tia Janković&quot;,
        broj_ugovora: &quot;4/2024&quot;,
        datum_akontacije: &quot;2024-03-14&quot;,
        rok_isporuke: &quot;2024-08-13&quot;
        status: &quot;KREIRANO&quot;
    }
]
Prilog 2. Artikli
[
    {
        id: 1
        naziv: &quot;Perilica posuđa ugradbena Electrolux EEA27200L&quot;,
        dobavljač: &quot;Sancta Domenica&quot;,
        količina: 1,
        status: &quot;KREIRANO&quot;
    },
    {
        id: 2
        naziv: &quot;Napa ugradbena Gorenje TH60E3X&quot;,
        količina: 1,
        dobavljač: &quot;Sancta Domenica&quot;,
        status: &quot;NARUČENO&quot;
    },
    {

        id: 3
        naziv: &quot;Ploča ugradbena kombinirana Gorenje GCE691BSC&quot;,
        količina: 1,
        dobavljač: &quot;Bijela tehnika&quot;,
        status: &quot;ISPORUČEO&quot;
    }
]
```

## Rješenje:

Projekt se pokreće sa pokretanjem datoteke docker-compose.yml

Nakon pokretanja, status servera se može provjeriti na:
>http://localhost:8080/actuator

## API - primjeri

### Pregled artikala

```http request
GET http://localhost:8080/artikl
```

### Pregled ugovora

```http request
GET http://localhost:8080/ugovor
```

### Kreiranje novog ugovora

```http request
POST http://localhost:8080/ugovor
Content-Type: application/json

{
  "kupac": "ime kupca",
  "datumAkontacije": "2023-10-16",
  "rokIsporuke": "2023-10-17",
  "ugovorArtikli": [
    {
      "kolicina": 1,
	  "artikl": {
        "id": 1
      }
	},
    {
      "kolicina": 2,
	  "artikl": {
        "id": 2
      }
	},
    {
      "kolicina": 3,
	  "artikl": {
        "id": 3
      }
	}
  ]
}
```

### Ažuriranje ugovora

```http request
PUT http://localhost:8080/ugovor/1
Content-Type: application/json

{
  "kupac": "ime kupca",
  "datumAkontacije": "2023-10-16",
  "rokIsporuke": "2023-10-17",
  "ugovorArtikli": [
	{
      "id": 2,
	  "kolicina": 3,
	  "artikl": {
		"id": 1
	  }
	},
	{
      "id": 3,
	  "kolicina": 2,
	  "artikl": {
		"id": 2
	  }
	},
	{
	  "kolicina": 2,
	  "artikl": {
		"id": 3
	  }
	}
  ]
}
```

### Potvrda statusa ugovora - prelazak u idući korak u procesu

```http request
POST http://localhost:8080/ugovor/1/confirm
```

### Brisanje ugovora (soft delete)

```http request
DELETE http://localhost:8080/ugovor/1
```

## Napomene

TODO:

Zadnji zadatak
>Potrebno je omogućiti autentifikaciju putem JWT tokena

je namjerno preskočen jer bi uvelike otežavao testiranje i demonstraciju aplikacije, a implementacija je poprilično jednostavna:

potrebno je samo:

1. dodati spring security dependency u pom.xml:
```xml
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```
2. konfigurirati ga u security configu:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/login", "/register")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.disable();
	}
}
```

3. napraviti utility za kreiranje i čitanje JWT tokena

4. nakon uspješnog logiranja preko username/password attachati token u HTTP header tako da idući zahtjev sadrži token
