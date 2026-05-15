package PROYECTO_FINAL.Servicios;

import PROYECTO_FINAL.Modelos.Estudiante;
import PROYECTO_FINAL.estructuras.PilaHistorial;

public class GeneradorReportes {
    
    private PilaHistorial<String> historialReportes;

    public GeneradorReportes() {
        this.historialReportes = new PilaHistorial<>();
    }

    /**
     * Genera un reporte detallado recorriendo las matrices del estudiante
     * y lo guarda en la pila de historial.
     */
    public void generarReporteNotas(Estudiante est) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("   REPORTE ACADÉMICO - UCC\n");
        sb.append("========================================\n");
        sb.append("Estudiante: ").append(est.getNombre()).append("\n");
        sb.append("ID: ").append(est.getId()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append(String.format("%-15s | %-10s\n", "MATERIA", "NOTA"));
        sb.append("----------------------------------------\n");

        double[][] notas = est.getNotas();
        String[][] nombres = est.getNombresMaterias();
        int[] conteo = est.getConteoMaterias();

        // Recorremos los 10 semestres
        for (int i = 0; i < 10; i++) {
            if (conteo[i] > 0) { // Si el semestre tiene materias
                sb.append("Semestre ").append(i + 1).append(":\n");
                for (int j = 0; j < conteo[i]; j++) {
                    sb.append(String.format("%-15s | %-10.2f\n", nombres[i][j], notas[i][j]));
                }
                sb.append("----------------------------------------\n");
            }
        }

        String reporteFinal = sb.toString();
        System.out.println(reporteFinal);
        
        // Guardamos en la pila para navegación
        historialReportes.apilar(reporteFinal);
    }

    /**
     * Permite regresar al reporte anterior sin perderlo de la pila si se desea.
     */
    public void mostrarReporteAnterior() {
        if (historialReportes.getTamaño() > 1) {
            historialReportes.desapilar(); // Quitamos el actual
            String anterior = historialReportes.verCima(); // Miramos el que quedó arriba
            System.out.println("<<< MOSTRANDO REPORTE ANTERIOR >>>\n" + anterior);
        } else {
            System.out.println("Aviso: No hay más reportes en el historial.");
        }
    }
}