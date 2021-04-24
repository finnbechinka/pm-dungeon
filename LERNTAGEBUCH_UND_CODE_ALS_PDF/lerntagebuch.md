---
title:  'Lerntagebuch zur Bearbeitung von Blatt 1'
author:
- Finn Bechinka (finn.bechinka@fh-bielefeld.de)
- Michel Witt (michel-andre.witt@fh-bielefeld.de)
...

<!--
Führen Sie zu jedem Aufgabenblatt und zum Projekt (Stationen 3-9) ein
Lerntagebuch in Ihrem Team. Kopieren Sie dazu diese Vorlage und füllen
Sie den Kopf entsprechend aus.

Im Lerntagebuch sollen Sie Ihr Vorgehen bei der Bearbeitung des jeweiligen
Aufgabenblattes vom ersten Schritt bis zur Abgabe der Lösung dokumentieren,
d.h. wie sind Sie die gestellte Aufgabe angegangen (und warum), was war
Ihr Plan und auf welche Probleme sind Sie bei der Umsetzung gestoßen und
wie haben Sie diese Probleme gelöst. Beachten Sie die vorgegebene Struktur.
Für jede Abgabe sollte ungefähr eine DIN-A4-Seite Text erstellt werden,
d.h. ca. 400 Wörter umfassen. Wer das Lerntagebuch nur ungenügend führt
oder es gar nicht mit abgibt, bekommt für die betreffende Abgabe 0 Punkte.

Checken Sie das Lerntagebuch mit in Ihr Projekt/Git-Repo ein.

Schreiben Sie den Text mit [Markdown](https://pandoc.org/MANUAL.html#pandocs-markdown).

Geben Sie das Lerntagebuch stets mit ab. Achtung: Wenn Sie Abbildungen
einbetten (etwa UML-Diagramme), denken Sie daran, diese auch abzugeben!

Beachten Sie auch die Hinweise im [Orga "Bewertung der Aufgaben"](pm_orga.html#punkte)
sowie [Praktikumsblatt "Lerntagebuch"](pm_praktikum.html#lerntagebuch).
-->


# Aufgabe

<!--
Bitte hier die zu lösende Aufgabe kurz in eigenen Worten beschreiben.
-->

Für die Aufgabe 1.1 sollen wir die bereitgestellten Dateien (Asset-Ordner und Jar-Library) in unser Projekt einbinden und die .gitignore Datei erstellen.  
Dann sollten wir uns die bereitgestellte Dokumentation durchlesen und uns mit dieser vertraut machen.  
Außerdem sollen wir uns benötigte Texturen heraussuchen.

Für die Aufgabe 1.2 sollen wir wie in der Dokumentation beschrieben die MainController Klasse und unsere Helden Klasse implementieren.  
Weitergehend sollen wir sowohl eine Idle-Animation als auch zwei lauf Animationen (nach rechts und links) erstellen und sicherstellen, dass diese wie und wann gewollt abgespielt werden.


# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

* Aufgabe durchlesen und verstehen
* Dokumentation durchlesen und verstehen
* Assets und jar-Library zum Projekt hinzufügen
* Texturen für den Helden heraussuchen/erstellen
* Klassen mit Hilfe der Dokumentation implementieren
* Idle-Animation erstellen
* Lauf-Animationen erstellen und sicherstellen das alle Animationen zum gewollten Zeitpunkt abgespielt werden


# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

## 17.04.2021:
Heute haben wir die MainController und die Helden Klasse wie in der Dokumentation beschrieben implementiert und für die Idle-Animation eigene Texturen erstellt.  
Am Anfang hatten wir Probleme damit die bereitgestellte jar-Datei als library einzubinden da wir so etwas noch nie gemacht haben aber, nachdem wir das geschafft haben gab es keine weiteren großartigen Probleme.  
Wir haben die Implementierung der idle-Animation und das bewegen des Charakters abgeschlossen und müssen jetzt noch die weiteren Animationen für die Laufrichtungen erstellen.

## 18.04.2021:
Heute haben wir ohne weitere Probleme die beiden Animationen fürs nach rechts und links bewegen erstellt und sichergestellt, dass die Animationen je nach Bewegung bzw. stillstand (idle-Animation) wechseln.


# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Bis auf das obige beschriebene Problem die library einzubinden, da wir nicht wusste wie, gab es sonst keine Probleme daher sind wir im Großen und Ganzen zufrieden mit unserer Bearbeitung dieses Aufgabenblattes.