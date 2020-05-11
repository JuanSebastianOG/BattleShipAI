package tryBattleShip;

import java.util.Scanner;

public class main {
	static int dimension = 10;
	static int[][] matBoard = { { 0, 0, 0, 4, 4, 4, 4,0,0,2 },
								{ 0, 1, 0, 0, 0, 0, 0,0,0,2},
								{ 0, 0, 0, 0, 0, 0, 0,0,0,0 },
								{ 0, 0, 0, 0, 0, 0, 0,0,0,5 }, 
								{ 0, 0, 0, 0, 0, 0, 0,0,0,5 },
								{ 0, 3, 3, 3, 0, 0, 0,0,0,5 }, 
								{ 0,0, 0, 0, 0, 0, 0,0, 0,5 }, 
								{ 0,0, 0, 0, 0, 0, 0,0,0, 5 }, 
								{ 0, 0, 0, 0, 0, 0, 0,0,0,0 }, 
								{ 0, 0, 0, 0, 0, 0, 0,0,0,0 }};
	
	static int[][] matShootBoard = { 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0 }, 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0}, 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0},
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0},
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0}, 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0},
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0}, 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0}, 
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0},
			{ 0, 0, 0, 0, 0, 0, 0,0,0,0}};
	static int[] rRows = { 5, 2, 0, 1, 1, 4, 1,1,0,0 };
	static int[] rCol = { 0,2,1,2,1,1,1,0,0,7};
	static int posMinCol, posMinRow;

	static int hRow=-1,hCol=-1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int resultado=0;

		boolean juego=true;
		while(juego==true) {
			if(hRow!=-1 && hCol!=-1) {
				hunt(hRow, hCol);
			}else {
				int colS, rowS;
				int minCol = -1, minRow = -1;

				//Busqueda menor y mayor en el resumen
				for (int i = 0; i < dimension; i++) {
					if (rRows[i] > minRow) {
						minRow = rRows[i];
						posMinRow = i;
					}
					if (rCol[i] > minCol) {
						minCol = rCol[i];
						posMinCol = i;
					}
				}
				System.out.println(posMinRow);
				System.out.println(minRow);
				System.out.println(posMinCol);
				System.out.println(minCol);
				if (minCol > minRow) {
					for (int i = 0; i < dimension; i += minCol) {
						if (matShootBoard[i][posMinCol] == 0) {
							resultado = disparo(i, posMinCol);
							if(resultado==1) {
								posMinCol=posMinCol;
								posMinRow=i;
							}
							break;
						}
					}
				} else{
					for (int i = 0; i < dimension; i += minRow) {
						if (matShootBoard[posMinRow][i] == 0) {
							resultado = disparo(posMinRow, i);
							if(resultado==1) {
								posMinCol=i;
								posMinRow=posMinRow;
							}
							break;
						}
					}
				}
				
			}
			
		}
		

	}

	private static void  hunt(int row, int col) {
        int resultado=-1;
        if (col+1<dimension&&matShootBoard[row][col + 1] == 0) {
            resultado = disparo(row, col + 1);
            if(resultado==1) {
                hCol=col+1;
                hRow=row;
            }
        } else if (row+1<dimension&&col+1<dimension&&matShootBoard[row + 1][col + 1] == 0) {
            resultado = disparo(row + 1, col + 1);
            if(resultado==1) {
                hCol=col+1;
                hRow=row +1;
            }
        } else if (row+1<dimension&&matShootBoard[row + 1][col] == 0) {
            resultado = disparo(row + 1, col);
            if(resultado==1) {
                hCol=col;
                hRow=row +1;
            }
        } else if (row+1<dimension&&col-1>0&&matShootBoard[row + 1][col - 1] == 0) {
            resultado = disparo(row + 1, col - 1);
            if(resultado==1) {
                hCol=col-1;
                hRow=row+1;
            }
        } else if (col-1>0&&matShootBoard[row][col - 1] == 0) {
            resultado = disparo(row, col - 1);
            if(resultado==1) {
                hCol=col-1;
                hRow=row;
            }
        }
        else if (row-1>0&&col-1>0&&matShootBoard[row - 1][col - 1] == 0) {
            resultado = disparo(row - 1, col - 1);
            if(resultado==1) {
                hCol=col-1;
                hRow=row-1;
            }
        } else if (row-1>0&&matShootBoard[row - 1][col] == 0) {
            resultado = disparo(row - 1, col);
            if(resultado==1) {
                hCol=col;
                hRow=row-1;
            }
        } else if (row-1>0&&col+1<dimension&&matShootBoard[row - 1][col + 1] == 0) {
            resultado = disparo(row - 1, col + 1);
            if(resultado==1) {
                hCol=col+1;
                hRow=row-1;
            }
        }
        if(resultado==2) {
            hCol=-1;
            hRow=-1;
        }
        if(resultado==-1) {
	//En implementacion se llama a si mismo cuando no logra hacer el disparo
            hCol=posMinCol;
            hRow=posMinRow;
            System.out.println("EN RESULTADO -1");
            System.out.println(posMinCol);
            System.out.println(posMinRow);
        }
    }

	private static int  disparo(int row, int col) {
		// TODO Auto-generated method stub
		if (matBoard[row][col] != 0) {
			matShootBoard[row][col] = 1;
			rRows[row]-=1;
			rCol[col]-=1;
			hRow=row;
			hCol=col;
			
		} else {
			matShootBoard[row][col] = 2;
		}
		
		System.out.println("Disparo en fila: "+row +" Columna: "+col);
		int res= new Scanner(System.in).nextInt();
		return res;
	}

}
