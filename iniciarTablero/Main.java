import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    static int[][] tablero;
    static ArrayList<Integer> posiciones = new ArrayList<Integer>();
    public static void main(String[] args) {
        /*Scanner keyboard = new Scanner(System.in);
        System.out.println("Dimension");
        int dimension = keyboard.nextInt();

        int[] barcos = new int[dimension / 2];

        for (int i = 0; i < dimension / 2; i++) { // n/2
            barcos[i] = keyboard.nextInt();
        }
        keyboard.close();*/
        // -------------------------------------------------------------------------------
        for(int p = 0; p<10; p++){
            int dimension = 10;
            int[] barcos={4,4,5,3,2};

            tablero = new int[dimension][dimension];

            for(int i =0; i<dimension;i++)
                for(int j =0; j<dimension;j++)
                    tablero[i][j]=0;

            for(int i = 0; i<dimension*dimension;i++)   
                posiciones.add(i);

            Collections.shuffle(posiciones);

            for(int i=0;i<barcos.length;i++){
                boolean validado = false;
                int random = 0;
                while(validado == false){
                    int indx = posiciones.get(random);
                    if(validado==false){
                        validado = diagonalDer(i+1, indx, barcos[i], dimension);
                    }
                    if(validado==false){
                        validado = diagonalIzq(i+1, indx, barcos[i], dimension);
                    }
                    if(validado==false){
                        validado = horizontal(i+1, indx, barcos[i], dimension);
                    }
                    if(validado==false){
                        validado = vertical(i+1, indx, barcos[i], dimension);
                    }

                    random++;
                } 
            }

            for(int i =0; i<dimension;i++){
                for(int j =0; j<dimension;j++)
                    System.out.print(tablero[i][j]+"\t");
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    public static boolean horizontal(int nB, int indx, int tam, int dimension) {
        boolean validado = true;
        for (int i =0;i<tam;i++)
        {
            if(posiciones.indexOf(indx+i)==-1 ||  (indx+i)/dimension != indx/dimension || !validarEntorno(indx+i, dimension)){
                validado=false;
            }
                
        }
        for (int i=0;i<tam && validado==true;i++) {
            tablero[(indx+i)/dimension][(indx+i)%dimension] = nB;
            deshabilitar((indx + i), dimension);
        }
        return validado;
    }

    public static boolean vertical(int nB, int indx, int tam, int dimension){
        boolean validado = true;
        for(int i=0; i<tam; i++){
            if(posiciones.indexOf(indx+(i*dimension))==-1 || indx%dimension != (indx+(i*dimension))%dimension || !validarEntorno(indx+(i*dimension), dimension))
                validado=false;
        }
        for(int i=0; i<tam && validado==true; i++){
            tablero[(indx+(i*dimension))/dimension][(indx+(i*dimension))%dimension]=nB;
            deshabilitar(indx+(i*dimension), dimension);
        }
        return validado;
    }

    public static boolean diagonalIzq(int nB, int indx, int tam, int dimension){
        boolean validado = true;
        for(int i=0; i<tam; i++){
            if(posiciones.indexOf(indx+(i*dimension)-i)==-1 || indx%dimension < (indx+(i*dimension)-i)%dimension ||  !validarEntorno((indx+(i*dimension)-i), dimension))
                validado=false;
        }
        for(int i=0; i<tam && validado==true; i++){
            tablero[(indx+(i*dimension)-i)/dimension][(indx+(i*dimension)-i)%dimension]=nB;
            deshabilitar(indx+(i*dimension)-i, dimension);
        }
        return validado;
    }

    public static boolean diagonalDer(int nB, int indx, int tam, int dimension){
        boolean validado = true;
        for(int i=0; i<tam; i++){
            if(posiciones.indexOf(indx+(i*dimension)+i)==-1 || indx%dimension > (indx+(i*dimension)+i)%dimension ||  !validarEntorno(indx+(i*dimension)+i, dimension))
                validado=false;
        }
        for(int i=0; i<tam && validado==true; i++){
            tablero[(indx+(i*dimension)+i)/dimension][(indx+(i*dimension)+i)%dimension]=nB;
            deshabilitar(indx+(i*dimension)+i, dimension);
        }
        return validado;
    }

    public static boolean validarEntorno(int indx, int dimension){
        int row = indx/dimension;
        int col = indx%dimension;
        if (col+1<dimension&&tablero[row][col + 1] != 0) {
            return false;
        } else if (row+1<dimension&&col+1<dimension&&tablero[row + 1][col + 1] != 0) {
            return false;
        } else if (row+1<dimension&&tablero[row + 1][col] != 0) {
            return false;
        } else if (row+1<dimension&&col-1>0&&tablero[row + 1][col - 1] != 0) {
            return false;
        } else if (col-1>0&&tablero[row][col - 1] != 0) {
            return false;
        }else if (row-1>0&&col-1>0&&tablero[row - 1][col - 1] != 0) {
            return false;
        } else if (row-1>0&&tablero[row - 1][col] != 0) {
            return false;
        } else if (row-1>0&&col+1<dimension&&tablero[row - 1][col + 1] != 0) {
            return false;
        }
        return true;
    }

    public static void deshabilitar(int indx, int dimension){
        if(posiciones.indexOf(indx)!=-1)
            posiciones.remove(posiciones.indexOf(indx));
        if(posiciones.indexOf(indx+1)!=-1)
            posiciones.remove(posiciones.indexOf(indx+1));
        if(posiciones.indexOf(indx+1+dimension)!=-1)
            posiciones.remove(posiciones.indexOf(indx+1+dimension));
        if(posiciones.indexOf(indx+dimension)!=-1)
            posiciones.remove(posiciones.indexOf(indx+dimension));
        if(posiciones.indexOf(indx+dimension-1)!=-1)
            posiciones.remove(posiciones.indexOf(indx+dimension-1));
        if(posiciones.indexOf(indx-1)!=-1)
            posiciones.remove(posiciones.indexOf(indx-1));
        if(posiciones.indexOf(indx-1-dimension)!=-1)
            posiciones.remove(posiciones.indexOf(indx-1-dimension));
        if(posiciones.indexOf(indx-dimension)!=-1)
            posiciones.remove(posiciones.indexOf(indx-dimension));
        if(posiciones.indexOf(indx-dimension+1)!=-1)
            posiciones.remove(posiciones.indexOf(indx-dimension+1));
    }

}