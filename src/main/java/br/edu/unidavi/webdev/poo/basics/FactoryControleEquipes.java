package br.edu.unidavi.webdev.poo.basics;

/**
 *
 * @author jessicapeixe
 */
abstract class FactoryControleEquipes {
    
    static public Time criaEquipe(String sNomeEquipe) {
        for(Time oEquipeAtual : ControleJogos.getInstance().buscaEquipes()){
            if (oEquipeAtual.getDescricao().equals(sNomeEquipe)) {
                return null;
            }
        }
        
        return ControleJogos.getInstance().criaEquipe(sNomeEquipe);  
    }   
}