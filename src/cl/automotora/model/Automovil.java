package cl.automotora.model;

/**
 * Clase padre de de automóvil con todos elementos básicos.
 * 
 * @author Cristian Farías
 * @since 1.0
 */
public class Automovil implements IAutomovil {
	// attr
	protected int posicion;
	protected String patente;
	protected String tipo;
	protected int valor;
	protected boolean disponible;
	
	// constructor
	/**
	 * 
	 * Constructor del objeto tipo vehículo.
	 * 
	 * @param patente identificador único
	 * @param tipo hace referencia al tipo de vehículo
	 * @param valor es el precio.
	 */
	public Automovil(String patente, String tipo, int valor, int posicion) {
		super();
		this.patente = patente;
		this.tipo = tipo;
		this.valor = valor;
		this.posicion = posicion;
		this.disponible = true;
	}

	// métodos getters and setters
	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	@Override
	public String toString() {
		return "Automovil [patente=" + patente + ", tipo=" + tipo + ", valor=" + valor + ", disponible=" + disponible
				+ "]";
	}
	
	// método auxiliar
	
	/**
	 * Método que permite obtener la información del vehículo.
	 * @return string con la información.
	 */
	@Override
	public String conocerAuto() {
		return String.format("El vehículo tipo %s, tiene una patente %s", this.tipo, this.patente);
	}
	
	/**
	 * Método que permite formatear la patente.
	 * <p>Ejemplo</p>
	 *<blockquote><pre>
	 *	formatoPatente("abcd12");
	 *	formatoPatente("za1234");
	 * </pre></blockquote>
	 * @param patente sin espacios, sin guión
	 * @return
	 */
	@Override
	public String formatoPatente(String patente) {
		String patenteFormat = patente;
		return patenteFormat.toUpperCase();
	}

}
