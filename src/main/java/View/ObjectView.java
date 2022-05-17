package View;

import javax.swing.*;
import java.awt.*;

public abstract class ObjectView {

    public abstract void Draw(Point cp, Graphics2D g);

    protected int size = 100;

    public abstract Point getCoordinate();

    public int getSize(){
        return size;
    }


}
