package Model;

import java.util.ArrayList;

//A játékbeli építéshez szükséges recepteket reprezentáló osztály
public class Recipe {

    //Tárolt objektumok
    private ArrayList<Material> Ingredients;

    //Konstruktorok

    /**
     * Konstruktor
     */
    public Recipe(){
        Ingredients = new ArrayList<Material>();
    }
    
    //--------------Függvények----------------------

    /**
     * Megnézi, hogy a paraméterként kapott Model.Material szükséges-e a recepthez
     *
     * @param m errõl dönti el, hogy szükséges-e a recepthez
     */
    public void isNeeded(Material m){
        for (int i = 0; i < Ingredients.size(); i++){
            if(Ingredients.get(i).MaterialType(m)){
                Ingredients.remove(i);
                return;
            }
        }
    }

    /**
     * Ha megvan minden szükséges nyersanyag, elveszi azokat,
     * és frissíti a telepes eszköztárát
     *
     * @param inventory a telepes inventory-ja
     * @return a frissített inventory
     */
    public ArrayList<Material> UpdateInventory(ArrayList<Material> inventory){
        ArrayList<Material> tmp = new ArrayList<>();
        tmp.addAll(Ingredients);
        //Possible place of shit happening with indexes
        for(int i = 0; i < inventory.size(); i++){
            int iOffset = 0;
            for(int j = 0; j < tmp.size(); j++) {
                if(inventory.get(i).MaterialType(tmp.get(j))){
                    inventory.remove(i);
                    tmp.remove(j);
                    iOffset = 1;
                    j--;
                }
            }
            i -= iOffset;
        }
        return inventory;
    }

    /**
     * Ellenõrzi, hogy megvan-e minden szükséges nyersanyag a recepthez
     *
     * @param inventory a telepes inventory-ja
     * @return az ellenõrzés eredménye
     */
    public boolean HasEverything(ArrayList<Material> inventory){
        ArrayList<Material> initial = new ArrayList<>();
        initial.addAll(Ingredients);

        for(int i = 0; i < inventory.size(); i++){
            isNeeded(inventory.get(i));
        }

        boolean success = Ingredients.size() == 0;
        Ingredients = initial;
        return success;
    }

    /**
     * Beállítja a robothoz szükséges nyersanyagokat szükségesnek
     */
    public void SetToRobot(){
        Ingredients.add(new Iron());
        Ingredients.add(new Coal());
        Ingredients.add(new Uran());
    }

    /**
     * Beállítja a teleportkapuhoz szükséges nyersanyagokat szükségesnek
     */
    public void SetToTeleportGate(){
        Ingredients.add(new Iron());
        Ingredients.add(new Iron());
        Ingredients.add(new Water());
        Ingredients.add(new Uran());
    }

    /**
     * Beállítja a nyeréshez szükséges nyersanyagokat szükségesnek
     */
    public void SetToWin(){
        Ingredients.add(new Coal());
        Ingredients.add(new Iron());
        Ingredients.add(new Uran());
        Ingredients.add(new Water());
    }

}
