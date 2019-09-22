package juego;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Tablero implements Serializable {

	private static final long serialVersionUID = 1L;
	private int nivel;
    private int x;
    private int y;
    private int minas;
    private int descubiertas;
    private int marcadas;
    private int estadoJuego;
    int[][] tablero;
    boolean[][] visibles;
    boolean[][] marcas;
    private static Random random = new Random(new Date().getTime());
    
    public Tablero(int nivel) {
        descubiertas = 0;
        switch (nivel) {
            case 0:
                minas = 4;
                x = 3;
                y = 4;
                break;
            case 1:
                minas = 10;
                x = 9;
                y = 9;
                break;
            case 2:
                minas = 40;
                x = 16;
                y = 16;
                break;
            case 3:
                minas = 99;
                x = 16;
                y = 30;
                break;
        }
        estadoJuego = 0;
        tablero = new int[x][y];
        visibles = new boolean[x][y];
        marcas = new boolean[x][y];
        rellenaMinas();
        procesaTablero();
    }
    
    private void rellenaMinas() {
        int m = minas;
        int xx, yy;
        while (m != 0) {
            do {
                xx = (int) (random.nextDouble() * this.x);
                yy = (int) (random.nextDouble() * this.y);
            } while (tablero[xx][yy] == -1);
            tablero[xx][yy] = -1;
            m--;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (char i = 0; i < getY(); i++) {
            sb.append(String.format(" %2d ", i + 1));
        }
        sb.append("\n");
        
        for (int i = 0; i < getX(); i++) {
            sb.append(String.format(" %2d ", i + 1));
            for (int j = 0; j < getY(); j++) {
                String val;
                if (marcas[i][j]) {
                    val = "  X ";
                } else {
                    if (visibles[i][j]) {
                        val = String.format(" %2d ", tablero[i][j]);
                    } else {
                        val = "  - ";
                    }
                }
                sb.append(val);
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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

    public int getMinas() {
        return minas;
    }

    public void setMinas(int minas) {
        this.minas = minas;
    }

    public int getDescubiertas() {
        return descubiertas;
    }

    public void setDescubiertas(int descubiertas) {
        this.descubiertas = descubiertas;
    }

    public int getEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(int estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    
    public void ponMarca(int x, int y) {
        hazJugada(x, y, 1);
    }
    
    public void destapa(int i, int j) {
        if (tablero[i][j] == -1 || visibles[i][j]) {
            return;
        }
        visibles[i][j] = true;
        descubiertas++;
        if (tablero[i][j] > 0) {
            return;
        }
        if (i > 0) {
            destapa(i - 1, j);
        }
        if (j > 0) {
            destapa(i, j - 1);
        }
        if (j != y - 1) {
            destapa(i, j + 1);
        }
        if (i != x - 1) {
            destapa(i + 1, j);
        }
        if (i > 0 && j > 0) {
            destapa(i - 1, j - 1);
        }
        if (j != y - 1 && i != x - 1) {
            destapa(i + 1, j + 1);
        }
        if (j != y - 1 && i > 0) {
            destapa(i - 1, j + 1);
        }
        if (i != x - 1 && j > 0) {
            destapa(i + 1, j - 1);
        }
    }
    
    private void procesaTablero() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //int n = 0;
                if (tablero[i][j] >= 0) {
                    if (i > 0) {
                        tablero[i][j] += tablero[i - 1][j] == -1 ? 1 : 0;
                    }
                    if (j > 0) {
                        tablero[i][j] += tablero[i][j - 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1) {
                        tablero[i][j] += tablero[i][j + 1] == -1 ? 1 : 0;
                    }
                    if (i != x - 1) {
                        tablero[i][j] += tablero[i + 1][j] == -1 ? 1 : 0;
                    }
                    if (i > 0 && j > 0) {
                        tablero[i][j] += tablero[i - 1][j - 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1 && i != x - 1) {
                        tablero[i][j] += tablero[i + 1][j + 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1 && i > 0) {
                        tablero[i][j] += tablero[i - 1][j + 1] == -1 ? 1 : 0;
                    }
                    if (i != x - 1 && j > 0) {
                        tablero[i][j] += tablero[i + 1][j - 1] == -1 ? 1 : 0;
                    }
                }
            }
        }
    }
    
    public void hazJugada(int x, int y, int t) {
        if (t == 0) // Tiro
        {
            if (tablero[x][y] == -1) {
                for (int i = 0; i < x; i++) {
                    Arrays.fill(visibles[i], true);
                }
                estadoJuego = -1;
            } else {
                destapa(x, y);
                if (minas + descubiertas + marcadas == x * y) {
                    for (int i = 0; i < x; i++) {
                        Arrays.fill(visibles[i], true);
                    }
                    estadoJuego = 1;
                }
            }
        } else {
            marcas[x][y] = !marcas[x][y];
            if (tablero[x][y] == -1) {
                minas += marcas[x][y] ? -1 : 1;
                marcadas += marcas[x][y] ? 1 : -1;
            }
            if (minas == 0) {
                estadoJuego = 1;
            }
        }
    }
}