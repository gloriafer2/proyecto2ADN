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
    private int frecuencia;
    private int[] ubicaciones; 
    private int numUbicaciones;

    /**
     * Constructor de la clase PatronADN.
     * @param secuencia La secuencia de 3 nucleótidos del patrón (ej. "ATG").
     * @param primeraUbicacion La primera posición de inicio del patrón en la secuencia de ADN principal.
     */
    public PatronADN(String secuencia, int primeraUbicacion) {
        this.secuencia = secuencia;
        this.frecuencia = 1;
        
        this.ubicaciones = new int[10]; 
        this.ubicaciones[0] = primeraUbicacion;
        this.numUbicaciones = 1;
    }

    /**
     * Obtiene la secuencia del patrón de ADN.
     * @return La secuencia del patrón.
     */
    public String getSecuencia() {
        return secuencia;
    }

    /**
     * Obtiene la frecuencia de aparición del patrón.
     * @return La frecuencia del patrón.
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * Obtiene el array de ubicaciones donde aparece el patrón.
     * NOTA: Este método devuelve la referencia al array interno.
     * Es preferible devolver una copia o implementar un iterador si la clase fuera más robusta.
     * @return Un array de enteros que contiene las ubicaciones del patrón.
     */
    public int[] getUbicaciones() {
        return ubicaciones;
    }

    /**
     * Obtiene el número de ubicaciones actualmente almacenadas en el array.
     * @return El número de ubicaciones.
     */
    public int getNumUbicaciones() {
        return numUbicaciones;
    }

    /**
     * Incrementa la frecuencia de aparición del patrón en uno.
     */
    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    public void agregarUbicacion(int ubicacion) {
        if (numUbicaciones == ubicaciones.length) {
           
            int[] nuevoArray = new int[ubicaciones.length * 2];
            for (int i = 0; i < ubicaciones.length; i++) {
                nuevoArray[i] = ubicaciones[i];
            }
            ubicaciones = nuevoArray;
        }
        ubicaciones[numUbicaciones++] = ubicacion;
    }

    /**
     * Compara este objeto PatronADN con el objeto especificado para igualdad.
     * Dos PatronADN son considerados iguales si sus secuencias son idénticas.
     * Este método es fundamental para el correcto funcionamiento de la HashTable.
     * @param obj El objeto a comparar con este PatronADN.
     * @return true si los objetos son iguales (tienen la misma secuencia), false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PatronADN other = (PatronADN) obj;
        return secuencia.equals(other.secuencia);
    }

    /**
     * Genera un valor de código hash para este PatronADN.
     * La implementación de esta función hash busca minimizar colisiones
     * para patrones de ADN de longitud 3.
     * Se asignan valores primos a cada nucleótido y se utiliza un enfoque polinomial.
     * @return Un valor de código hash entero.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        int prime = 31; 

        // Mapeo simple de nucleótidos a enteros
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
     * Compara este PatronADN con otro PatronADN para ordenar.
     * La comparación se basa primero en la frecuencia (descendente: mayor frecuencia primero),
     * y luego alfabéticamente por la secuencia si las frecuencias son iguales.
     * Este método es fundamental para el correcto funcionamiento del Árbol Binario de Búsqueda.
     * @param otro El PatronADN con el que se va a comparar.
     * @return Un valor negativo si este objeto es "menor" (mayor frecuencia),
     * cero si son "iguales" (misma frecuencia y secuencia),
     * o un valor positivo si este objeto es "mayor" (menor frecuencia).
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
     * Devuelve una representación en cadena de este PatronADN,
     * incluyendo su secuencia, frecuencia y ubicaciones.
     * @return Una cadena que representa el PatronADN.
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
