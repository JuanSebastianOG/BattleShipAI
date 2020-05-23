package co.edu.javeriana.algoritmos.proyecto;

import java.util.ArrayList;
import java.util.Collections;

public class JugadorClass implements Jugador{
	int casos = 0;
	int casoReconocido = 0;
	TableroClass tableroClass = new TableroClass();
	int[][] tablero;
    ArrayList<Integer> posiciones;
    int[] resumenFilas;
    int[] resumenColumnas;
    int[][] matShootBoard;
    int dimension;
    int hRow=-1,hCol=-1;
	int auxRow=-1,auxCol=-1;
    int col, row;
	
    public JugadorClass() {
    	
    }
    
	@Override
	public String obtenerNombre() {
		// TODO Auto-generated method stub
		return "Java Dabba Doo";
	}

	@Override
	public Tablero iniciarTablero(int dimension, int[] barcos) {
		// TODO Auto-generated method stub
		this.posiciones = new ArrayList<Integer>();
		this.dimension = dimension;
		this.matShootBoard = new int[dimension][dimension];
		this.resumenFilas= new int[dimension];
	    this.resumenColumnas = new int[dimension];
        tablero = new int[dimension][dimension];

        for(int i = 0; i<dimension*dimension;i++)   
            posiciones.add(i);

        Collections.shuffle(posiciones);

        for(int i=0;i<barcos.length;i++){
            boolean validado = false;
            int random = 0;
            while(validado == false) {
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
                
            	if(random == posiciones.size()) {
            		return iniciarTablero(dimension, barcos);
            	}
            } 
        }
		
        tableroClass.setTablero(tablero);
        tableroClass.setDimension(dimension);
        tableroClass.setBarcos(barcos);
        
		return tableroClass;
	}

	@Override
	public void recibirResumenRival(ResumenTablero resumen) {
		// TODO Auto-generated method stub
		for(int i = 0; i<dimension;i++) {
			this.resumenFilas[i] = resumen.getCasillasOcupadasEnFila(i);
		}
		for(int i = 0; i<dimension;i++) {
			this.resumenColumnas[i] = resumen.getCasillasOcupadasEnColumna(i);
		}
	}

	@Override
	public RespuestaJugada registrarDisparoAPosicion(Casilla posicion) {
		// TODO Auto-generated method stub

		return tableroClass.dispararACasilla(posicion);
	}

	@Override
	public Casilla realizarDisparo() {
		// TODO Auto-generated method stub		
		if(hRow!=-1 && hCol!=-1) {
			return hunt(hRow, hCol);
		}else {
			int minCol = 0, minRow = 0;
			int posMinCol=0, posMinRow=0;
			
			//Busqueda menor y mayor en el resumen
			for (int i = 0; i < dimension; i++) {
				if (resumenFilas[i] > minRow) {
					minRow = resumenFilas[i];
					posMinRow = i;
				}
				if (resumenColumnas[i] > minCol) {
					minCol = resumenColumnas[i] ;
					posMinCol = i;
				}
			}
			if (minCol > minRow) {
				while(true) {
					for (int i = 0; i < dimension; i += minCol) {
						if (matShootBoard[i][posMinCol] == 0) {
							this.row = i;
							this.col = posMinCol;
							return new Casilla(row, col);
						}
					}
					minCol--;
				}
			} else{
				while(true) {
					for (int i = 0; i < dimension; i += minRow) {
						if (matShootBoard[posMinRow][i] == 0) {
							this.row = posMinRow;
							this.col = i;
							return new Casilla(row, col);
						}
					}
					minRow--;
				}
			}
		}
	}

	@Override
	public void procesarResultadoDisparo(RespuestaJugada resultado) {
		// TODO Auto-generated method stub
		if(resultado.getLetrero().equalsIgnoreCase("agua"))
		{
			this.matShootBoard[row][col]=2;
			if(casoReconocido!=0) {
				if(casoReconocido==1) {
					casoReconocido=5;
				}else if(casoReconocido==2) {
					casoReconocido=6;
				}else if(casoReconocido==3) {
					casoReconocido=7;
				}else if(casoReconocido==4) {
					casoReconocido=8;
				}else if(casoReconocido==5) {
					casoReconocido=1;
				}else if(casoReconocido==6) {
					casoReconocido=2;
				}else if(casoReconocido==7) {
					casoReconocido=3;
				}else if(casoReconocido==8) {
					casoReconocido=4;
				}
	            col=auxCol;
	            row=auxRow;
			}
		}
		if(resultado.getLetrero().equalsIgnoreCase("impacto") && this.auxCol==-1 && this.auxRow ==-1) {
			this.matShootBoard[row][col]=1;
			this.resumenFilas[row]-=1;
			this.resumenColumnas[col]-=1;
			this.auxCol=col;
			this.auxRow=row;
			this.hCol=col;
			this.hRow=row;
			if(casos!=0) casoReconocido=casos;
		}else if(resultado.getLetrero().equalsIgnoreCase("impacto")) {
				this.matShootBoard[row][col]=1;
				this.resumenFilas[row]-=1;
				this.resumenColumnas[col]-=1;
				this.hCol=col;
				this.hRow=row;
				if(casos!=0) casoReconocido=casos;
			}
		if(resultado.getLetrero().equalsIgnoreCase("hundido")) {
			this.matShootBoard[row][col]=1;
			this.resumenFilas[row]-=1;
			this.resumenColumnas[col]-=1;
			this.auxCol=-1;
			this.auxRow=-1;
			this.hCol=-1;
			this.hRow=-1;
			casos=0;
			casoReconocido=0;
		}
		
	}

