package cl.automotora.model;

public class SUV extends Automovil {

	// attr
	private boolean dobletraccion;
	
	// constructor
	public SUV(String patente, boolean dobletraccion, int posicion) {
		super(patente, "SUV", 50000, posicion);
		this.dobletraccion = dobletraccion;
	}

	// métodos getters and setters
	public boolean isDobletraccion() {
		return dobletraccion;
	}

	public void setDobletraccion(boolean dobletraccion) {
		this.dobletraccion = dobletraccion;
	}
	
	

}
