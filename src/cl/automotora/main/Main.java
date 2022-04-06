package cl.automotora.main;

import java.util.ArrayList;
import java.util.Random;
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
			String patente = generatePatente();
			camionetasList.add( new Camioneta(patente,i) );
		}
		
		for (int i = 0; i<7; i+=1) {
			String patente = generatePatente();
			suvList.add(new SUV(patente, i%2==0,i));
		}
		
		for (int i = 0; i<4; i+=1) {
			String patente = generatePatente();
			sedanList.add(new Sedan(patente,i));
		}
		
		for (int i = 0; i<1; i+=1) {
			String patente = generatePatente();
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
			System.out.println("Seleccione una opción:");
			opcionTexto = scan.nextLine();
			
			try {
				opcion = Integer.parseInt(opcionTexto);
			} catch( NumberFormatException err ) {
				opcion = -1;
			}
			
			if (opcion == 0) {
				// Salir
			} else if (opcion == 1) {
				// arrendar
				String opcionArriendo;
				do {
					System.out.println("\tIngrese si desea una camioneta, un suv, un sedan o un coupe. O enter para volver:");
					opcionArriendo = scan.nextLine();
					if (opcionArriendo.equals("")) {
						// atras
					} else if (opcionArriendo.toLowerCase().equals("camioneta")) {
						camionetasList = onRentCamioneta(scan, camionetasList, true);
					} else if (opcionArriendo.toLowerCase().equals("suv")) {
						suvList = onRentSUV(scan, suvList, true);
					} else if (opcionArriendo.toLowerCase().equals("sedan")) {
						sedanList = onRentSedan(scan, sedanList, true);
					} else if (opcionArriendo.toLowerCase().equals("coupe")) {
						coupeList = onRentCoupe(scan, coupeList, true);
					} else {
						System.out.println("\tOpción no encontrada");
					}
				} while (!opcionArriendo.equals(""));
			} else if (opcion == 2) {
				// devolver
				String opcionDevolver;
				do {
					System.out.println("\tIngrese si desea devolver una camioneta, un suv, un sedan o un coupe. O enter para volver:");
					opcionDevolver = scan.nextLine();
					if (opcionDevolver.equals("")) {
						// atras
					} else if (opcionDevolver.toLowerCase().equals("camioneta")) {
						camionetasList = onRentCamioneta(scan, camionetasList,false);
					} else if (opcionDevolver.toLowerCase().equals("suv")) {
						suvList = onRentSUV(scan, suvList,false);
					} else if (opcionDevolver.toLowerCase().equals("sedan")) {
						sedanList = onRentSedan(scan, sedanList,false);
					} else if (opcionDevolver.toLowerCase().equals("coupe")) {
						coupeList = onRentCoupe(scan, coupeList,false);
					} else {
						System.out.println("\tOpción no encontrada");
					}
				} while (!opcionDevolver.equals(""));
			} else if ( opcion == 3 ) {
				// listar
				System.out.println("\tAutomoviles disponibles");
				System.out.println("\t-----------------------");
				listarAutomoviles(camionetasList, suvList, sedanList, coupeList, true);
				System.out.println("\n\tAutomoviles NO disponibles");
				System.out.println("\t--------------------------");
				listarAutomoviles(camionetasList, suvList, sedanList, coupeList, false);
			} else if ( opcion == 4 ) {
				// montos recaudados
				System.out.printf("\n\n\t Total en disponibles %d\n\n", sumarArriendos(camionetasList, suvList, sedanList, coupeList));
			} else {
				System.out.println("Opción no valida.");
			}
			
		} while(opcion != 0);
		
	}
	/**
	 * Permite arrendar una camioneta
	 * @param _scan interacción con el usuario
	 * @param _camionetasList
	 * @param _arrendar indica si es arriendo (true) o es devolver (false) 
	 * @return return el arreglo de objeto con los cambios de la camioneta
	 */
	private static ArrayList<Camioneta> onRentCamioneta(Scanner _scan, ArrayList<Camioneta> _camionetasList, boolean _arrendar) {
		
		int x=0;
		for (int i=0; i<_camionetasList.size(); i+=1) {
			Camioneta camionetaObjeto = _camionetasList.get(i);
			if (camionetaObjeto.isDisponible() == _arrendar) {
				System.out.println("\t"+camionetaObjeto.conocerAuto());
				x++;
			}
		}
		
		if (x == 0) {
			System.out.println("No hay vehículos para " + (_arrendar? "arrendar":"devolver") + ".");
		} else {

			String opcionPatente;
			Camioneta camioneta = null;

			do {
				System.out.println("\tIngrese una patente o enter para salir:");
				opcionPatente = _scan.nextLine();

				if (opcionPatente.equals("")) {
					//salir
				} else {
					camioneta = getCamionetaByPatente(_camionetasList, opcionPatente, true);
					if ( camioneta != null ) {
						camioneta.setDisponible(!_arrendar);
						_camionetasList.set(camioneta.getPosicion(), camioneta);
						System.out.println("\tCamioneta modificada correctamente.");
					} else {
						System.out.println("\tLa patente no existe!");
					}
				}
				
			} while(!opcionPatente.equals(""));
			
		
		}

		return _camionetasList;
	}
	
	/**
	 * Arrendar un SUV
	 * @param _scan
	 * @param _suvList
	 * @param _arrendar indica si es arriendo (true) o es devolver (false) 
	 * @return retorno el arreglo de objetos del suv
	 */
	private static ArrayList<SUV> onRentSUV(Scanner _scan, ArrayList<SUV> _suvList, boolean _arrendar) {

		int x=0;
		for (int i=0; i<_suvList.size(); i+=1) {
			SUV suvObjeto = _suvList.get(i);
			if (suvObjeto.isDisponible() == _arrendar) {
				System.out.println("\t"+suvObjeto.conocerAuto());
				x++;
			}
		}

		if (x == 0) {
			System.out.println("No hay vehículos para " + (_arrendar? "arrendar":"devolver") + ".");
		} else {
			String opcionPatente;
			SUV suv = null;
	
			do {
				System.out.println("\tIngrese una patente o enter para salir:");
				opcionPatente = _scan.nextLine();
	
				if (opcionPatente.equals("")) {
					//salir
				} else {
					suv = getSUVByPatente(_suvList, opcionPatente, _arrendar);
					if ( suv != null ) {
						suv.setDisponible(!_arrendar);
						_suvList.set(suv.getPosicion(), suv);
						System.out.println("\tSUV modificada correctamente.");
					} else {
						System.out.println("\tLa patente no existe!");
					}
				}
				
			} while(!opcionPatente.equals(""));

		}
	
		return _suvList;
	}
	

	/**
	 * Arrendar Sedan
	 * @param _scan
	 * @param _sedanList
	 * @param _arrendar indica si es arriendo (true) o es devolver (false) 
	 * @return retorno el arreglo de objetos del sedan
	 */
	private static ArrayList<Sedan> onRentSedan(Scanner _scan, ArrayList<Sedan> _sedanList, boolean _arrendar) {
		
		int x=0;
		for (int i=0; i<_sedanList.size(); i+=1) {
			Sedan sedanObjeto = _sedanList.get(i);
			if (sedanObjeto.isDisponible() == _arrendar) {
				System.out.println("\t"+sedanObjeto.conocerAuto());
				x++;
			}
		}
		

		if (x == 0) {
			System.out.println("No hay vehículos para " + (_arrendar? "arrendar":"devolver") + ".");
		} else {
			String opcionPatente;
			Sedan sedan = null;
			
			do {
				System.out.println("\tIngrese una patente o enter para salir:");
				opcionPatente = _scan.nextLine();
				
				if (opcionPatente.equals("")) {
					//salir
				} else {
					sedan = getSedanByPatente(_sedanList, opcionPatente, _arrendar);
					if ( sedan != null ) {
						int posicion = sedan.getPosicion();
						sedan.setDisponible(!_arrendar);
						_sedanList.set(posicion, sedan);
						System.out.println("\tSedan ha sido modificada correctamente.");
					} else {
						System.out.println("\tLa patente no existe!");
					}
				}
				
			} while(!opcionPatente.equals(""));
		}

		return _sedanList;
	}
	
	/**
	 * Arrendar coupe
	 * @param _scan
	 * @param _coupeList
	 * @param _arrendar indica si es arriendo (true) o es devolver (false) 
	 * @return retorno el arreglo de objetos del coupe
	 */
	private static ArrayList<Coupe> onRentCoupe( Scanner _scan, ArrayList<Coupe> _coupeList, boolean _arrendar) {
		int x=0;
		for (int i=0; i<_coupeList.size(); i+=1) {
			Coupe coupeObjeto = _coupeList.get(i);
			if (coupeObjeto.isDisponible() == _arrendar) {
				System.out.println("\t"+coupeObjeto.conocerAuto());
				x++;
			}
		}

		if (x == 0) {
			System.out.println("No hay vehículos para " + (_arrendar? "arrendar":"devolver") + ".");
		} else {
			String opcionPatente;
			Coupe coupe = null;
			
			do {
				System.out.println("\tIngrese una patente o enter para salir:");
				opcionPatente = _scan.nextLine();
				
				if (opcionPatente.equals("")) {
					//salir
				} else {
					coupe  = getCoupeByPatente(_coupeList,opcionPatente, _arrendar);
					if (coupe != null) {
						coupe.setDisponible(!_arrendar);
						_coupeList.set(coupe.getPosicion(), coupe);
						System.out.println("\tCoupe modificada correctamente.");
						
					} else {
						System.out.println("\tLa patente no existe!");
					}
				}
				
			} while(!opcionPatente.equals(""));
		}
		
		return _coupeList;
		
	}
	
	
	/**
	 * Obtengo la Camioneta en base a la patente solo si está disponible
	 * @param _lista listado de camionetas donde buscar
	 * @param _patente patente a buscar
	 * @param soloDisponibles indica si buscamos solo los disponibles o todos.
	 * @return puede ser null (no existe) o el elemento buscado
	 */
	private static Camioneta getCamionetaByPatente(ArrayList<Camioneta> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			if ( _lista.get(i).isDisponible() == soloDisponibles && _lista.get(i).getPatente().equals(_lista.get(i).formatoPatente(_patente))) {
				return _lista.get(i);
			}
		}
		return null;
	}

	/**
	 * Obtengo el SUV en base a la patente
	 * @param _lista de SUV
	 * @param _patente
	 * @param soloDisponibles indica si buscamos solo los disponibles o todos.
	 * @return
	 */
	private static SUV getSUVByPatente( ArrayList<SUV> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			if ( _lista.get(i).isDisponible() == soloDisponibles && _lista.get(i).getPatente().equals(_lista.get(i).formatoPatente(_patente))) {
				return _lista.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Obtengo el Sedan en base a la patente
	 * @param _lista de Sedan
	 * @param _patente
	 * @param soloDisponibles indica si buscamos solo los disponibles o todos.
	 * @return
	 */
	private static Sedan getSedanByPatente( ArrayList<Sedan> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			if ( _lista.get(i).isDisponible() == soloDisponibles && _lista.get(i).getPatente().equals(_lista.get(i).formatoPatente(_patente))) {
				return _lista.get(i);
			}
		}
		return null;
	}

	/**
	 *  Obtengo el Coupe en base a la patente
	 * @param _lista de Coupe
	 * @param _patente
	 * @param soloDisponibles indica si buscamos solo los disponibles o todos.
	 * @return
	 */
	private static Coupe getCoupeByPatente( ArrayList<Coupe> _lista, String _patente, boolean soloDisponibles) {
		for ( int i=0; i<_lista.size(); i+=1) {
			if ( _lista.get(i).isDisponible() == soloDisponibles && _lista.get(i).getPatente().equals(_lista.get(i).formatoPatente(_patente))) {
				return _lista.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Lista automóvilos
	 * @param _camionetasList
	 * @param _suvList
	 * @param _sedanList
	 * @param _coupeList
	 */
	private static void listarAutomoviles(
			ArrayList<Camioneta> _camionetasList,
			ArrayList<SUV> _suvList,
			ArrayList<Sedan> _sedanList,
			ArrayList<Coupe> _coupeList,
			boolean isDisponibles
	) {
		for (int i =0; i<_camionetasList.size(); i+=1) {
			Camioneta camionetaObjeto = _camionetasList.get(i);
			if (camionetaObjeto.isDisponible() == isDisponibles) {
				System.out.println("\t"+camionetaObjeto.toString());
			}
		}
		
		for (int i =0; i<_suvList.size(); i+=1) {
			SUV suvObjeto = _suvList.get(i);
			if (suvObjeto.isDisponible() == isDisponibles) {
				System.out.println("\t"+suvObjeto.toString());
			}
		}
		for (int i =0; i<_sedanList.size(); i+=1) {
			Sedan sedanObjeto = _sedanList.get(i);
			if (sedanObjeto.isDisponible() == isDisponibles) {
				System.out.println("\t"+sedanObjeto.toString());
			}
		}
		for (int i =0; i<_coupeList.size(); i+=1) {
			Coupe coupeObjeto = _coupeList.get(i);
			if (coupeObjeto.isDisponible() == isDisponibles) {
				System.out.println("\t"+coupeObjeto.toString());
			}
		}
	}
	
	/**
	 * Obtiene la suma de los vehiculos que no están disponibles 
	 * @param _camionetasList
	 * @param _suvList
	 * @param _sedanList
	 * @param _coupeList
	 * @return el valor de la suma
	 */
	private static int sumarArriendos(
			ArrayList<Camioneta> _camionetasList,
			ArrayList<SUV> _suvList,
			ArrayList<Sedan> _sedanList,
			ArrayList<Coupe> _coupeList
	) {
		int suma=0;
		for (int i =0; i<_camionetasList.size(); i+=1) {
			Camioneta camionetaObjeto = _camionetasList.get(i);
			if (!camionetaObjeto.isDisponible()) {
				suma += camionetaObjeto.getValor();
			}
		}
		
		for (int i =0; i<_suvList.size(); i+=1) {
			SUV suvObjeto = _suvList.get(i);
			if (!suvObjeto.isDisponible()) {
				suma += suvObjeto.getValor();
			}
		}
		for (int i =0; i<_sedanList.size(); i+=1) {
			Sedan sedanObjeto = _sedanList.get(i);
			if (!sedanObjeto.isDisponible()) {
				suma += sedanObjeto.getValor();
			}
		}
		for (int i =0; i<_coupeList.size(); i+=1) {
			Coupe coupeObjeto = _coupeList.get(i);
			if (!coupeObjeto.isDisponible()) {
				suma += coupeObjeto.getValor();
			}
		}
		
		return suma;
	}
	
	private static String generatePatente() {
		
		String universo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    Random random = new Random();
		
		String patente = "";

		for (int i= 0; i<4; i+=1) {
			patente += universo.charAt(random.nextInt(universo.length()));
		}
		for (int i= 0; i<2; i+=1) {
			patente += random.nextInt(1,9);
		}
		
		
		return patente;
	}
	
}
