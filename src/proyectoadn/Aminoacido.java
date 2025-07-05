/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proyectoadn;

public class Aminoacido {
    private String nombreCompleto;
    private String abreviatura3Letras;
    private char abreviatura1Letra;
    
    private String tipo; 

    private String[] codonesAsociados;
    private int numCodones;

    private int frecuenciaGlobal;

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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getAbreviatura3Letras() {
        return abreviatura3Letras;
    }

    public char getAbreviatura1Letra() {
        return abreviatura1Letra;
    }

    public String getTipo() {
        return tipo;
    }

    public String[] getTodosLosCodonesAsociados() {
        String[] result = new String[numCodones];
        System.arraycopy(codonesAsociados, 0, result, 0, numCodones);
        return result;
    }

    public int getNumCodones() {
        return numCodones;
    }
    
    public void incrementarFrecuenciaGlobal(int cantidad) {
        this.frecuenciaGlobal += cantidad;
    }

    public int getFrecuenciaGlobal() {
        return frecuenciaGlobal;
    }

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