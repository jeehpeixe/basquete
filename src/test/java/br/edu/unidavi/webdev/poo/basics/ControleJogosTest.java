package br.edu.unidavi.webdev.poo.basics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class ControleJogosTest {

    @Test
    public void agendamentoDeJogo() throws ParseException{
        Time oTimeA = FactoryControleEquipes.criaEquipe("Time 01");
        Time oTimeB = FactoryControleEquipes.criaEquipe("Time 02");

        DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Jogo oJogo = new Jogo(oTimeA, oTimeB, dDataHora.parse("20/03/2018 08:00:00"));
        ControleJogos.getInstance().adicionaJogo(oJogo);

        assert ControleJogos.getInstance().getUltimoAgendamento().getDataHoraString().equals("20/03/2018 08:00:00");
    }

    @Test
    public void reagendamentoDeJogo() throws ParseException{
        DateFormat dNewDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        Jogo oJogoReagendar = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "20/03/2018 08:00:00"));
        oJogoReagendar.reagendarJogo(dNewDataHora.parse("12/08/2019 08:00:00"));
        
        assert oJogoReagendar.getDataHoraString().equals("12/08/2019 08:00:00");
    }

    @Test
    public void jogosDeUmaData() throws ParseException{
        DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dDateTime = dDataHora.parse("12/08/2019 08:00:00");
 
        assert ControleJogos.getInstance().buscarJogosPorData(dDateTime).get(0).getDataHoraString().equals("12/08/2019 08:00:00");
    }
    
    @Test
    public void adicionarPontosJogoAndamento() throws ParseException{
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.iniciaJogo();
        oJogo.adicionarPontuacaoEquipeA(new Cesta02());
        
        assert oJogo.getPontosEquipeA() == 2;
    }

    @Test
    public void definirResultadoJogoEmAdamento() throws ParseException {
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.encerrarJogo();
        
        assert oJogo.getPontosEquipeA() == 2 && oJogo.getPontosEquipeB() == 0;
    }
}