	@Override
	public int numeroBarcosNoHundidos() {
		return tableroClass.numeroBarcosNoHundidos();
	}
	
//------------------------------------------------------------------------------------------------
	public boolean horizontal(int nB, int indx, int tam, int dimension) {
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

    public boolean vertical(int nB, int indx, int tam, int dimension){
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

    public boolean diagonalIzq(int nB, int indx, int tam, int dimension){
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

    public boolean diagonalDer(int nB, int indx, int tam, int dimension){
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

    public boolean validarEntorno(int indx, int dimension){
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

    public void deshabilitar(int indx, int dimension){
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
//--------------------------------------------------------------------------------------------
    private Casilla hunt(int row, int col) {
       	while(true) {
       		if(casoReconocido!=0){
       			if (col+1<dimension&&matShootBoard[row][col + 1] == 0&&casoReconocido==1) {
    	        	this.row = row;
    	        	this.col = col+1;
    	        	this.casos = 1;
    	        	return new Casilla(row,col+1);
    	        } else if (row+1<dimension&&col+1<dimension&&matShootBoard[row + 1][col + 1] == 0&&casoReconocido==2) {
    	        	this.row = row+1;
    	        	this.col = col+1;
    	        	this.casos = 2;
    	        	return new Casilla(row+1,col+1);
    	        } else if (row+1<dimension&&matShootBoard[row + 1][col] == 0&&casoReconocido==3) {
    	        	this.row = row+1;
    	        	this.col = col;
    	        	this.casos = 3;
    	        	return new Casilla(row+1,col);
    	        } else if (row+1<dimension&&col-1>=0&&matShootBoard[row + 1][col - 1] == 0&&casoReconocido==4) {
    	        	this.row = row+1;
    	        	this.col = col-1;
    	        	this.casos = 4;
    	        	return new Casilla(row+1,col-1);
    	        } else if (col-1>=0&&matShootBoard[row][col - 1] == 0&&casoReconocido==5) {
    	        	this.row = row;
    	        	this.col = col-1;
    	        	this.casos = 5;
    	        	return new Casilla(row,col-1);
    	        }else if (row-1>=0&&col-1>=0&&matShootBoard[row - 1][col - 1] == 0&&casoReconocido==6) {
    	        	this.row = row-1;
    	        	this.col = col-1;
    	        	this.casos = 6;
    	        	return new Casilla(row-1,col-1);
    	        } else if (row-1>=0&&matShootBoard[row - 1][col] == 0&&casoReconocido==7) {
    	        	this.row = row-1;
    	        	this.col = col;
    	        	this.casos = 7;
    	        	return new Casilla(row-1,col);
    	        } else if (row-1>=0&&col+1<dimension&&matShootBoard[row - 1][col + 1] == 0&&casoReconocido==8) {
    	        	this.row = row-1;
    	        	this.col = col+1;
    	        	this.casos = 8;
    	        	return new Casilla(row-1,col+1);
    	        }
       		}
	        if (col+1<dimension&&matShootBoard[row][col + 1] == 0) {
	        	this.row = row;
	        	this.col = col+1;
	        	this.casos = 1;
	        	return new Casilla(row,col+1);
	        } else if (row+1<dimension&&col+1<dimension&&matShootBoard[row + 1][col + 1] == 0) {
	        	this.row = row+1;
	        	this.col = col+1;
	        	this.casos = 2;
	        	return new Casilla(row+1,col+1);
	        } else if (row+1<dimension&&matShootBoard[row + 1][col] == 0) {
	        	this.row = row+1;
	        	this.col = col;
	        	this.casos = 3;
	        	return new Casilla(row+1,col);
	        } else if (row+1<dimension&&col-1>=0&&matShootBoard[row + 1][col - 1] == 0) {
	        	this.row = row+1;
	        	this.col = col-1;
	        	this.casos = 4;
	        	return new Casilla(row+1,col-1);
	        } else if (col-1>=0&&matShootBoard[row][col - 1] == 0) {
	        	this.row = row;
	        	this.col = col-1;
	        	this.casos = 5;
	        	return new Casilla(row,col-1);
	        }else if (row-1>=0&&col-1>=0&&matShootBoard[row - 1][col - 1] == 0) {
	        	this.row = row-1;
	        	this.col = col-1;
	        	this.casos = 6;
	        	return new Casilla(row-1,col-1);
	        } else if (row-1>=0&&matShootBoard[row - 1][col] == 0) {
	        	this.row = row-1;
	        	this.col = col;
	        	this.casos = 7;
	        	return new Casilla(row-1,col);
	        } else if (row-1>=0&&col+1<dimension&&matShootBoard[row - 1][col + 1] == 0) {
	        	this.row = row-1;
	        	this.col = col+1;
	        	this.casos = 8;
	        	return new Casilla(row-1,col+1);
	        }
            col=auxCol;
            row=auxRow;
       	}
    }
}