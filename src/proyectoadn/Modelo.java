/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import logic.MiArbolBinarioBusqueda;

public class Modelo {

    /**
     * Tabla hash utilizada para almacenar y buscar patrones de ADN por su secuencia
     * (clave), permitiendo un acceso rápido a la información de cada patrón.
     */
    private MiHashTable tablaPatronesADN;

    /**
     * Árbol binario de búsqueda utilizado para almacenar los patrones de ADN
     * y mantenerlos ordenados por su frecuencia de aparición.
     * Facilita la obtención de patrones en orden de frecuencia y la identificación
     * de los más y menos frecuentes.
     */
    private MiArbolBinarioBusqueda arbolFrecuenciaPatrones;

    /**
     * Objeto encargado de gestionar la tabla del código genético,
     * permitiendo la conversión de secuencias de ADN a codones de ARN
     * y el mapeo de estos codones a sus respectivos aminoácidos.
     */
    private MapeadorCodones mapeadorCodones;

    /**
     * Almacena la secuencia completa de ADN cargada desde un archivo,
     * la cual será procesada para extraer patrones.
     */
    private String secuenciaPrincipalADN;

    /**
     * Constructor de la clase Modelo.
     * Inicializa las estructuras de datos principales:
     * una nueva tabla hash para patrones de ADN,
     * un nuevo árbol binario de búsqueda para la frecuencia de patrones,
     * y un mapeador de codones para la traducción genética.
     */
    public Modelo() {
        this.tablaPatronesADN = new MiHashTable(101); // Se elige 101 como tamaño inicial para la tabla hash (número primo).
        this.arbolFrecuenciaPatrones = new MiArbolBinarioBusqueda();
        this.mapeadorCodones = new MapeadorCodones();
        this.secuenciaPrincipalADN = "";
    }

