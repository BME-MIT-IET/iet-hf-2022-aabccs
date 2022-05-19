# Unit tesztek dokumentációja

## Elvégzett munka összefoglalása
> Ezekkel a JUnit tesztekkel a Model-t teszteltük. Ketten dolgoztunk rajta, először megterveztük a teszteket, mind a specifikáció, mind pedig a kódfedettség alapján.
Ezután felosztottuk a 37 tesztet kettőnk között, önállóan dolgoztunk ezeken majd az eredményeket összefésültük gitHubon. A tesztek implementálása során 
a Model kódjában felfedezett nagyobb architekturális hibákat javítottuk.

## Eredmények

#### Ahogy az alábbi screenshot-on is látszódik, minden teszt sikeresen lefutott: 
![image](https://user-images.githubusercontent.com/79667132/169241742-2889d29c-9d4a-466e-b16c-f38bb00f30a6.png)


#### A kód lefedettség 37,9%-os, amely elég alacsony, hiszen a grafikus felületet ezek a tesztek nem tesztelik.
![image](https://user-images.githubusercontent.com/79667132/169238773-3f8bbba3-009d-471f-a65a-f0ae87e22b6a.png)

## Tanulságok
- Tesztelés közben arra jutottunk, hogy a forráskód készítői több architekturális és implementációs hibát vétettek,
 pl. a laza csatolás elve nem teljesül, ez megnehezítette a tesztelés 
folyamatát. 
- Körülményes volt a tesztkörnyezet előkészítése, ehhez lett volna praktikus ha @before tag-et használunk.
- A házi feladat ezen részének köszönhetően mélyebben megismerkedtünk a JUnit eszköztárával.
- Utólagos észrevételünk az, hogy több unit teszt összevonható lett volna, mert hasonló működést vizsgálnak.
