package PROYECTO_FINAL.Principal;






import PROYECTO_FINAL.Servicios.InscripcionManager;
import PROYECTO_FINAL.Servicios.GeneradorReportes;
import PROYECTO_FINAL.Modelos.*;
import PROYECTO_FINAL.Util.NavegadorRutas;
import PROYECTO_FINAL.Util.LectorCSV;
import PROYECTO_FINAL.excepciones.*;

import java.util.Scanner;

/**
 * Clase principal del Sistema de GestiÃ³n AcadÃ©mica.
 * Contiene el menÃº interactivo con las 22 opciones requeridas por el PDF.
 */
public class Main_proyecto_final {

    public static void main(String[] args) {

        // â”€â”€ InicializaciÃ³n de servicios â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        InscripcionManager manager  = new InscripcionManager();
        GeneradorReportes  generador = new GeneradorReportes();
        NavegadorRutas     navegador = new NavegadorRutas(10);
        Scanner sc = new Scanner(System.in);

        // Edificios iniciales del campus (mÃ­nimo 5 requerido por el PDF)
        navegador.registrarEdificioEnGrafo("Ingenieria");
        navegador.registrarEdificioEnGrafo("Biblioteca");
        navegador.registrarEdificioEnGrafo("Cafeteria");
        navegador.registrarEdificioEnGrafo("Rectoria");
        navegador.registrarEdificioEnGrafo("Laboratorios");

        // Conexiones de ejemplo entre edificios
        navegador.agregarConexion("Ingenieria",   "Cafeteria",    150);
        navegador.agregarConexion("Cafeteria",    "Rectoria",     180);
        navegador.agregarConexion("Ingenieria",   "Biblioteca",   200);
        navegador.agregarConexion("Biblioteca",   "Laboratorios", 100);
        navegador.agregarConexion("Rectoria",     "Laboratorios", 250);

        // â”€â”€ Bucle principal del menÃº â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
        int opcion = 0;
        do {
            System.out.println("\n============================================================");
            System.out.println("  PLANIFICACION ACADEMICA - SISTEMA UNIVERSITARIO");
            System.out.println("============================================================");
            System.out.println("=== GESTION DE ESTUDIANTES ===");
            System.out.println("  1. Registrar estudiante");
            System.out.println("  2. Buscar estudiante por ID");
            System.out.println("  3. Listar todos los estudiantes");
            System.out.println("  4. Eliminar estudiante");
            System.out.println("=== GESTION DE MATERIAS ===");
            System.out.println("  5. Crear materia");
            System.out.println("  6. Agregar pre-requisito");
            System.out.println("  7. Mostrar pre-requisitos");
            System.out.println("  8. Inscribir estudiante");
            System.out.println("  9. Cancelar inscripcion");
            System.out.println(" 10. Mostrar cola de espera");
            System.out.println("=== GESTION DE HORARIOS ===");
            System.out.println(" 11. Reservar horario en aula");
            System.out.println(" 12. Liberar horario");
            System.out.println(" 13. Consultar disponibilidad");
            System.out.println("=== RUTAS ENTRE EDIFICIOS ===");
            System.out.println(" 14. Agregar conexion entre edificios");
            System.out.println(" 15. Calcular ruta mas corta (Dijkstra)");
            System.out.println("=== REPORTES ACADEMICOS ===");
            System.out.println(" 16. Registrar nota");
            System.out.println(" 17. Ver reporte academico");
            System.out.println(" 18. Navegador de reportes (atras)");
            System.out.println("=== SISTEMA DESHACER/REHACER ===");
            System.out.println(" 19. Deshacer ultima operacion");
            System.out.println(" 20. Rehacer ultima operacion");
            System.out.println("=== PROCESAMIENTO POR LOTES ===");
            System.out.println(" 21. Procesar archivo CSV de inscripciones");
            System.out.println("=== INFORMACION ===");
            System.out.println(" 22. Listar facultades");
            System.out.println("=== SALIR ===");
            System.out.println("  0. Salir");
            System.out.print("\nSeleccione: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());

                switch (opcion) {

                    // â”€â”€ 1. Registrar estudiante â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 1: {
                        System.out.println("\n--- REGISTRO DE ESTUDIANTE ---");
                        System.out.print("ID     : "); String id  = sc.nextLine().trim();
                        System.out.print("Nombre : "); String nom = sc.nextLine().trim();
                        System.out.print("Email  : "); String em  = sc.nextLine().trim();
                        System.out.print("Semestre actual: ");
                        int sem = Integer.parseInt(sc.nextLine().trim());
                        manager.registrarEstudiante(new Estudiante(nom, id, em, sem));
                        break;
                    }

                    // â”€â”€ 2. Buscar estudiante â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 2: {
                        System.out.println("\n--- BUSCAR ESTUDIANTE ---");
                        System.out.print("ID: ");
                        String idBusq = sc.nextLine().trim();
                        Estudiante eB = manager.buscarEstudiante(idBusq);
                        eB.mostrarInformacion();
                        System.out.println("Semestre: " + eB.getSemestre());
                        break;
                    }

                    // â”€â”€ 3. Listar estudiantes â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 3: {
                        manager.listarEstudiantes();
                        break;
                    }

                    // â”€â”€ 4. Eliminar estudiante â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 4: {
                        System.out.println("\n--- ELIMINAR ESTUDIANTE ---");
                        System.out.print("ID a eliminar: ");
                        String idElim = sc.nextLine().trim();
                        manager.eliminarEstudiante(idElim);
                        break;
                    }

