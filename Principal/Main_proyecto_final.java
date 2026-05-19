package PROYECTO_FINAL.Principal;
import PROYECTO_FINAL.Servicios.InscripcionManager;
import PROYECTO_FINAL.Servicios.GeneradorReportes;
import PROYECTO_FINAL.Modelos.*;
import PROYECTO_FINAL.Util.NavegadorRutas;
import PROYECTO_FINAL.Util.LectorCSV;
import PROYECTO_FINAL.excepciones.*;

import java.util.Scanner;

/**
 * Clase principal del Sistema de Gestion Academica.
 * Contiene el menu interactivo con las 22 opciones requeridas por el PDF.
 */
public class Main_proyecto_final {

    public static void main(String[] args) {

        // Inicializacion de servicios 
        InscripcionManager manager  = new InscripcionManager();
        GeneradorReportes  generador = new GeneradorReportes();
        NavegadorRutas     navegador = new NavegadorRutas(10);
        Scanner sc = new Scanner(System.in);

        // Edificios iniciales del campus (m­inimo 5 requerido por el PDF)
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

        //Bucle principal del menu
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

                    // 1. Registrar estudiante 
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

                    //2. Buscar estudiante 
                    case 2: {
                        System.out.println("\n--- BUSCAR ESTUDIANTE ---");
                        System.out.print("ID: ");
                        String idBusq = sc.nextLine().trim();
                        Estudiante eB = manager.buscarEstudiante(idBusq);
                        eB.mostrarInformacion();
                        System.out.println("Semestre: " + eB.getSemestre());
                        break;
                    }

                    // 3. Listar estudiantes 
                    case 3: {
                        manager.listarEstudiantes();
                        break;
                    }

                    //4. Eliminar estudiante
                    case 4: {
                        System.out.println("\n--- ELIMINAR ESTUDIANTE ---");
                        System.out.print("ID a eliminar: ");
                        String idElim = sc.nextLine().trim();
                        manager.eliminarEstudiante(idElim);
                        break;
                    }

                    //5. Crear materia
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

                    //6. Agregar pre-requisito
                    case 6: {
                        System.out.println("\n--- AGREGAR PRE-REQUISITO ---");
                        System.out.print("Codigo de la materia   : ");
                        String codM = sc.nextLine().trim();
                        System.out.print("Codigo del pre-requisito: ");
                        String codR = sc.nextLine().trim();
                        manager.agregarPreRequisito(codM, codR);
                        break;
                    }

                    //7. Mostrar pre-requisitos
                    case 7: {
                        System.out.println("\n--- PRE-REQUISITOS ---");
                        System.out.print("Codigo de la materia: ");
                        String codPR = sc.nextLine().trim();
                        manager.mostrarPreRequisitos(codPR);
                        break;
                    }

                    //8. Inscribir estudiante
                    case 8: {
                        System.out.println("\n--- INSCRIBIR ESTUDIANTE ---");
                        System.out.print("ID Estudiante  : ");
                        String idE = sc.nextLine().trim();
                        System.out.print("Codigo Materia : ");
                        String cM  = sc.nextLine().trim();
                        manager.inscribirEstudianteEnMateria(idE, cM);
                        break;
                    }

                    //9. Cancelar inscripcion 
                    case 9: {
                        System.out.println("\n--- CANCELAR INSCRIPCION ---");
                        System.out.print("ID Estudiante  : ");
                        String idC = sc.nextLine().trim();
                        System.out.print("Codigo Materia : ");
                        String cMC = sc.nextLine().trim();
                        manager.cancelarInscripcion(idC, cMC);
                        break;
                    }

                    //10. Mostrar cola de espera 
                    case 10: {
                        System.out.println("\n--- COLA DE ESPERA ---");
                        System.out.print("Codigo Materia: ");
                        String codCola = sc.nextLine().trim();
                        manager.mostrarColaEspera(codCola);
                        break;
                    }

                    //11. Reservar horario
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

                    //12. Liberar horario 
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

                    //13. Consultar disponibilidad 
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

                    //14. Agregar conexion entre edificios
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

                    //15. Calcular ruta mas corta (Dijkstra) 
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

                    //16. Registrar nota 
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

                    //17. Ver reporte academico
                    case 17: {
                        System.out.println("\n--- REPORTE ACADEMICO ---");
                        System.out.print("ID Estudiante: ");
                        String idR = sc.nextLine().trim();
                        Estudiante estRep = manager.buscarEstudiante(idR);
                        generador.generarReporteNotas(estRep);
                        break;
                    }

                    //18. Navegador de reportes (atras)
                    case 18: {
                        generador.mostrarReporteAnterior();
                        break;
                    }

                    //19. Deshacer
                    case 19: {
                        manager.deshacerUltimaOperacion();
                        break;
                    }

                    //20. Rehacer 
                    case 20: {
                        manager.rehacerUltimaOperacion();
                        break;
                    }

                    //21. Procesar CSV batch 
                    case 21: {
                        System.out.print("Ruta del archivo CSV (ej: inscripciones.csv): ");
                        String archivo = sc.nextLine().trim();
                        LectorCSV.procesarInscripcionesBatch(
                            NavegadorRutas.getRutaData(archivo), manager);
                        break;
                    }

                    //22. Listar facultades (arreglo Facultad[5]) 
                    case 22: {
                        Facultad.listarFacultades();
                        break;
                    }

                    //0. Salir 
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
