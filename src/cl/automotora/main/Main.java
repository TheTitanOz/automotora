package cl.automotora.main;

import java.util.ArrayList;
import java.util.Scanner;

import cl.automotora.model.Camioneta;
import cl.automotora.model.Coupe;
import cl.automotora.model.SUV;
import cl.automotora.model.Sedan;

public class Main {	

	public static void main(String[] args) {

		ArrayList<Camioneta> camionetasList = new ArrayList<Camioneta>();
		ArrayList<SUV> suvList = new ArrayList<SUV>();
		ArrayList<Sedan> sedanList = new ArrayList<Sedan>();
		ArrayList<Coupe> coupeList = new ArrayList<Coupe>();
		
		// Generar el listado de vehículos iniciales asumiendo que el admin no agrega vehículos.
		
		for (int i = 0; i<3; i+=1) {
			String patente = String.format("ABCD%d%d", (i+1), (i+2));
			camionetasList.add( new Camioneta(patente,i) );
		}
		
		for (int i = 0; i<7; i+=1) {
			String patente = String.format("BDEF%d%d", (i+1), (i+2));
			suvList.add(new SUV(patente, i%2==0,i));
		}
		
		for (int i = 0; i<4; i+=1) {
			String patente = String.format("EFGH%d%d", (i+1), (i+2));
			sedanList.add(new Sedan(patente,i));
		}
		
		for (int i = 0; i<1; i+=1) {
			String patente = String.format("HIJK%d%d", (i+1), (i+2));
			coupeList.add(new Coupe(patente,i));
		}
		
		
		// menú
		// 1. arrendar solo disponibles
		// 2. devolver solo no disponibles
		// 3. verificar disponibles
		// 4. monto total

		Scanner scan = new Scanner(System.in);
		String opcionTexto = "";
		
		int opcion;
		
		do {
			System.out.println("Menú");
			System.out.println("[1]. Arrendar");
			System.out.println("[2]. Devolver");
			System.out.println("[3]. Verificar");
			System.out.println("[4]. Montos recaudados");
			
			System.out.println("[0]. Salir");
			
			System.out.println("Seleccione una opcion;");
			opcionTexto = scan.nextLine();
			opcion = Integer.parseInt(opcionTexto);
			
			
			if (opcion == 0) {
				// Salir
			} else if (opcion == 1) {
				// arrendar
				_arrendarAutomoviles(scan, camionetasList, suvList, sedanList, coupeList);
			} else if (opcion == 2) {

			} else if ( opcion == 3 ) {
				
				listarAutomoviles(camionetasList, suvList, sedanList, coupeList);
				
			} else if ( opcion == 4 ) {
				
			} else {
				System.out.println("Opción no valida.");
			}
			
			
		} while(opcion != 0);
		
	}
	