                    // â”€â”€ 5. Crear materia â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 5: {
                        System.out.println("\n--- CREAR MATERIA ---");
                        System.out.print("Codigo   : "); String cod  = sc.nextLine().trim();
                        System.out.print("Nombre   : "); String nomM = sc.nextLine().trim();
                        System.out.print("Cupos    : ");
                        int cupos = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Creditos : ");
                        int cred = Integer.parseInt(sc.nextLine().trim());
                        manager.registrarMateria(new Materia(cod, nomM, cupos, cred));
                        break;
                    }

                    // â”€â”€ 6. Agregar pre-requisito â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 6: {
                        System.out.println("\n--- AGREGAR PRE-REQUISITO ---");
                        System.out.print("Codigo de la materia   : ");
                        String codM = sc.nextLine().trim();
                        System.out.print("Codigo del pre-requisito: ");
                        String codR = sc.nextLine().trim();
                        manager.agregarPreRequisito(codM, codR);
                        break;
                    }

                    // â”€â”€ 7. Mostrar pre-requisitos â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 7: {
                        System.out.println("\n--- PRE-REQUISITOS ---");
                        System.out.print("Codigo de la materia: ");
                        String codPR = sc.nextLine().trim();
                        manager.mostrarPreRequisitos(codPR);
                        break;
                    }

                    // â”€â”€ 8. Inscribir estudiante â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 8: {
                        System.out.println("\n--- INSCRIBIR ESTUDIANTE ---");
                        System.out.print("ID Estudiante  : ");
                        String idE = sc.nextLine().trim();
                        System.out.print("Codigo Materia : ");
                        String cM  = sc.nextLine().trim();
                        manager.inscribirEstudianteEnMateria(idE, cM);
                        break;
                    }

                    // â”€â”€ 9. Cancelar inscripcion â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 9: {
                        System.out.println("\n--- CANCELAR INSCRIPCION ---");
                        System.out.print("ID Estudiante  : ");
                        String idC = sc.nextLine().trim();
                        System.out.print("Codigo Materia : ");
                        String cMC = sc.nextLine().trim();
                        manager.cancelarInscripcion(idC, cMC);
                        break;
                    }

                    // â”€â”€ 10. Mostrar cola de espera â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 10: {
                        System.out.println("\n--- COLA DE ESPERA ---");
                        System.out.print("Codigo Materia: ");
                        String codCola = sc.nextLine().trim();
                        manager.mostrarColaEspera(codCola);
                        break;
                    }

                    // â”€â”€ 11. Reservar horario â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 11: {
                        System.out.println("\n--- RESERVAR HORARIO EN AULA ---");
                        System.out.print("Nombre del aula     : ");
                        String nomAula = sc.nextLine().trim();
                        System.out.print("Capacidad del aula  : ");
                        int capAula = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Dia (0=Dom 1=Lun ... 6=Sab): ");
                        int dia = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Hora inicio (0-23)  : ");
                        int hora = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Duracion en horas   : ");
                        int dur = Integer.parseInt(sc.nextLine().trim());

                        Aula aula = new Aula(nomAula, capAula);
                        aula.reservar(dia, hora, dur);
                        break;
                    }

                    // â”€â”€ 12. Liberar horario â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 12: {
                        System.out.println("\n--- LIBERAR HORARIO ---");
                        System.out.print("Nombre del aula: ");
                        String nomLiberar = sc.nextLine().trim();
                        System.out.print("Dia (0-6)      : ");
                        int diaLib = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Hora inicio    : ");
                        int horaLib = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Duracion       : ");
                        int durLib = Integer.parseInt(sc.nextLine().trim());
                        // En un sistema completo el aula vivirÃ­a en un mapa;
                        // aquÃ­ demostramos el uso de la clase con datos de entrada.
                        Aula aulaLib = new Aula(nomLiberar, 0);
                        aulaLib.liberar(diaLib, horaLib, durLib);
                        break;
                    }

                    // â”€â”€ 13. Consultar disponibilidad â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 13: {
                        System.out.println("\n--- CONSULTAR DISPONIBILIDAD ---");
                        System.out.print("Nombre del aula : ");
                        String nomCons = sc.nextLine().trim();
                        System.out.print("Dia (0-6)       : ");
                        int diaCons = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Hora (0-23)     : ");
                        int horaCons = Integer.parseInt(sc.nextLine().trim());
                        Aula aulaCons = new Aula(nomCons, 0);
                        boolean libre = aulaCons.consultarDisponibilidad(diaCons, horaCons);
                        System.out.println("El aula " + nomCons + " el dia " + diaCons
                                           + " a las " + horaCons + ":00 esta: "
                                           + (libre ? "LIBRE" : "OCUPADA"));
                        break;
                    }

                    // â”€â”€ 14. Agregar conexion entre edificios â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 14: {
                        System.out.println("\n--- AGREGAR CONEXION ---");
                        navegador.listarEdificios();
                        System.out.print("Edificio origen  : ");
                        String ed1 = sc.nextLine().trim();
                        System.out.print("Edificio destino : ");
                        String ed2 = sc.nextLine().trim();
                        System.out.print("Distancia (m)    : ");
                        int dist = Integer.parseInt(sc.nextLine().trim());
                        navegador.agregarConexion(ed1, ed2, dist);
                        break;
                    }

                    // â”€â”€ 15. Calcular ruta mas corta (Dijkstra) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 15: {
                        System.out.println("\n--- CALCULAR RUTA MAS CORTA ---");
                        navegador.listarEdificios();
                        System.out.print("Origen  : ");
                        String org = sc.nextLine().trim();
                        System.out.print("Destino : ");
                        String des = sc.nextLine().trim();
                        navegador.calcularRutaMasCorta(org, des);
                        break;
                    }

                    // â”€â”€ 16. Registrar nota â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 16: {
                        System.out.println("\n--- REGISTRAR NOTA ---");
                        System.out.print("ID Estudiante   : ");
                        String idNota = sc.nextLine().trim();
                        System.out.print("Semestre (1-10) : ");
                        int semNota = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Codigo Materia  : ");
                        String codNota = sc.nextLine().trim();
                        System.out.print("Nota (0.0-5.0)  : ");
                        double nota = Double.parseDouble(sc.nextLine().trim());
                        manager.registrarNota(idNota, semNota, codNota, nota);
                        break;
                    }

                    // â”€â”€ 17. Ver reporte academico â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 17: {
                        System.out.println("\n--- REPORTE ACADEMICO ---");
                        System.out.print("ID Estudiante: ");
                        String idR = sc.nextLine().trim();
                        Estudiante estRep = manager.buscarEstudiante(idR);
                        generador.generarReporteNotas(estRep);
                        break;
                    }

                    // â”€â”€ 18. Navegador de reportes (atras) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 18: {
                        generador.mostrarReporteAnterior();
                        break;
                    }

                    // â”€â”€ 19. Deshacer â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 19: {
                        manager.deshacerUltimaOperacion();
                        break;
                    }

                    // â”€â”€ 20. Rehacer â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 20: {
                        manager.rehacerUltimaOperacion();
                        break;
                    }

                    // â”€â”€ 21. Procesar CSV batch â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 21: {
                        System.out.print("Ruta del archivo CSV (ej: inscripciones.csv): ");
                        String archivo = sc.nextLine().trim();
                        LectorCSV.procesarInscripcionesBatch(
                            NavegadorRutas.getRutaData(archivo), manager);
                        break;
                    }

                    // â”€â”€ 22. Listar facultades (arreglo Facultad[5]) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 22: {
                        Facultad.listarFacultades();
                        break;
                    }

                    // â”€â”€ 0. Salir â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                    case 0: {
                        System.out.println("\nCerrando sistema. Â¡Hasta luego!");
                        break;
                    }

                    default:
                        System.out.println("Opcion no valida. Intente de nuevo.");
                }

            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un numero valido.");
            } catch (EstudianteNoEncontradoException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (CupoLlenoException e) {
                System.err.println("Aviso: " + e.getMessage());
            } catch (PreRequisitoNoAprobadoException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (HorarioConflictivoException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ColaDeEsperaVaciaException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (PilaDeshacerVaciaException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (ArchivoInvalidoException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);

        sc.close();
    }
}
