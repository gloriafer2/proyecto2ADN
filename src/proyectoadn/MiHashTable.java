/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */
public class MiHashTable {
    private HashEntry[] tabla; 
    private int capacidad; 
    private int tamanoActual; 
    private int colisionesRegistradas; 
    private String[] reporteColisionesDetallado; 
    private int numReportesColisionDetallado; 

    /**
     * Constructor de MiHashTable.
     * @param capacidadInicial La capacidad inicial de la tabla hash.
     */
    public MiHashTable(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.tabla = new HashEntry[capacidad]; 
        this.tamanoActual = 0;
        this.colisionesRegistradas = 0;
        this.reporteColisionesDetallado = new String[50]; 
        this.numReportesColisionDetallado = 0;
    }

    /**
     * Calcula el índice (la posición) en la tabla hash para una clave (secuencia de ADN) dada.
     * Usamos el número de "carnet" (hashCode) de la secuencia y el operador % (módulo)
     * para asegurarnos de que el índice esté dentro de los límites de nuestra tabla.
     * @param clave La secuencia del patrón (String) para la cual calcular el índice.
     * @return El índice calculado en la tabla.
     */
    private int calcularIndice(String clave) {
        
        return (clave.hashCode() & 0x7fffffff) % capacidad;
    }

    /**
     * Inserta o actualiza un PatronADN en la tabla hash.
     * Si el patrón ya existe, incrementa su frecuencia y agrega la ubicación.
     * Si no existe, lo añade como un nuevo PatronADN.
     * También registra las colisiones.
     * @param nuevoPatron El objeto PatronADN a insertar/actualizar.
     * @param ubicacion La ubicación en la secuencia principal de ADN.
     * @return true si se añadió un nuevo patrón, false si se actualizó uno existente.
     */
    public boolean insertarOActualizar(PatronADN nuevoPatron, int ubicacion) {
        int indice = calcularIndice(nuevoPatron.getSecuencia()); 
        HashEntry cabeza = tabla[indice]; 

        HashEntry actual = cabeza;
        
        while (actual != null) {
            if (actual.patron.getSecuencia().equals(nuevoPatron.getSecuencia())) {
                
               
                actual.patron.incrementarFrecuencia();
                actual.patron.agregarUbicacion(ubicacion);
                return false; 
            }
            actual = actual.siguiente; 
        }

        
        HashEntry nuevoEntry = new HashEntry(nuevoPatron); 
        nuevoEntry.siguiente = cabeza; 
        tabla[indice] = nuevoEntry; 
        tamanoActual++; 

        if (cabeza != null) {
            colisionesRegistradas++; 

         
            if (numReportesColisionDetallado == reporteColisionesDetallado.length) {
                String[] nuevoReporte = new String[reporteColisionesDetallado.length * 2];
                for (int i = 0; i < reporteColisionesDetallado.length; i++) {
                    nuevoReporte[i] = reporteColisionesDetallado[i];
                }
                reporteColisionesDetallado = nuevoReporte;
            }
            reporteColisionesDetallado[numReportesColisionDetallado++] =
                "Colisión en índice " + indice + ": Nuevo patrón '" + nuevoPatron.getSecuencia() +
                "' colisionó con el patrón existente '" + cabeza.patron.getSecuencia() + "'";
        }
        return true;
    }

    /**
     * Busca un PatronADN en la tabla hash por su secuencia (clave).
     * @param secuencia La secuencia (clave) del patrón a buscar.
     * @return El objeto PatronADN si se encuentra, o null si no existe.
     * La complejidad promedio de esta operación es O(1) (muy rápida).
     */
    public PatronADN buscar(String secuencia) {
        int indice = calcularIndice(secuencia); 
        HashEntry actual = tabla[indice]; 

             while (actual != null) {
            if (actual.patron.getSecuencia().equals(secuencia)) {
                return actual.patron; 
            }
            actual = actual.siguiente; 
        }
        return null; 
    }

    /**
     * Obtiene todos los PatronADN distintos almacenados en la tabla hash.
     * @return Un array de PatronADN que contiene todos los patrones distintos.
     */
    public PatronADN[] obtenerTodosLosPatrones() {
      
        PatronADN[] todosLosPatrones = new PatronADN[tamanoActual];
        int contador = 0; 

       
        for (int i = 0; i < capacidad; i++) {
            HashEntry actual = tabla[i]; 
            while (actual != null) {
                if (contador < tamanoActual) { 
                    todosLosPatrones[contador++] = actual.patron;
                }
                actual = actual.siguiente; 
            }
        }
        return todosLosPatrones; 
    }

    /**
     * Obtiene el número total de colisiones registradas.
     * @return El número de colisiones.
     */
    public int getColisionesRegistradas() {
        return colisionesRegistradas;
    }

    /**
     * Genera un reporte detallado de las colisiones ocurridas.
     * Indica los patrones que, siendo tripletas distintas, generaron colisiones.
     * @return Una cadena con el reporte de colisiones.
     */
    public String obtenerReporteColisionesDetallado() {
        StringBuilder sb = new StringBuilder(); 
        if (colisionesRegistradas == 0) {
            sb.append("No se registraron colisiones.");
        } else {
            sb.append("--- Reporte Detallado de Colisiones ---\n");
          
            for (int i = 0; i < numReportesColisionDetallado; i++) {
                sb.append(reporteColisionesDetallado[i]).append("\n"); 
            }
            sb.append("Total de colisiones registradas: ").append(colisionesRegistradas);
        }
        return sb.toString();
    }
}
