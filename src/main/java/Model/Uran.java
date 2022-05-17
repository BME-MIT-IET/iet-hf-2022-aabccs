package Model;

//A játékbeli uránt, mint nyersanyagot reprezentáló osztály
public class Uran extends Material {

    //----------Tulajdonságok----------\\

    private int numOfExp = 0;


    //-----------Függvények--------------\\


    /**
     * Ha az urán fedetlenné vált napközelben, felrobbantja a paraméterként kapott Model.Location-t, amiben van
     *
     * @param l amely Model.Location-ben található a nyersanyag
     */
    @Override
    public void Exposed(Location l){
    	if(l.isNearSun()){
            numOfExp++;
            if(numOfExp >= 3){
                l.Explode();
            }
        }
    }

    public void  SetCounter(int i){
        numOfExp = i;
    }

    public int GetCounter(){return numOfExp;}

    public String toString(){
        return "Uran";
    }

}
