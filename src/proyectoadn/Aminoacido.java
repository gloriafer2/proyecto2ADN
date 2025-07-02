/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */
public class Aminoacido {
    private String nombre;
    private String tipo; // "Inicio", "Parada", "Aminoacido Valido"
    private String[] codonesARN; // Las tripletas de ARN asociadas, ej. "UUU", "UUC"
    private int numCodones; // Contador de codones reales en el array

    /**
     * Constructor de la clase Aminoacido.
     * @param nombre El nombre completo del aminoácido (ej. "Fenilalanina").
     * @param tipo El tipo de codón (ej. "Aminoacido Valido", "Inicio", "Parada").
     */
    public Aminoacido(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        // Inicializamos el array de codones ARN con un tamaño inicial.
        // Se redimensionará si es necesario.
        this.codonesARN = new String[5]; // Un aminoácido puede tener hasta 6 codones.
        this.numCodones = 0;
    }

    /**
     * Agrega un codón ARN a la lista de codones que sintetizan este aminoácido.
     * Si el array de codones está lleno, se redimensiona.
     * @param codon La secuencia del codón ARN (ej. "UUU").
     */
    public void agregarCodon(String codon) {
        if (numCodones == codonesARN.length) {
            // Redimensionar array si está lleno
            String[] nuevoArray = new String[codonesARN.length * 2];
            for (int i = 0; i < codonesARN.length; i++) {
                nuevoArray[i] = codonesARN[i];
            }
            codonesARN = nuevoArray;
        }
        codonesARN[numCodones++] = codon;
    }

    /**
     * Obtiene el nombre del aminoácido.
     * @return El nombre del aminoácido.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tipo de aminoácido/codón.
     * @return El tipo (ej. "Aminoacido Valido", "Inicio", "Parada").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene el array de codones ARN asociados a este aminoácido.
     * @return Un array de cadenas que contiene los codones ARN.
     */
    public String[] getCodonesARN() {
        return codonesARN;
    }

    /**
     * Obtiene el número de codones ARN actualmente almacenados.
     * @return El número de codones.
     */
    public int getNumCodones() {
        return numCodones;
    }

    /**
     * Devuelve una representación en cadena de este Aminoacido,
     * incluyendo su nombre, tipo y los codones ARN asociados.
     * @return Una cadena que representa el Aminoacido.
     */
    @Override
    public String toString() {
        StringBuilder codonesStr = new StringBuilder();
        codonesStr.append("[");
        for (int i = 0; i < numCodones; i++) {
            codonesStr.append(codonesARN[i]);
            if (i < numCodones - 1) {
                codonesStr.append(", ");
            }
        }
        codonesStr.append("]");
        return "Aminoacido: " + nombre + " (Tipo: " + tipo + "), Codones ARN: " + codonesStr.toString();
    }
}