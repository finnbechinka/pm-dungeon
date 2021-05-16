---
title:  'Lerntagebuch zur Bearbeitung von Blatt 5'
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


# Blatt 5 Quests, JUnit

<!--
Bitte hier die zu lösende Aufgabe kurz in eigenen Worten beschreiben.
-->

## Aufgabe 5.1: Quests 
* Verschiedene Quests implementieren, welche der Held erfüllen muss/kann
* Observer-Pattern benutzen
* Für die Erfüllung von Quests soll der Held Belohnungen erhalten
* Potenzielle Quests müssen dem Helden angezeigt werden z. B.: NPC
* Held kann angebotene Quests annehmen oder ablehnen
* Aktive Quests sollen im HUD angezeigt werden

## Aufgabe 5.2: JUnit
* Geeignete Testfälle für Quests mit JUnit implementieren

# Ansatz und Modellierung

<!--
Bitte hier den Lösungsansatz kurz beschreiben:
-   Wie sollte die Aufgabe gelöst werden?
-   Welche Techniken wollten Sie einsetzen?
-   Wie sah Ihre Modellierung aus (UML-Diagramm)?
-   Worauf müssen Sie konkret achten?
-->

## Aufgabe 5.1
Die Quests werden als ein Interface Quest implementiert welches dann von anderen Klassen implementiert werden z. B.: von einer Klasse, also Unterklasse von Character, welche einen NPC darstellt.   
Der Held bekommt dann eine Liste von Quests welche dann wie im Observer-Pattern beschrieben nach potenziell relevanten Vorkommnissen z. B.: Ein Monster stirbt informiert werden.   
Daher stellt hier der Held das Observable dar und die Quests die Observer.  

## Aufgabe 5.2
Sinnvolle Testfälle für die erstellten Quests ausdenken und als mit JUnit testen.  

# Umsetzung

<!--
Bitte hier die Umsetzung der Lösung kurz beschreiben:
-   Was haben Sie gemacht,
-   an welchem Datum haben sie es gemacht,
-   wie lange hat es gedauert,
-   was war das Ergebnis?
-->

## 16.05.2021
* Modellierung
* Aufgabe 5.1
  * Quests Implementiert
  * Der Held bekommt Belohnungen fürs erfüllen von Quests
  * Quests werden dem Spieler durch das Interagieren mit einem NPC vorgeschlagen und können angenommen/abgelehnt werden
  * Der Held hat eine listen von aktiven Quests
* Aufgabe 5.2
  * JUnit Tests implementiert

# Postmortem

<!--
Bitte blicken Sie auf die Aufgabe, Ihren Lösungsansatz und die Umsetzung
kritisch zurück:
-   Was hat funktioniert, was nicht? Würden Sie noch einmal so vorgehen?
-   Welche Probleme sind bei der Umsetzung Ihres Lösungsansatzes aufgetreten?
-   Wie haben Sie die Probleme letztlich gelöst?
-->

Da wir aus persönlichen Gründen erst am Sonntag mit diesem Aufgabenblatt angefangen haben, hatten wir nicht mehr genug Zeit gehabt um den NPC Interaktion Dialog, das Annehmen bzw. Ablehnen von Quests und aktive Quests auf dem HUD auszugeben.  
Vor allem, weil wir logischerweise das meiste davon als Text auf dem HUD ausgeben müssten aber das im MainController bereitgestellte textHud vom Typ TextStage nicht mehrere Strings in Form von mehreren Labels auf dem Bildschirm ausgeben kann (was es eigentlich laut Dokumentation können sollte) und uns daher einen Workaround ausdenken hätten müssten z. B.: Den ganzen Text in vorm von PNG's auf dem HUD auszugeben was aber zu lange gedauert hätte.  
Daher läuft bis jetzt noch alles über Konsolenausgaben und wir müssen die HUD ausgaben später machen.