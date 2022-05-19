# Statikus tesztek dokumentációja

## Elvégzett munka összefoglalása
> A SonarCloud segítségével az egész programkódunkat statikus analízis alá vetettük. Az analízis Maven segítségével, a Github Actions-ön keresztül zajlott. Először a Maven-t kellett beimplementálni a programba, ez után már futott az analízis, viszont a Unit tesztek az analízissel együtt történő futtatásához még programkódon belüli módosításra és komoly kézi tesztelésekre volt szükség. A Unit tesztek végül sikeresen lefogták a program egy részét (részletek a unit_tests.md-ben), az analízis segítségével pedig több apróbb hibát javítottunk.

## Eredmények

#### Képernyőkép az analízis futásának eredményéről: 
![image](https://user-images.githubusercontent.com/79572121/169370063-c8c82c07-dd9c-4bd0-844e-e6a30b8d65cc.png)

#### Képernyőkép a hibák javításának folyamata közben: 
![image](https://user-images.githubusercontent.com/79572121/169370225-ea9ee78e-e41a-4540-b084-0f16aa23c15c.png)

## Tanulságok
- Mint a képek is bizonyítják, felismertük, hogy csak azért, mert egy kód technikailag működik, korántsem jelenti azt, hogy jó minőségű is.
- Hosszas kutatómunka és próbálgatás utána megtanultuk, hogy hogyan kell Unit teszteket Maven-en segítségével SonarCloud-on futtatni. (pl.: verzió kompatibilitások összeegyeztetése)
- Megismerkedtünk a SonarCloud hibajavítást segítő funkcióival, amik nem csak a triviális, de valamivel komplexebb hibák megoldásában is nagy segítséget nyújtottak, mind a figyelemfelhívás, mind a javaslattétel (és ha szükség volt rá, magyarázás) terén.