    /**
     * Carga una secuencia principal de ADN desde un archivo de texto plano.
     * Lee todo el contenido del archivo, lo concatena, elimina espacios en blanco,
     * convierte a mayúsculas y lo almacena.
     * Después de cargar, invoca el método {@code procesarSecuenciaADN()}
     * para analizar los patrones dentro de la secuencia cargada.
     *
     * @param archivo El objeto {@link File} que representa el archivo .txt a cargar.
     * @throws IOException Si ocurre un error durante la lectura del archivo.
     */
    public void cargarSecuenciaADN(File archivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea.trim().toUpperCase());
            }
            this.secuenciaPrincipalADN = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        procesarSecuenciaADN();
    }

    /**
     * Procesa la secuencia de ADN principal para extraer patrones de longitud 3
     * y los almacena en la tabla hash. Cada patrón se introduce como clave
     * y un objeto {@link PatronADN} como valor, registrando su frecuencia
     * y sus ubicaciones dentro de la secuencia.
     * Si un patrón ya existe, se actualiza su frecuencia y ubicaciones.
     * Finalmente, todos los patrones únicos se transfieren al árbol binario de búsqueda
     * para su ordenamiento por frecuencia.
     * <p>
     * Nota: La tabla hash y el árbol binario de búsqueda se reinician antes de cada procesamiento
     * para manejar nuevas cargas de secuencia.
     * </p>
     */
    private void procesarSecuenciaADN() {
        if (secuenciaPrincipalADN.length() < 3) {
            System.out.println("La secuencia de ADN es demasiado corta para extraer patrones.");
            return;
        }

        // Reinicia las estructuras para un nuevo procesamiento
        this.tablaPatronesADN = new MiHashTable(101);
        this.arbolFrecuenciaPatrones = new MiArbolBinarioBusqueda();

       
        for (int i = 0; i <= secuenciaPrincipalADN.length() - 3; i += 3) {
            String secuenciaFragmento = secuenciaPrincipalADN.substring(i, i + 3);

            // Valida que el fragmento contenga solo bases de ADN (A, T, C, G)
            if (secuenciaFragmento.matches("[ATCG]+")) {
                // Busca si el patrón ya existe en la tabla hash
                PatronADN patronExistente = (PatronADN) tablaPatronesADN.buscar(secuenciaFragmento);

                if (patronExistente != null) {
                   
                    patronExistente.incrementarFrecuencia();
                    patronExistente.agregarUbicacion(i);
                } else {
                    
                    PatronADN nuevoPatron = new PatronADN(secuenciaFragmento);
                    nuevoPatron.incrementarFrecuencia(); 
                    nuevoPatron.agregarUbicacion(i);
                    tablaPatronesADN.insertar(secuenciaFragmento, nuevoPatron);
                }
            }
        }

       
        transferirPatronesATree();
    }

    /**
     * Transfiere todos los objetos {@link PatronADN} desde la {@link MiHashTable}
     * al {@link MiArbolBinarioBusqueda}. Esto asegura que el árbol se construya
     * con las frecuencias finales de cada patrón, permitiendo que el árbol
     * mantenga su propiedad de ordenación por frecuencia.
     * Se invoca después de que todos los patrones de ADN han sido extraídos y contabilizados.
     */
    private void transferirPatronesATree() {
       
        Object[] todosLosPatrones = tablaPatronesADN.getTodosLosValores();

        if (todosLosPatrones != null) {
           
            for (Object obj : todosLosPatrones) {
                if (obj instanceof PatronADN) {
                    arbolFrecuenciaPatrones.insertar((PatronADN) obj);
                }
            }
        }
    }

    /**
     * Obtiene una lista de todos los patrones de ADN únicos, ordenados
     * por su frecuencia de aparición (de mayor a menor).
     * Esta información se recupera del árbol binario de búsqueda.
     * La complejidad esperada es O(N) donde N es la cantidad de patrones únicos,
     * debido al recorrido del árbol.
     *
     * @return Un arreglo de objetos {@link PatronADN} ordenados por frecuencia.
     */
    public PatronADN[] getPatronesOrdenadosPorFrecuencia() {
        return arbolFrecuenciaPatrones.obtenerPatronesOrdenados();
    }

    /**
     * Busca la información de un patrón de ADN específico por su secuencia.
     * Esta operación se realiza utilizando la tabla hash, lo que garantiza
     * una complejidad de tiempo promedio de O(1).
     *
     * @param secuencia La secuencia de ADN (String) del patrón a buscar.
     * @return El objeto {@link PatronADN} si se encuentra, o {@code null} si no existe.
     */
    public PatronADN buscarPatron(String secuencia) {
        return (PatronADN) tablaPatronesADN.buscar(secuencia.toUpperCase());
    }

    /**
     * Identifica y retorna el patrón de ADN con la mayor frecuencia de aparición.
     * Esta operación aprovecha las propiedades del árbol binario de búsqueda
     * y tiene una complejidad de tiempo de O(log n) en el caso promedio.
     *
     * @return El objeto {@link PatronADN} que representa el patrón más frecuente.
     */
    public PatronADN getPatronMasFrecuente() {
        return arbolFrecuenciaPatrones.obtenerPatronMasFrecuente();
    }

    /**
     * Identifica y retorna el patrón de ADN con la menor frecuencia de aparición.
     * Esta operación aprovecha las propiedades del árbol binario de búsqueda
     * y tiene una complejidad de tiempo de O(log n) en el caso promedio.
     *
     * @return El objeto {@link PatronADN} que representa el patrón menos frecuente.
     */
    public PatronADN getPatronMenosFrecuente() {
        return arbolFrecuenciaPatrones.obtenerPatronMenosFrecuente();
    }

    /**
     * Genera un reporte detallado sobre las colisiones ocurridas en la
     * {@link MiHashTable} utilizada para almacenar los patrones de ADN.
     * Este reporte incluye información sobre la cantidad de colisiones
     * por celda y un resumen global.
     *
     * @return Una cadena de texto con el reporte de colisiones.
     */
    public String generarReporteColisiones() {
        return tablaPatronesADN.getReporteColisiones();
    }

    /**
     * Genera un reporte completo sobre los aminoácidos, indicando las tripletas de ADN
     * que los generan (convertidas a codones de ARN) y la frecuencia total de aparición
     * de estos aminoácidos en la secuencia principal de ADN.
     * Utiliza el {@link MapeadorCodones} para la traducción y una tabla hash auxiliar
     * para acumular las frecuencias por aminoácido.
     *
     * @return Una cadena de texto con el reporte de aminoácidos y sus frecuencias/codones asociados.
     */
    public String generarReporteAminoacidos() {
        Object[] todosLosPatrones = tablaPatronesADN.getTodosLosValores();

        if (todosLosPatrones == null || todosLosPatrones.length == 0) {
            return "No se han procesado patrones de ADN.";
        }

       
        MiHashTable aminoacidosFrecuencia = new MiHashTable(50);

        for (Object obj : todosLosPatrones) {
            if (obj instanceof PatronADN) {
                PatronADN patron = (PatronADN) obj;
                String codonARN = mapeadorCodones.convertirADN_a_ARN(patron.getSecuencia());
                Aminoacido amino = mapeadorCodones.getAminoacido(codonARN); 

                if (amino != null) {
                    String claveAmino = amino.getNombreCompleto();

                    
                    Aminoacido acumuladorAmino = (Aminoacido) aminoacidosFrecuencia.buscar(claveAmino);

                    if (acumuladorAmino != null) {
                       
                        acumuladorAmino.incrementarFrecuenciaGlobal(patron.getFrecuencia());
                        acumuladorAmino.agregarCodonAsociado(codonARN);
                    } else {
                        
                        Aminoacido nuevoAcumuladorAmino = new Aminoacido(amino.getNombreCompleto(), amino.getAbreviatura3Letras(), amino.getAbreviatura1Letra());
                        nuevoAcumuladorAmino.incrementarFrecuenciaGlobal(patron.getFrecuencia()); // Suma la frecuencia del primer patrón
                        nuevoAcumuladorAmino.agregarCodonAsociado(codonARN);
                        aminoacidosFrecuencia.insertar(claveAmino, nuevoAcumuladorAmino);
                    }
                }
            }
        }

        StringBuilder reporte = new StringBuilder("Reporte de Aminoácidos:\n");
        reporte.append("----------------------------------------------------------------\n");

        Object[] listaAminoacidosAcumulados = aminoacidosFrecuencia.getTodosLosValores();

        
        if (listaAminoacidosAcumulados != null) {
            for (Object obj : listaAminoacidosAcumulados) {
                if (obj instanceof Aminoacido) {
                    Aminoacido amino = (Aminoacido) obj;
                    reporte.append("Aminoácido: ").append(amino.getNombreCompleto())
                            .append(" (").append(amino.getAbreviatura3Letras()).append(" / ")
                            .append(amino.getAbreviatura1Letra()).append(")\n");
                    reporte.append("  Frecuencia Total: ").append(amino.getFrecuenciaGlobal()).append("\n");
                    reporte.append("  Codones asociados (ARN): ");
                    String[] codones = amino.getTodosLosCodonesAsociados();
                    if (codones != null) {
                        for (int i = 0; i < codones.length; i++) {
                            reporte.append(codones[i]);
                            if (i < codones.length - 1) {
                                reporte.append(", ");
                            }
                        }
                    }
                    reporte.append("\n----------------------------------------------------------------\n");
                }
            }
        } else {
            reporte.append("No se encontraron aminoácidos para reportar.\n");
        }

        return reporte.toString();
    }
}