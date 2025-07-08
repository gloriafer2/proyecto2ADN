/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyectoadn;

public class Aminoacido {
    
    private String nombreCompleto;
  
    private String abreviatura3Letras;
   
    private char abreviatura1Letra;
    
    /**
     * Define el tipo de aminoácido: "Inicio" para Metionina, "Parada" para STOP,
     * o "Aminoacido Valido" para los demás.
     */
    private String tipo;

    /**
     * Arreglo dinámico que almacena los codones de ARN (secuencias de 3 bases)
     * que codifican para este aminoácido.
     */
    private String[] codonesAsociados;
    /**
     * Cantidad actual de codones almacenados en el arreglo `codonesAsociados`.
     */
    private int numCodones;

    /**
     * Frecuencia total acumulada de este aminoácido, calculada a partir de
     * la suma de las frecuencias de todos los patrones de ADN que lo codifican.
     */
    private int frecuenciaGlobal;

    /**
     * Constructor para crear una nueva instancia de `Aminoacido`.
     * Inicializa las propiedades básicas del aminoácido y determina su tipo.
     * Prepara el arreglo de codones asociados con una capacidad inicial y
     * establece la frecuencia global a cero.
     *
     * @param nombreCompleto El nombre completo del aminoácido.
     * @param abreviatura3Letras La abreviatura de tres letras del aminoácido.
     * @param abreviatura1Letra La abreviatura de una letra del aminoácido.
     */
    public Aminoacido(String nombreCompleto, String abreviatura3Letras, char abreviatura1Letra) {
        this.nombreCompleto = nombreCompleto;
        this.abreviatura3Letras = abreviatura3Letras;
        this.abreviatura1Letra = abreviatura1Letra;
        
        if (nombreCompleto.equals("STOP")) {
            this.tipo = "Parada";
        } else if (nombreCompleto.equals("Metionina")) {
            this.tipo = "Inicio";
        } else {
            this.tipo = "Aminoacido Valido";
        }

       
        this.codonesAsociados = new String[5];
        this.numCodones = 0;
        this.frecuenciaGlobal = 0;
    }

    /**
     * Agrega un codón de ARN a la lista de codones asociados con este aminoácido.
     * Antes de agregar, verifica si el codón ya existe para evitar duplicados.
     * Si el arreglo de codones alcanza su capacidad máxima, se redimensiona
     * automáticamente al doble de su tamaño actual.
     *
     * @param codon La secuencia de 3 bases de ARN (String) a asociar con el aminoácido.
     */
    public void agregarCodonAsociado(String codon) {
        for (int i = 0; i < numCodones; i++) {
            if (codonesAsociados[i] != null && codonesAsociados[i].equals(codon)) {
                return; 
            }
        }

       
        if (numCodones == codonesAsociados.length) {
            String[] nuevoArray = new String[codonesAsociados.length * 2];
            System.arraycopy(codonesAsociados, 0, nuevoArray, 0, codonesAsociados.length);
            codonesAsociados = nuevoArray;
        }
       
        codonesAsociados[numCodones++] = codon;
    }

    /**
     * Obtiene el nombre completo del aminoácido.
     * @return El nombre completo del aminoácido.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Obtiene la abreviatura de tres letras del aminoácido.
     * @return La abreviatura de tres letras del aminoácido.
     */
    public String getAbreviatura3Letras() {
        return abreviatura3Letras;
    }

    /**
     * Obtiene la abreviatura de una letra del aminoácido.
     * @return La abreviatura de una letra del aminoácido.
     */
    public char getAbreviatura1Letra() {
        return abreviatura1Letra;
    }

    /**
     * Obtiene el tipo de aminoácido (ej. "Inicio", "Parada", "Aminoacido Valido").
     * @return El tipo de aminoácido.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna una copia de los codones de ARN asociados con este aminoácido.
     * El arreglo retornado contendrá solo los codones que han sido agregados,
     * sin espacios nulos extra del arreglo interno.
     * @return Un arreglo de Strings que contiene todos los codones de ARN asociados.
     */
    public String[] getTodosLosCodonesAsociados() {
     
        String[] result = new String[numCodones];
        
        System.arraycopy(codonesAsociados, 0, result, 0, numCodones);
        return result;
    }

    /**
     * Obtiene el número de codones de ARN actualmente asociados con este aminoácido.
     * @return El número de codones asociados.
     */
    public int getNumCodones() {
        return numCodones;
    }
    
    /**
     * Incrementa la frecuencia global de este aminoácido por una cantidad específica.
     * Esto se utiliza para acumular la frecuencia de aparición de aminoácidos
     * basándose en la frecuencia de sus patrones de ADN correspondientes.
     *
     * @param cantidad La cantidad por la cual se incrementará la frecuencia global.
     */
    public void incrementarFrecuenciaGlobal(int cantidad) {
        this.frecuenciaGlobal += cantidad;
    }

    /**
     * Obtiene la frecuencia global acumulada de este aminoácido.
     * @return La frecuencia global del aminoácido.
     */
    public int getFrecuenciaGlobal() {
        return frecuenciaGlobal;
    }

    /**
     * Proporciona una representación en cadena de texto de este objeto `Aminoacido`.
     * Incluye el nombre completo, abreviaturas, tipo, frecuencia global y los codones ARN asociados.
     *
     * @return Una cadena formateada que describe el aminoácido.
     */
    @Override
    public String toString() {
        StringBuilder codonesStr = new StringBuilder();
        codonesStr.append("[");
        for (int i = 0; i < numCodones; i++) {
            codonesStr.append(codonesAsociados[i]);
            if (i < numCodones - 1) {
                codonesStr.append(", ");
            }
        }
        codonesStr.append("]");
        return "Aminoacido: " + nombreCompleto + 
               " (Abrev: " + abreviatura3Letras + " / " + abreviatura1Letra + 
               ", Tipo: " + tipo + 
               ", Frecuencia: " + frecuenciaGlobal + 
               "), Codones ARN: " + codonesStr.toString();
    }
}