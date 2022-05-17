package Model;

//Egy absztrakt osztály, mely egy nyersanyagot reprezentál
public abstract class Material {

	//--------------Függvények----------------------

    /**
     * Konstruktor
     */
	public Material() {
	}

    /**
     * A nyersanyag fedetlenné válását reprezentáló függvény
     *
     * @param l amely Model.Location-ben található a nyersanyag
     */
    public void Exposed(Location l){
    }

    /**
     * A nyersanyag l Model.Location-re lehelyezését reprezentáló függvény
     *
     * @param l Model.Location, melybe helyezik a nyersanyagot
     * @return a művelet sikerességét jelzi
     */
    public boolean Place(Location l){
        return l.Place(this);
    }

    /**
     * Absztrakt függvény, mely elenőrzi, hogy a paraméterként kapott
     * m Model.Material megegyezik-e a az aktuális Model.Material-al
     *
     * @param m megnézi megegyezik-e az adott Materiallal
     * @return a művelet sikerességét jelzi
     */
    public boolean MaterialType(Material m){
        return m.getClass() == this.getClass();
    }



}
