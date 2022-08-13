# Mein Moers (Android)

Mein Moers für Android ist die App für die Stadt Moers.
Alle Informationen stammen aus Open Data Angeboten der Stadt Moers und anderen Organisationen.

## Allgemeine Informationen

### Warum native Apps?


### Konzept


## Technische Umsetzung

### Aufbau der App

Die App ist modular aufgebaut und in verschiedene Feature-Module aufgeteilt.

- `App`: Die App kombiniert alle
- `Core`: Im Core-Modul befinden sich häufig verwendete UI-Elemente und Funktionen, die in allen
  anderen Modulen verwendet werden. Alle anderen Module hängen von diesem Modul ab.
- `EventsFeature`: 
- `FuelFeature`:
- `NewsFeature`:
- `RubbishFeature`:
- `ParkingFeature`: Alles rund um Parkplätze…

> Alle Feature-Module werden in Zukunft auch in eigenen nativen Apps verwendet werden können. 

### Tech-Stack

Die App wird als native und moderne Android-App entwickelt.
Verwendet wird die Programmiersprache Kotlin, allerdings ist auch teilweise die Verwendung von Java möglich.

Als UI Framework wird soweit möglich [Jetpack Compose](https://) verwendet.

Als Entwicklungspattern wird an den meisten Stellen das Repository-Pattern verwendet:
`Services` laden Informationen von Servern, die dann an Repository weitergeben. Das Repository ist dafür zuständig, diese Daten dann über ein `DAO` (Data-Access Object) in eine lokale Datenbank auf dem Gerät abgespeichert.
Auf diese Weise werden Daten zwischengespeichert, sodass diese auch offline verwendet werden können. 

## Zukünftige Entwicklung

Das Ziel ist es, die 

### Links zu anderen Repositories

- [Mein Moers (moers.app)](https://moers.app)
- [Mein Moers (iOS)](https://github.com/lambdadigamma/moers-ios)
- [Mein Moers (Android)](https://github.com/lambdadigamma/moers-android)