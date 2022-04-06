package cl.automotora.model;

public class Camioneta extends Automovil {
	
	// constructor
	public Camioneta(String patente, int posicion) {
		super(patente, "Camioneta", 70000, posicion);
	}

	public Camioneta(String patente, int valor, int posicion) {
		super(patente, "Camioneta", valor, posicion);
	}
	

}
