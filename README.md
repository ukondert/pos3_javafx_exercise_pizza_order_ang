# pos3_javafx_exercise_pizza_order
Bizza Bestellungsformular mit Speicherung der Daten in csv-Datei

## Angabe

🍕 Konfigurierbare Pizza-Bestellung

- Produkt: Pizza (mit BuilderPattern)

## Konfigurierbare Eigenschaften:

| Eigenschaft   | Optionen                         | Preisaufschlag   |
| ------------- | -------------------------------- | ---------------- |
| **Teig**      | Standard                         | +0 €             |
|               | Vollkorn                         | +1 €             |
|               | Glutenfrei                       | +2 €             |
| **Größe**     | Klein (Ø 24 cm)                  | Basispreis: 6 €  |
|               | Mittel (Ø 30 cm)                 | Basispreis: 8 €  |
|               | Groß (Ø 36 cm)                   | Basispreis: 10 € |
| **Käserand**  | Ja                               | +2 €             |
|               | Nein                             | +0 €             |
| **Beläge**    | Salami, Schinken, Speck, Ruccola, Mais, Zwiebel | +1 € pro Belag   |
| **Lieferung** | Abholung                         | +0 €             |
|               | Lieferung Standard (30 min)      | +2 €             |
|               | Lieferung Express (20 min)       | +4 €             |
|**Anmerkungen**| Lieferadresse | |

### Validierungen:

- Glutenfreier Teig nicht mit Käserand kombinierbar
- Maximal 3 Beläge bei klein/mittel, 4 Beläge bei groß erlaubt
- Lieferadresse nur bei Lieferung ausfüllbar 

### JavaFX UI (Verwendung von FXML):

- Combobox für Teig
- Checkboxes für Belge
- RadioButtons für Größe, Käserand und Lieferoption
- Lieferadresse TextArea

- Validierung durch:
  - disable von Checkboxen beim Erreichen der max. Anzahl an Zutaten (onAction #validateNumOfIngredients)
  - disable von Käserand bei Auswahl glutenfreier Teig (Eventhandler in initialize Methode)
  - disable Lieferadresse bei Auswahl Selbstabholung (Eventhandler in initialize Methode)

## Implementierungs Constraints

- Verwendung von englischen Bezeichnungen für Klassen, Eigenschaften und Enumerations (nicht Werte)
- Validierung der Eingabe
- Eigenschaften als Enum mit getDisplayValue, Price
- Initialisierung der GUI-Elemten in der initialize Methode des Controllers
- Pizza mit Builder Pattern (validate methode public), mit public Method für akutelle Anzahl dergewählten Zutaten
- CSVFileWriter (Delemiter ";" - statische Methode)
