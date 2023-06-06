# Game master
Dieses Projekt beinhaltet den gesamten Code des IPv12 - Energieprojekt Game Master Bildschirms.

## Wie brauche ich dieses Repository.

### Verwendung

1. Eine Java und JavaFX lauffähige Maschine bereitstellen inklusiv USB Port und PINS wo man Buttons anhängen kann.<br>
Weiteres unter dem Kapitel "Spielerbuttons"<br><br>
2. Im Kapitel Spielerbuttons muss man folgen wenn man Hardware Buttons einsetzen will. Sonst wird der User-Input  jeweils über UI Buttons ersetzt.<br><br>
3. Nun muss man den Anemometer (Das Windmessgerät) neu Konfigurieren um auf der aktuellen Hardware problemfrei zu laufen. Genaue Spezifikation under dem Kapitel "Anemometer".
4. Nun sollte die App bereit sein um normal via SuperApp.java#main mit Java zu starten.

### Spielerbuttons

1. Folgende Linie in App.java muss auskommentiert werden um nicht den Button-Emulator laufen zu lassen sondern tatsächlich die Inputs von den Buttons zu lesen. Die Linien mit dem EmulatorView können auch entfernt werden. 
```
App.java#29
//PUI pui = new PUI(controller, Pi4J.newContext());

App.java#43-48
Scene emulatorScene = new Scene(new EmulatorView(controller));
Stage secondaryStage = new Stage();
secondaryStage.setTitle("PUI Emulator");
secondaryStage.setScene(emulatorScene);
secondaryStage.show();
```

Man kann das Spiel auch mit dem EmulatorView spielen und einfach starten. Dann werden aber keine Inputs über die PINS gelesen.<br><br><br>

2. Die Buttons die man auswählt werden über PIN angeschlossen. Die Input PINS werden in folgender Datei angepasst. 

```
PUI.java#48
// Diese Linie 4x hinzufügen damit es für jeden Spieler einen Button hat.
playerButtons.add(new SimpleButton(pi4J, PIN.D22, false));
    
```

Folgende Linie in App.java muss auskommentiert werden um nicht den Button-Emulator laufen zu lassen sondern tatsächlich die Inputs von den Buttons zu lesen. Die Linien mit dem EmulatorView können auch entfernt werden.
```
App.java#29
//PUI pui = new PUI(controller, Pi4J.newContext());

App.java#43-48
Scene emulatorScene = new Scene(new EmulatorView(controller));
Stage secondaryStage = new Stage();
secondaryStage.setTitle("PUI Emulator");
secondaryStage.setScene(emulatorScene);
secondaryStage.show();
```

3. Jetzt muss sichergestellt sein, dass die Buttons korrekt an die Hardware mit PIN-Anschluss die Buttons korrekt angeschlossen sind. Dokumentation über die Verkabelung mit den Buttons ist je nach Hardware ein wenig unterschiedlich.

### Anemometer

1. Ein Anemometer muss vorhanden sein mit Modbus-Kommunikation.<br>
Getestetes Modell: https://www.pi-shop.ch/rs485-wind-speed-transmitter<br>
Doku des Modells: https://wiki.dfrobot.com/RS485_Wind_Speed_Transmitter_SKU_SEN0483<br>
Mit PIN zu USB-Adapter: https://www.dfrobot.com/product-2189.html

2. Nun muss der USB-Port und die Modbus Konfiguration so angepasst werden um für das ausgewählte Modell zu funktionieren. Dies wird in der Datei Anemometer.java gemacht.

```
Anemometer.java#15
params.setPortName("cu.usbserial-1220");

Anemometer.java#29
Register[] slaveResponse = master.readMultipleRegisters(2, 0, 1);
```

3. Falls es noch nicht so funktioniert wenn man den USB-Port angegeben hat und die zu lesenden Register angepasst hat, kann es sein das man weitere Hardware spezifische Modbus Konfiguration hinzufügen muss.

## Für Developers.

### Vorbereitung

1. Als erstes soll in IntelliJ die XML-Datei für die Coding Conventions importiert werden. Man findet diese unter: <br>
https://www.cs.technik.fhnw.ch/confluence20/display/VT122209/Code-Konvention

### Arbeitsweise im Git
1. Für eine neue Erweiterung wird ein Gitlab Issue erstellt: https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_energieversorgung/game-master/-/issues

2. Sobald man an diesem Issue arbeitet wird einen dazugehörigen Merge-Request/Branch erstellt mithilfe des drückens des blauen Knopfes. Angaben muss man soweit nicht anpassen.
Beispiel: https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_energieversorgung/game-master/-/issues/1<br>
Result: https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_energieversorgung/game-master/-/merge_requests/1

3. Merge Request bedeutet: Eine abgezweigte Version des Codes wird versucht wieder in den originale Version hineingebracht zu werden.<br>
Wenn man an dem Issue arbeitet kann man das machen ohne das jemand anderst in die Queere kommt. 
Für das muss man in den neu erstellten Branch (wird erstellt bei Merge Request) gehen: https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_energieversorgung/game-master/-/tree/1-beispiel-issue<br>
Direkt sollte man die verschiedenen Anforderungen in der Merge Request angeben, welche geändert/hinzugefügt werden, damit auf diese speziellen Wert gelegt werden kann.<br>
https://www.cs.technik.fhnw.ch/confluence20/display/VT122209/Requirements

4. Lokal muss man auch in diesen Branch gehen, dann dort arbeiten und dort commiten.

5. Sobald man das Issue implementiert hat geht man wieder auf die Merge Request und nimmt den "Draft:" prefix im Titel weg. Danach kann diese Merge Request reviewed werden durch eine andere Person und gemerged werden.


## Für Primeo

### Doku und Anleitung:
Die gesamte Doku ist abgelegt unter: [dem Ordner docs](./docs)

### Was ist noch nicht gemacht bzw. Funktioniert nicht:
- Gamemaster.java#selectMiniGame:<br>
Der Anemometer ist grundsätzlich komplett implementiert aber hier noch auskommentiert. Der Grund dafür ist, dass das UI bzw Anemominigameview.fxml noch nicht sauber genug ist. Wird versucht in den nächsten Tagen noch fertig zu machen.

- Translation support:<br>
Translation support ist noch nicht gemacht aber kann via javafx bundles erstellt werden. (https://stackoverflow.com/questions/60377780/javafx-internationalization-without-fxml)

- Memory issues:<br>
Aktuell stocken die GIF und die gesamte App nach einer Weile. Unsere These ist, dass das GameMasterView#setupModelToUiBindings unendlich instanzen kreiert und sie nicht wieder aufräumt. Da das ganze Asynchron ist, werden viele Threads erstellt und womöglich nicht mehr zeitgerecht geschlossen was die Memory volllaufen lässt. Wird versucht in den nächsten Tagen noch zu beheben, da es sonst das Spiel unspielbar macht.







