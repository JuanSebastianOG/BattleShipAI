package co.edu.javeriana.algoritmos.proyecto;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int prom = 0;
		int x;
		for(int i = 0; i<100;i++) {
			Jugador jugadorA = new JugadorClass();
			Jugador jugadorB = new JugadorClass();
			
			int[]barcos= {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};
			Tablero tableroA = jugadorA.iniciarTablero(100, barcos);
			Tablero tableroB = jugadorB.iniciarTablero(100, barcos);
			ResumenTablero rtA = tableroA.obtenerResumen();
			ResumenTablero rtB = tableroB.obtenerResumen();
			
			jugadorA.recibirResumenRival(rtB);
			jugadorB.recibirResumenRival(rtA);
		
			x = 0;
			while(jugadorA.numeroBarcosNoHundidos()!=0 && jugadorB.numeroBarcosNoHundidos()!=0)
			{
				Casilla da = jugadorA.realizarDisparo();
				RespuestaJugada rjB = jugadorB.registrarDisparoAPosicion(da);
				jugadorA.procesarResultadoDisparo(rjB);
				Casilla db = jugadorB.realizarDisparo();
				RespuestaJugada rjA = jugadorA.registrarDisparoAPosicion(db);
				jugadorB.procesarResultadoDisparo(rjA);
				
				x++;
			}
			prom = prom + x;
			if(jugadorA.numeroBarcosNoHundidos()==0)
			{
				System.out.println("EL GANADOR ES B " + "despues de " + x + " disparos");
			}else System.out.println("EL GANADOR ES A "+ "despues de " + x + " disparos");
		}
		System.out.println("EL PROMEDIO DE DISPAROS ES "+ prom/100);
	}

}