	/**
	 * Este método me permite ARRENDAR un vehículo por medio de la patente.
	 * @param _scan es nuestro parámetro de interaccion con el usuairo
	 * @param _camionetasList
	 * @param _suvList
	 * @param _sedanList
	 * @param _coupeList
	 */
	private static void _arrendarAutomoviles (
			Scanner _scan,
			ArrayList<Camioneta> _camionetasList,
			ArrayList<SUV> _suvList,
			ArrayList<Sedan> _sedanList,
			ArrayList<Coupe> _coupeList
	) {

		for (int i =0; i<_camionetasList.size(); i+=1) {
			Camioneta camionetaObjeto = _camionetasList.get(i);
			if (camionetaObjeto.isDisponible()) {
				System.out.println("\t"+camionetaObjeto.conocerAuto());
			}
		}
		
		for (int i =0; i<_suvList.size(); i+=1) {
			SUV suvObjeto = _suvList.get(i);
			if (suvObjeto.isDisponible()) {
				System.out.println("\t"+suvObjeto.conocerAuto());
			}
		}
		for (int i =0; i<_sedanList.size(); i+=1) {
			Sedan sedanObjeto = _sedanList.get(i);
			if (sedanObjeto.isDisponible()) {
				System.out.println("\t"+sedanObjeto.conocerAuto());
			}
		}
		for (int i =0; i<_coupeList.size(); i+=1) {
			Coupe coupeObjeto = _coupeList.get(i);
			if (coupeObjeto.isDisponible()) {
				System.out.println("\t"+coupeObjeto.conocerAuto());
			}
		}
		
		String opcionPatente;
		
		
		Camioneta camioneta = null;
		SUV suv = null;
		Sedan sedan = null;
		Coupe coupe = null;
		
		
		do {
			System.out.println("\tIngrese una patente o enter para salir:");
			opcionPatente = _scan.nextLine();
			
			// obtengo el objeto que voy a modificar.
			camioneta = getCamionetaByPatente(_camionetasList, opcionPatente, true);
			if (camioneta == null) {
				suv = getSUVByPatente(_suvList, opcionPatente, true);
				if (suv == null) {
					sedan = getSedanByPatente(_sedanList, opcionPatente, true);
					if (sedan == null) {
						coupe  = getCoupeByPatente(_coupeList,opcionPatente, true);
					}
				}
			}
			
			// valido si la patente existe en alguna de las listas.
			if ( (camioneta != null || suv != null) || (sedan != null || coupe != null)) { // la patente existe
			
				// i) (false || false ) || (true || false) 
				// ii)     false        ||       true
				// iii)				true
				
				// ¿dónde está la patente?
				if (camioneta != null) {
					camioneta.setDisponible(false);
					_camionetasList.set(camioneta.getPosicion(), camioneta);
					System.out.println("\tCamioneta modificada correctamente.");
				} else if (suv != null) {
					suv.setDisponible(false);
					_suvList.set(suv.getPosicion(), suv);
					System.out.println("\tSUV modificada correctamente.");
				} else if (sedan != null) {
					sedan.setDisponible(false);
					_sedanList.set(sedan.getPosicion(), sedan);
					System.out.println("\tSedan modificada correctamente.");
				} else if (coupe != null) {
					coupe.setDisponible(false);
					_coupeList.set(coupe.getPosicion(), coupe);
					System.out.println("\tCoupe modificada correctamente.");
				}
			} else {
				System.out.println("\tLa patente no existe!");
			}
			
		} while(!opcionPatente.equals(""));
	}
	
	
	/**
	 * Obtengo la Camioneta en base a la patente solo si está disponible
	 * @param _lista listado de camionetas donde buscar
	 * @param _patente patente a buscar
	 * @return puede ser null (no existe) o el elemento buscado
	 */
	private static Camioneta getCamionetaByPatente(ArrayList<Camioneta> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			
			if (soloDisponibles) {
				if ( _lista.get(i).isDisponible() && _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			} else {
				if ( _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			}
			
		}
		return null;
	}

	/**
	 * 
	 * @param _lista de SUV
	 * @param _patente
	 * @return
	 */
	private static SUV getSUVByPatente( ArrayList<SUV> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			if (soloDisponibles) {
				if ( _lista.get(i).isDisponible() && _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			} else {
				if ( _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param _lista de Sedan
	 * @param _patente
	 * @return
	 */
	private static Sedan getSedanByPatente( ArrayList<Sedan> _lista, String _patente, boolean soloDisponibles) {
		
		for ( int i=0; i<_lista.size(); i+=1) {
			if (soloDisponibles) {
				if ( _lista.get(i).isDisponible() && _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			} else {
				if ( _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			}
		}
		
		return null;
		
	}

	/**
	 * 
	 * @param _lista de Coupe
	 * @param _patente
	 * @return
	 */
	private static Coupe getCoupeByPatente( ArrayList<Coupe> _lista, String _patente, boolean soloDisponibles) {
		
		for ( int i=0; i<_lista.size(); i+=1) {
			if (soloDisponibles) {
				if ( _lista.get(i).isDisponible() && _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			} else {
				if ( _lista.get(i).getPatente().equals(_patente)) {  
					return _lista.get(i);
				}
			}
		}
		
		return null;
		
	}
	
	private static void listarAutomoviles(
			ArrayList<Camioneta> _camionetasList,
			ArrayList<SUV> _suvList,
			ArrayList<Sedan> _sedanList,
			ArrayList<Coupe> _coupeList
	) {
		
		for (int i =0; i<_camionetasList.size(); i+=1) {
			Camioneta camionetaObjeto = _camionetasList.get(i);
			if (camionetaObjeto.isDisponible()) {
				System.out.println("\t"+camionetaObjeto.toString());
			}
		}
		
		for (int i =0; i<_suvList.size(); i+=1) {
			SUV suvObjeto = _suvList.get(i);
			if (suvObjeto.isDisponible()) {
				System.out.println("\t"+suvObjeto.toString());
			}
		}
		for (int i =0; i<_sedanList.size(); i+=1) {
			Sedan sedanObjeto = _sedanList.get(i);
			if (sedanObjeto.isDisponible()) {
				System.out.println("\t"+sedanObjeto.toString());
			}
		}
		for (int i =0; i<_coupeList.size(); i+=1) {
			Coupe coupeObjeto = _coupeList.get(i);
			if (coupeObjeto.isDisponible()) {
				System.out.println("\t"+coupeObjeto.toString());
			}
		}
		
		
	}
	
	
}
