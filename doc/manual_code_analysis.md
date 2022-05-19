# Manuális kód átvizsgálás dokumentációja

## Elvégzett munka összefoglalása
> A GItHub webes felületén a pull requestbe bekerülő commitokat átvizsgáltuk. Az ellenőrzéseket a statikus analízis alapján javított kódrészeken végeztük el, releváns változásokhoz véleményező kommenteket fűztünk. 

## Eredmények

#### Képernyőkép két kommentezett kódrészletről: 
![image](https://user-images.githubusercontent.com/42514054/169379568-2b82810d-52c0-4c38-b88d-689d3d31ee27.png)


## Tanulságok
- Sok hibára fény derülhet csupán azáltal, hogy a kódot végig nézzük, mert könnyebben észrevehetőek logikai bukfencek (pl logikátlan függvény-szekvenciák), a konvenciók követésének hiányosságai (pl setter függvények megjelennek kis és nagy kezdőbetűvel is), illetve egyszerűsítési lehetőségek (pl kódblokkok függvénybe szervezése)
- A változtatásokat jó, ha egy külön ember átnézi, ugyanis a javításokat látva felmerülhetnek benne olyan értelmező kérdések, melyekre a commit-oló esetleg nem is gondolt. Ezáltal esetleges hibák kiszürhetők, illetve jobb megoldásokra juthatunk.
- A GitHub platform nagyban megkönnyíti az egyértelmű kommunikációt az intuitív felhasználói felületével, érdemes ezt vagy más hasonló rendszert alkalmazni ilyen feladatokra.
