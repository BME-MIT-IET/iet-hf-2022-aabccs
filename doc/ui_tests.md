# UI tesztek dokumentációja

## Elvégzett munka összefoglalása
> A UI tesztek végrehajtásához az eddig is használt JUnit könyvtár mellett a java.awt csomagban található Robot osztályt használtuk. A tesztek megírását elsősorban a forráskód segítette. A menüt teljes egészében, azonban a játék kezelőfelületét csupán részben fedik le a tesztesetek. A fennmaradó elemeket (a GameLoop megvalósítása miatt) többszálú környezetben lehet csak tesztelni. 


## Eredmények

#### Ahogy az alábbi screenshot-on is látszódik, minden teszt sikeresen lefutott, a hosszú futási idő a manuálisan beleírt várakoztatások miatt tapasztalható (robot.delay): 

![ui_tests_success](https://user-images.githubusercontent.com/79750064/169383081-1b6da611-e17e-4e71-b813-c75f58b563af.PNG)

Fontos azonban megjegyezni, hogy a Robot osztály csak akkor használható, amennyiben a futtatókörnyezet támogatja az ún. low-level input kezelést, illetve a GraphicEnvironment.isHeadless() függvény viszatérési értéke true. Ellenkező esetben AWT Exception keletkezik. Sajnálatos módon a Github Actionsben történő futtatáskor ezutóbbi eset áll fenn, így a build-et sikertelennek jelzi, mert a tesztek futtatása sikertelen.

## Tanulságok
- A kezelőfelület alapvetően használható, jól kezelhető.
- A játék felépítése nem támogatja az egyszerű tesztelhetőséget (a GameLoop függvény megvalósítása miatt), speciális módszerek igénybevétele lehet szükséges.
