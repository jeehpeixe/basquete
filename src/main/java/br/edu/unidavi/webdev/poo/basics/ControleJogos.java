package br.edu.unidavi.webdev.poo.basics;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jessicapeixe
 */
public final class ControleJogos {
    
    private final ArrayList<Jogo> aJogos = new ArrayList();
    private final ArrayList<Time> aEquipes = new ArrayList();
    
    private static final ControleJogos INSTANCE = new ControleJogos();

    public static ControleJogos getInstance(){
        return INSTANCE;
    }
    
    private ControleJogos(){}
    
    /**
     * Cria um novo agendamento de Jogo
     * @param oJogo
     */
    public void adicionaJogo(Jogo oJogo){
        aJogos.add(oJogo);
    }
    
    public Jogo getUltimoAgendamento(){
        return aJogos.get(aJogos.size() - 1);
    }
    
    /**
     * Busca todos os jogos agendados para a Data/Hora informada
     * @param dataHora
     * @return ArrayList
     */
    public ArrayList<Jogo> buscarJogosPorData(Date dataHora){
        
        ArrayList<Jogo> aJogosData = new ArrayList();
        
        aJogos.stream().filter((oJogoAtual) -> (oJogoAtual.getDataHora().equals(dataHora))).forEachOrdered((oJogoAtual) -> {
            aJogosData.add(oJogoAtual);
        });
        
        return aJogosData;
    }
    
    /**
     * Busca o Jogo da chave
     * @param sChaveJogo
     * @return 
     */
    public Jogo buscarJogoPorChave(String sChaveJogo){
        for(Jogo oJogoAtual : aJogos){
            if (oJogoAtual.getChaveJogo().equals(sChaveJogo)) {
                return oJogoAtual;
            }
        }
        
        return null;
    }
    
    public Time criaEquipe(String sNomeEquipe){
        Time oEquipe = new TimeBasquete(aEquipes.size() + 1, sNomeEquipe);
        aEquipes.add(oEquipe);
        
        return oEquipe;
    }
    
    public ArrayList<Time> buscaEquipes(){
        return aEquipes;
    }
    
    public ArrayList<Jogo> getAllAgendamentos(){
        return aJogos;
    }
    
    public boolean existemJogosAguardando(){
        return aJogos.stream().anyMatch((oJogoAtual) -> (oJogoAtual.isAguardando()));
    }
}