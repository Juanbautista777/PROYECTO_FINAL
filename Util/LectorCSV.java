package PROYECTO_FINAL.Util;

import PROYECTO_FINAL.Servicios.InscripcionManager;
import PROYECTO_FINAL.estructuras.Cola;
import PROYECTO_FINAL.excepciones.ArchivoInvalidoException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utilidad para procesamiento masivo (batch) de solicitudes de inscripción.
 *
 * Formato del CSV:
 *   idEstudiante,codigoMateria
 *
 * Flujo:
 *   1. Lee el archivo y encola cada solicitud en una Cola<String> (estructura propia).
 *   2. Procesa la cola secuencialmente intentando inscribir a cada estudiante.
 *   3. Al finalizar muestra un resumen: exitosas, fallidas y motivo de cada falla.
 */
public class LectorCSV {

    /**
     * Procesa masivamente solicitudes de inscripción desde un archivo CSV.
     *
     * @param ruta    Ruta absoluta al archivo CSV
     * @param manager Servicio de inscripción del sistema
     * @throws ArchivoInvalidoException si el archivo no existe o su formato es incorrecto
     */
    public static void procesarInscripcionesBatch(String ruta, InscripcionManager manager)
            throws ArchivoInvalidoException {

        // ── Fase 1: leer el CSV y encolar solicitudes ────────────────────────
        Cola<String> colaSolicitudes = new Cola<>();
        int totalLeidas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;   // ignorar líneas vacías

                String[] partes = linea.split(",");
                if (partes.length < 2) {
                    throw new ArchivoInvalidoException(
                        "ArchivoInvalidoException - Linea " + numeroLinea
                        + " mal formateada: \"" + linea + "\"");
                }
                // Encolar como "idEstudiante|codigoMateria"
                colaSolicitudes.encolar(partes[0].trim() + "|" + partes[1].trim());
                totalLeidas++;
            }
        } catch (IOException e) {
            throw new ArchivoInvalidoException(
                "ArchivoInvalidoException - No se pudo leer el archivo: " + ruta);
        }

        System.out.println("\n--- PROCESAMIENTO MASIVO (BATCH) ---");
        System.out.println("Archivo: " + ruta);
        System.out.println("Se encolaron " + totalLeidas + " solicitudes.");
        System.out.println("Procesando cola...\n");

        // ── Fase 2: procesar la cola ─────────────────────────────────────────
        int exitosas  = 0;
        int fallidas  = 0;
        int indice    = 1;

        while (!colaSolicitudes.estaVacia()) {
            String solicitud = colaSolicitudes.desencolar();
            String[] datos   = solicitud.split("\\|");
            String idEst     = datos[0];
            String codMat    = datos[1];

            try {
                manager.inscribirEstudianteEnMateria(idEst, codMat);
                System.out.println("[" + indice + "/" + totalLeidas + "] "
                                   + idEst + " -> " + codMat + " -> Exitosa");
                exitosas++;
            } catch (Exception e) {
                System.out.println("[" + indice + "/" + totalLeidas + "] "
                                   + idEst + " -> " + codMat
                                   + " -> Fallida (" + e.getMessage() + ")");
                fallidas++;
            }
            indice++;
        }

        // ── Fase 3: resumen ──────────────────────────────────────────────────
        System.out.println("\n=== RESUMEN ===");
        System.out.println("Exitosas : " + exitosas);
        System.out.println("Fallidas : " + fallidas);
    }
}