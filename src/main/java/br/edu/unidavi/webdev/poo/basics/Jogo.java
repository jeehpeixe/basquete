package br.edu.unidavi.webdev.poo.basics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jessicapeixe
 */
public class Jogo {
    
    private final Time equipeA;
    private final Time equipeB;
    private Date dataHora;
    
    protected int pontosEquipeA;
    protected int pontosEquipeB;
    protected Enum<SituacaoJogoEnum> situacao;
    
    public Jogo(Time TimeA, Time TimeB, Date dataHora){
        this.equipeA = TimeA;
        this.equipeB = TimeB;
        this.dataHora = dataHora;
        this.situacao = SituacaoJogoEnum.AGUARDANDO;
        this.pontosEquipeA = 0;
        this.pontosEquipeB = 0;
    }

    public String getDataHoraString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String sDataHora = df.format(getDataHora());
        return sDataHora;
    }
    
    public Date getDataHora(){
        return dataHora;
    }
    
    public String getChaveJogo(){
        return montaChaveJogo(this.equipeA.getCodigo(), this.equipeB.getCodigo(), this.getDataHoraString());
    }
    
    static public String montaChaveJogo(int codigoTimeA, int codigoTimeB, String dataHora) {
        return codigoTimeA + "_" + codigoTimeB + "_" + dataHora;
    }
    
    public void iniciaJogo(){
        if (isAguardando()) {
            situacao = SituacaoJogoEnum.ANDAMENTO;
        }
    }
    
    /**
     * Seta uma nova data hora para um Jogo que ainda não foi iniciado
     * @param dataHora
     */
    public void reagendarJogo(Date dataHora){
        if (isAguardando()) {
            this.dataHora = dataHora;
        }
    }
    
    /**
     * Adiciona uma pontuacao para o Time A
     * @param pontuacao 
     */
    public void adicionarPontuacaoEquipeA(InterfacePontuacao pontuacao){
        if (isAndamento()) {
            pontosEquipeA += pontuacao.getQuantidadePontos();
        }
    }
    
    /**
     * Adiciona uma pontuacao para o Time B
     * @param pontuacao 
     */
    public void adicionarPontuacaoEquipeB(InterfacePontuacao pontuacao){
        if (isAndamento()) {
            pontosEquipeB += pontuacao.getQuantidadePontos();
        }
    }
    
    /**
     * Encerra um Jogo se ele ainda estiver em andamento
     */
    public void encerrarJogo(){
        if (isAndamento()) {
            situacao = SituacaoJogoEnum.ENCERRADO;
        }
    }

    public int getPontosEquipeA() {
        return pontosEquipeA;
    }

    public int getPontosEquipeB() {
        return pontosEquipeB;
    }

    public Time getEquipeA() {
        return equipeA;
    }

    public Time getEquipeB() {
        return equipeB;
    }
    
    public boolean isAguardando(){
        return situacao == SituacaoJogoEnum.AGUARDANDO;
    }
    
    private boolean isAndamento(){
        return situacao == SituacaoJogoEnum.ANDAMENTO;
    }
}
