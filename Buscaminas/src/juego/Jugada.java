package juego;

import java.io.Serializable;

public class Jugada implements Serializable{
 
	private static final long serialVersionUID = 1L;
	private int tipo;
    private int x;
    private int y;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}