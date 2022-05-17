package Model;

//A játékbeli vízjeget, mint nyersanyagot reprezentáló osztály
public class Water extends Material {


    /**
     * Ha a vízjég fedetlenné vált napközelben, a víz elszublimál, azaz eltûnik
     *
     * @param l amely Model.Location-ben található a nyersanyag
     */
    @Override
    public void Exposed(Location l){
    	if(l.isNearSun()) {
        	l.RemoveMaterial();
        }
    }

    public String toString(){
        return "Water";
    }

}
