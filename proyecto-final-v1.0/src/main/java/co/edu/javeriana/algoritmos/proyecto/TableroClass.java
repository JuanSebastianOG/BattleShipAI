package co.edu.javeriana.algoritmos.proyecto;

import java.util.ArrayList;
import java.util.List;

public class TableroClass implements Tablero{


	int[][] tablero;
	int[] barcos;
    int dimension;
    
    public TableroClass() {
    	
    };
    
	@Override
	public ResumenTablero obtenerResumen() {
		
		int[] casillasOcupadasFila = new int[dimension];
	    int[] casillasOcupadasColumna = new int[dimension];
		// TODO Auto-generated method stub
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++){
				if(tablero[i][j]!=0) {
					casillasOcupadasFila[i]++;
				}
				if(tablero[j][i]!=0) {
					casillasOcupadasColumna[i]++;
				}
			}
		}
		ResumenTablero resume= new ResumenTablero(casillasOcupadasFila, casillasOcupadasColumna);
		return resume;
		
	}

	@Override
	public List<Casilla> obtenerCasillasOcupadasPorBarco(int numeroBarco) {
		// TODO Auto-generated method stub
		List<Casilla> lista = new ArrayList<Casilla>();
		for (int i=0; i < dimension; i++)
		{
			for (int j=0; j < dimension; j++)
			{
				if (tablero[i][j]==numeroBarco)
				{
					lista.add(new Casilla(i,j));
				}
			}
		}
	    return lista;
	}

	@Override
	public RespuestaJugada dispararACasilla(Casilla casilla) {
		// TODO Auto-generated method stub
		RespuestaJugada respuesta = null;
		int disparo = tablero[casilla.getFila()][casilla.getColumna()];
		if(disparo==0)
		{
			respuesta = RespuestaJugada.AGUA;
		}
		if(disparo!=0)
		{
			barcos[disparo-1]--;
			respuesta = RespuestaJugada.IMPACTO;
			if(barcos[disparo-1]<=0) {
				respuesta = RespuestaJugada.HUNDIDO;
			}
		}
		return respuesta;
	}

	@Override
	public int numeroBarcosNoHundidos() {
		// TODO Auto-generated method stub
		int cantidad = 0;
		for(int i=0; i<barcos.length; i++)
		{	
			if(barcos[i]>0)
			{
				cantidad++;
			}
		}
		return cantidad;
	}
	
	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public void setBarcos(int[] barcos) {
		this.barcos = new int[barcos.length];
		System.arraycopy(barcos, 0, this.barcos, 0, barcos.length);
	}
}
