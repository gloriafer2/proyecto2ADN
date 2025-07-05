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

    
    public PatronADN(String secuencia) {
        this.secuencia = secuencia;
        this.frecuencia = 0;
        this.ubicaciones = new int[10];
        this.numUbicaciones = 0;
    }
    

    public String getSecuencia() {
        return secuencia;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public int[] getUbicaciones() {
        
        int[] copiaUbicaciones = new int[numUbicaciones];
        System.arraycopy(ubicaciones, 0, copiaUbicaciones, 0, numUbicaciones);
        return copiaUbicaciones;
    }

    public int getNumUbicaciones() {
        return numUbicaciones;
    }

    public void incrementarFrecuencia() {
        this.frecuencia++;
    }

    public void agregarUbicacion(int ubicacion) {
        if (numUbicaciones == ubicaciones.length) {
            int[] nuevoArray = new int[ubicaciones.length * 2];
            System.arraycopy(ubicaciones, 0, nuevoArray, 0, ubicaciones.length);
            ubicaciones = nuevoArray;
        }
        ubicaciones[numUbicaciones++] = ubicacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PatronADN other = (PatronADN) obj;
        return secuencia.equals(other.secuencia);
    }

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

    @Override
    public int compareTo(PatronADN otro) {
        int comparacionFrecuencia = Integer.compare(otro.frecuencia, this.frecuencia);
        if (comparacionFrecuencia != 0) {
            return comparacionFrecuencia;
        }
        return this.secuencia.compareTo(otro.secuencia);
    }

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