Tesztelés
=========

Szintek (elmélet)
-----------------
### Egyég teszt (Unit test)
Az egység a rendszer vagy az alkalmazás legkisebb tesztelhető része, amelyet össze lehet állítani, tetszeni lehet, 
betölteni és futtatni. Ez a fajta tesztelés segít az egyes modulok külön-külön történő tesztelésében.
A cél a szoftver minden részének tesztelése külön-külön. Ez ellenőrzi, hogy az adott elem/egység megfelel-e a funkcióknak, vagy sem.

**Ezt a fajta tesztelést a fejlesztők végzik.**

Például egy metódus egy Integer paramétert vár, ami 1 és 10 közötti számok lehetnek. Mi történik, ha:
  - Paraméternek **1**-et kap?
  - **10**-est kap?
  - **2-9** között valami mást?
  - **null**-t?
  - **11**-t?
  - **-1**-et? 
  - Maximális integer számot? (Ugyanúgy működik mint a 11?)
  - Minimális integer számot? (Ugyanúgy működik mint a -1?)

## Integrációs tesztelés (Integration test):
Két szoftver komponens integrációja avagy kombinácija funkció beli problémát generálhat jelenthet, de mindkettő Unit tesztje
probléma nélkül lefut.
[Unit vs Integration](https://www.youtube.com/watch?v=0GypdsJulKE)

Az integrált tesztelés ellenőrzi az adatáramlást az egyik modulról a másikra.

**Ezt a fajta tesztelést a tesztelők végzik, de fejlesztők is végezhetik, ha két külön definiált komponenst köznek össze.**

## Rendszer teszt (System test) 
A rendszer tesztelése teljes, integrált rendszeren történik. Lehetővé teszi a rendszer megfelelőségének ellenőrzését 
a követelményeknek megfelelően. Teszteli az alkatrészek teljes kölcsönhatását. Ez magában foglalja a terhelés, a 
teljesítmény, a megbízhatóság és a biztonság tesztelését.

A rendszer tesztelése leggyakrabban az utolsó teszt annak ellenőrzésére, hogy a rendszer megfelel-e a specifikációnak.
Kiértékeli mind a funkcionális, mind a nem funkcionális tesztigényt.

**Technikailag:** Az ügyfél által biztosított környezetnek megfelelő hardver és szoftver környezetet hozunk létre
ahol megvizsgálásra kerül a program avagy termék viselkedése a fenti specifikációnak.

**Nem a fejlesztők végzik első sorban!**

## Elfogadási teszt:
Az elfogadási teszt egy teszt, amelyet annak megállapítására használnak, hogy a specifikáció vagy a szerződés
követelményei teljesülnek-e a szállítás során. Az elfogadási tesztet alapvetően a felhasználó vagy az ügyfél végzi.
Más részvényesek azonban bevonhatók ebbe a folyamatba.

