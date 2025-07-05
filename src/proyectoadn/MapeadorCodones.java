/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoadn;

/**
 *
 * @author Gloria
 */

public class MapeadorCodones {

    private MiHashTable tablaCodonesAminoacidos;

    public MapeadorCodones() {
        this.tablaCodonesAminoacidos = new MiHashTable(67);
        cargarTablaCodones();
    }

    public String convertirADN_a_ARN(String secuenciaADN) {
        if (secuenciaADN == null || secuenciaADN.length() != 3) {
            return null;
        }
        return secuenciaADN.replace('T', 'U');
    }

    public Aminoacido getAminoacido(String codonARN) {
        return (Aminoacido) tablaCodonesAminoacidos.buscar(codonARN);
    }

    private void cargarTablaCodones() {
        // Fenilalanina
        Aminoacido fenilalanina = new Aminoacido("Fenilalanina", "Phe", 'F');
        fenilalanina.agregarCodonAsociado("UUU");
        tablaCodonesAminoacidos.insertar("UUU", fenilalanina);
        fenilalanina.agregarCodonAsociado("UUC");
        tablaCodonesAminoacidos.insertar("UUC", fenilalanina);

        // Leucina
        Aminoacido leucina = new Aminoacido("Leucina", "Leu", 'L');
        leucina.agregarCodonAsociado("UUA");
        tablaCodonesAminoacidos.insertar("UUA", leucina);
        leucina.agregarCodonAsociado("UUG");
        tablaCodonesAminoacidos.insertar("UUG", leucina);
        leucina.agregarCodonAsociado("CUU");
        tablaCodonesAminoacidos.insertar("CUU", leucina);
        leucina.agregarCodonAsociado("CUC");
        tablaCodonesAminoacidos.insertar("CUC", leucina);
        leucina.agregarCodonAsociado("CUA");
        tablaCodonesAminoacidos.insertar("CUA", leucina);
        leucina.agregarCodonAsociado("CUG");
        tablaCodonesAminoacidos.insertar("CUG", leucina);

        // Isoleucina
        Aminoacido isoleucina = new Aminoacido("Isoleucina", "Ile", 'I');
        isoleucina.agregarCodonAsociado("AUU");
        tablaCodonesAminoacidos.insertar("AUU", isoleucina);
        isoleucina.agregarCodonAsociado("AUC");
        tablaCodonesAminoacidos.insertar("AUC", isoleucina);
        isoleucina.agregarCodonAsociado("AUA");
        tablaCodonesAminoacidos.insertar("AUA", isoleucina);

        // Metionina (Inicio)
        Aminoacido metionina = new Aminoacido("Metionina", "Met", 'M');
        metionina.agregarCodonAsociado("AUG");
        tablaCodonesAminoacidos.insertar("AUG", metionina);

        // Valina
        Aminoacido valina = new Aminoacido("Valina", "Val", 'V');
        valina.agregarCodonAsociado("GUU");
        tablaCodonesAminoacidos.insertar("GUU", valina);
        valina.agregarCodonAsociado("GUC");
        tablaCodonesAminoacidos.insertar("GUC", valina);
        valina.agregarCodonAsociado("GUA");
        tablaCodonesAminoacidos.insertar("GUA", valina);
        valina.agregarCodonAsociado("GUG");
        tablaCodonesAminoacidos.insertar("GUG", valina);

        // Serina
        Aminoacido serina = new Aminoacido("Serina", "Ser", 'S');
        serina.agregarCodonAsociado("UCU");
        tablaCodonesAminoacidos.insertar("UCU", serina);
        serina.agregarCodonAsociado("UCC");
        tablaCodonesAminoacidos.insertar("UCC", serina);
        serina.agregarCodonAsociado("UCA");
        tablaCodonesAminoacidos.insertar("UCA", serina);
        serina.agregarCodonAsociado("UCG");
        tablaCodonesAminoacidos.insertar("UCG", serina);
        serina.agregarCodonAsociado("AGU");
        tablaCodonesAminoacidos.insertar("AGU", serina);
        serina.agregarCodonAsociado("AGC");
        tablaCodonesAminoacidos.insertar("AGC", serina);

        // Prolina
        Aminoacido prolina = new Aminoacido("Prolina", "Pro", 'P');
        prolina.agregarCodonAsociado("CCU");
        tablaCodonesAminoacidos.insertar("CCU", prolina);
        prolina.agregarCodonAsociado("CCC");
        tablaCodonesAminoacidos.insertar("CCC", prolina);
        prolina.agregarCodonAsociado("CCA");
        tablaCodonesAminoacidos.insertar("CCA", prolina);
        prolina.agregarCodonAsociado("CCG");
        tablaCodonesAminoacidos.insertar("CCG", prolina);

        // Treonina
        Aminoacido treonina = new Aminoacido("Treonina", "Thr", 'T');
        treonina.agregarCodonAsociado("ACU");
        tablaCodonesAminoacidos.insertar("ACU", treonina);
        treonina.agregarCodonAsociado("ACC");
        tablaCodonesAminoacidos.insertar("ACC", treonina);
        treonina.agregarCodonAsociado("ACA");
        tablaCodonesAminoacidos.insertar("ACA", treonina);
        treonina.agregarCodonAsociado("ACG");
        tablaCodonesAminoacidos.insertar("ACG", treonina);

        // Alanina
        Aminoacido alanina = new Aminoacido("Alanina", "Ala", 'A');
        alanina.agregarCodonAsociado("GCU");
        tablaCodonesAminoacidos.insertar("GCU", alanina);
        alanina.agregarCodonAsociado("GCC");
        tablaCodonesAminoacidos.insertar("GCC", alanina);
        alanina.agregarCodonAsociado("GCA");
        tablaCodonesAminoacidos.insertar("GCA", alanina);
        alanina.agregarCodonAsociado("GCG");
        tablaCodonesAminoacidos.insertar("GCG", alanina);

        // Tirosina
        Aminoacido tirosina = new Aminoacido("Tirosina", "Tyr", 'Y');
        tirosina.agregarCodonAsociado("UAU");
        tablaCodonesAminoacidos.insertar("UAU", tirosina);
        tirosina.agregarCodonAsociado("UAC");
        tablaCodonesAminoacidos.insertar("UAC", tirosina);

        // Histidina
        Aminoacido histidina = new Aminoacido("Histidina", "His", 'H');
        histidina.agregarCodonAsociado("CAU");
        tablaCodonesAminoacidos.insertar("CAU", histidina);
        histidina.agregarCodonAsociado("CAC");
        tablaCodonesAminoacidos.insertar("CAC", histidina);

        // Glutamina
        Aminoacido glutamina = new Aminoacido("Glutamina", "Gln", 'Q');
        glutamina.agregarCodonAsociado("CAA");
        tablaCodonesAminoacidos.insertar("CAA", glutamina);
        glutamina.agregarCodonAsociado("CAG");
        tablaCodonesAminoacidos.insertar("CAG", glutamina);

        // Asparagina
        Aminoacido asparagina = new Aminoacido("Asparagina", "Asn", 'N');
        asparagina.agregarCodonAsociado("AAU");
        tablaCodonesAminoacidos.insertar("AAU", asparagina);
        asparagina.agregarCodonAsociado("AAC");
        tablaCodonesAminoacidos.insertar("AAC", asparagina);

        // Lisina
        Aminoacido lisina = new Aminoacido("Lisina", "Lys", 'K');
        lisina.agregarCodonAsociado("AAA");
        tablaCodonesAminoacidos.insertar("AAA", lisina);
        lisina.agregarCodonAsociado("AAG");
        tablaCodonesAminoacidos.insertar("AAG", lisina);

        // Ácido Aspártico
        Aminoacido acidoAspartico = new Aminoacido("Ácido Aspártico", "Asp", 'D');
        acidoAspartico.agregarCodonAsociado("GAU");
        tablaCodonesAminoacidos.insertar("GAU", acidoAspartico);
        acidoAspartico.agregarCodonAsociado("GAC");
        tablaCodonesAminoacidos.insertar("GAC", acidoAspartico);

        // Ácido Glutámico
        Aminoacido acidoGlutamico = new Aminoacido("Ácido Glutámico", "Glu", 'E');
        acidoGlutamico.agregarCodonAsociado("GAA");
        tablaCodonesAminoacidos.insertar("GAA", acidoGlutamico);
        acidoGlutamico.agregarCodonAsociado("GAG");
        tablaCodonesAminoacidos.insertar("GAG", acidoGlutamico);

        // Cisteína
        Aminoacido cisteina = new Aminoacido("Cisteína", "Cys", 'C');
        cisteina.agregarCodonAsociado("UGU");
        tablaCodonesAminoacidos.insertar("UGU", cisteina);
        cisteina.agregarCodonAsociado("UGC");
        tablaCodonesAminoacidos.insertar("UGC", cisteina);

        // Triptófano
        Aminoacido triptofano = new Aminoacido("Triptófano", "Trp", 'W');
        triptofano.agregarCodonAsociado("UGG");
        tablaCodonesAminoacidos.insertar("UGG", triptofano);

        // Arginina
        Aminoacido arginina = new Aminoacido("Arginina", "Arg", 'R');
        arginina.agregarCodonAsociado("CGU");
        tablaCodonesAminoacidos.insertar("CGU", arginina);
        arginina.agregarCodonAsociado("CGC");
        tablaCodonesAminoacidos.insertar("CGC", arginina);
        arginina.agregarCodonAsociado("CGA");
        tablaCodonesAminoacidos.insertar("CGA", arginina);
        arginina.agregarCodonAsociado("CGG");
        tablaCodonesAminoacidos.insertar("CGG", arginina);
        arginina.agregarCodonAsociado("AGA");
        tablaCodonesAminoacidos.insertar("AGA", arginina);
        arginina.agregarCodonAsociado("AGG");
        tablaCodonesAminoacidos.insertar("AGG", arginina);

        // Glicina
        Aminoacido glicina = new Aminoacido("Glicina", "Gly", 'G');
        glicina.agregarCodonAsociado("GGU");
        tablaCodonesAminoacidos.insertar("GGU", glicina);
        glicina.agregarCodonAsociado("GGC");
        tablaCodonesAminoacidos.insertar("GGC", glicina);
        glicina.agregarCodonAsociado("GGA");
        tablaCodonesAminoacidos.insertar("GGA", glicina);
        glicina.agregarCodonAsociado("GGG");
        tablaCodonesAminoacidos.insertar("GGG", glicina);
        
        // Codones STOP
        Aminoacido stop = new Aminoacido("STOP", "STOP", '-');
        stop.agregarCodonAsociado("UAA");
        tablaCodonesAminoacidos.insertar("UAA", stop);
        stop.agregarCodonAsociado("UAG");
        tablaCodonesAminoacidos.insertar("UAG", stop);
        stop.agregarCodonAsociado("UGA");
        tablaCodonesAminoacidos.insertar("UGA", stop);
    }
}