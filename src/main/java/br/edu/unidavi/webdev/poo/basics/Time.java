package br.edu.unidavi.webdev.poo.basics;

/**
 *
 * @author jessicapeixe
 */
abstract class Time {
    
    private final int codigo;
    private final String descricao;

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Time(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    public String getDescricaoComChave(){
        return this.codigo + " - " + this.descricao;
    }
}