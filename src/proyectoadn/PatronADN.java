/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */
public class PatronADN implements Comparable<PatronADN> {
    private String secuencia;
    /**
     * La frecuencia de aparición de este patrón en la secuencia de ADN analizada.
     */
    private int frecuencia;
    private int[] ubicaciones;
    private int numUbicaciones;

    /**
     * Constructor para crear una nueva instancia de `PatronADN`.
     * Inicializa el patrón con la secuencia dada, establece su frecuencia inicial a 0,
     * y prepara un arreglo para las ubicaciones con una capacidad inicial predefinida.
     *
     * @param secuencia La secuencia de ADN (String) para este patrón.
     */
    public PatronADN(String secuencia) {
        this.secuencia = secuencia;
        this.frecuencia = 0; 
        this.ubicaciones = new int[10]; 
        this.numUbicaciones = 0;
    }
    
    /**
     * Obtiene la secuencia de ADN de este patrón.
     * @return La secuencia de ADN como String.
     */
    public String getSecuencia() {
        return secuencia;
    }

    /**
     * Obtiene la frecuencia de aparición de este patrón.
     * @return La frecuencia del patrón.
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * Obtiene una copia del arreglo de ubicaciones donde se encontró este patrón.
     * Retorna un nuevo arreglo para proteger la integridad del arreglo interno.
     * @return Un arreglo de enteros que contiene las ubicaciones del patrón.
     */
    public int[] getUbicaciones() {
        // Retorna una copia para evitar la modificación externa del arreglo interno.
        int[] copiaUbicaciones = new int[numUbicaciones];
        System.arraycopy(ubicaciones, 0, copiaUbicaciones, 0, numUbicaciones);
        return copiaUbicaciones;
    }

    /**
     * Obtiene el número de ubicaciones registradas para este patrón.
     * @return El número de ubicaciones.
     */
    public int getNumUbicaciones() {
        return numUbicaciones;
    }

    /**
     * Incrementa la frecuencia de este patrón en uno.
     * Este método se llama cada vez que el patrón es encontrado.
     */
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    /**
     * Agrega una nueva ubicación (índice de inicio) donde se encontró este patrón.
     * Si el arreglo de ubicaciones alcanza su capacidad máxima, se redimensiona
     * automáticamente al doble de su tamaño actual para acomodar más ubicaciones.
     *
     * @param ubicacion La posición de inicio (índice) de la secuencia de ADN
     * donde se encontró el patrón.
     */
    public void agregarUbicacion(int ubicacion) {
        // Si el arreglo está lleno, lo redimensiona.
        if (numUbicaciones == ubicaciones.length) {
            int[] nuevoArray = new int[ubicaciones.length * 2];
            System.arraycopy(ubicaciones, 0, nuevoArray, 0, ubicaciones.length);
            ubicaciones = nuevoArray;
        }
        // Agrega la nueva ubicación y actualiza el contador.
        ubicaciones[numUbicaciones++] = ubicacion;
    }

    /**
     * Compara este objeto `PatronADN` con otro para determinar si son iguales.
     * Dos objetos `PatronADN` se consideran iguales si sus secuencias de ADN son idénticas.
     *
     * @param obj El objeto a comparar con este `PatronADN`.
     * @return {@code true} si los objetos son iguales (tienen la misma secuencia), {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; 
        PatronADN other = (PatronADN) obj;
        return secuencia.equals(other.secuencia); 
    }

    /**
     * Calcula un código hash para este `PatronADN`.
     * La implementación del `hashCode` es personalizada y busca una distribución
     * de hash eficiente para secuencias de ADN, especialmente para tripletes.
     * Asigna valores numéricos a las bases (A=1, C=2, G=3, T=4) y calcula un hash
     * polinomial. Para secuencias de longitud 3, utiliza una fórmula optimizada.
     * Para otras longitudes, usa el algoritmo polinomial estándar.
     *
     * @return El valor del código hash para este patrón de ADN.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        int prime = 31;

        int valA = 1, valC = 2, valG = 3, valT = 4;

        if (secuencia.length() == 3) {
            int v1 = 0, v2 = 0, v3 = 0;
            switch (secuencia.charAt(0)) {
                case 'A': v1 = valA; break;
                case 'C': v1 = valC; break;
                case 'G': v1 = valG; break;
                case 'T': v1 = valT; break;
            }
            switch (secuencia.charAt(1)) {
                case 'A': v2 = valA; break;
                case 'C': v2 = valC; break;
                case 'G': v2 = valG; break;
                case 'T': v2 = valT; break;
            }
            switch (secuencia.charAt(2)) {
                case 'A': v3 = valA; break;
                case 'C': v3 = valC; break;
                case 'G': v3 = valG; break;
                case 'T': v3 = valT; break;
            }
       
            hash = (v1 * prime * prime) + (v2 * prime) + v3;
        } else {
            for (int i = 0; i < secuencia.length(); i++) {
                char c = secuencia.charAt(i);
                int charVal = 0;
                switch (c) {
                    case 'A': charVal = valA; break;
                    case 'C': charVal = valC; break;
                    case 'G': charVal = valG; break;
                    case 'T': charVal = valT; break;
                }
                hash = hash * prime + charVal;
            }
        }
        return hash;
    }

    /**
     * Compara este objeto `PatronADN` con otro `PatronADN` para establecer un orden.
     * La comparación se realiza primero por la frecuencia de los patrones en orden descendente
     * (los patrones más frecuentes van primero). Si las frecuencias son iguales,
     * los patrones se ordenan lexicográficamente por su secuencia de ADN en orden ascendente.
     *
     * @param otro El otro objeto `PatronADN` con el que comparar.
     * @return Un valor negativo si este patrón es "menor" que el otro (debe ir antes),
     * un valor positivo si este patrón es "mayor" (debe ir después),
     * o cero si son iguales.
     */
    @Override
    public int compareTo(PatronADN otro) {
      
        int comparacionFrecuencia = Integer.compare(otro.frecuencia, this.frecuencia);
        if (comparacionFrecuencia != 0) {
            return comparacionFrecuencia;
        }
      
        return this.secuencia.compareTo(otro.secuencia);
    }

    /**
     * Proporciona una representación en cadena de texto de este objeto `PatronADN`.
     * Incluye la secuencia del patrón, su frecuencia y la lista de ubicaciones donde fue encontrado.
     *
     * @return Una cadena formateada que describe el patrón de ADN.
     */
    @Override
    public String toString() {
        StringBuilder ubicacionesStr = new StringBuilder();
        ubicacionesStr.append("[");
        for (int i = 0; i < numUbicaciones; i++) {
            ubicacionesStr.append(ubicaciones[i]);
            if (i < numUbicaciones - 1) {
                ubicacionesStr.append(", ");
            }
        }
        ubicacionesStr.append("]");
        return "Patron: " + secuencia + ", Frecuencia: " + frecuencia + ", Ubicaciones: " + ubicacionesStr.toString();
    }
}