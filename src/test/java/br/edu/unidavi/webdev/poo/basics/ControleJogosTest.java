package br.edu.unidavi.webdev.poo.basics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControleJogosTest {
    
    @Test
    public void teste01() throws ParseException{
        Time oTimeA = ControleJogos.getInstance().criaEquipe("Time 01");
        Time oTimeB = ControleJogos.getInstance().criaEquipe("Time 02");

        DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Jogo oJogo = new Jogo(oTimeA, oTimeB, dDataHora.parse("20/03/2018 08:00:00"));
        ControleJogos.getInstance().adicionaJogo(oJogo);

        assert ControleJogos.getInstance().getUltimoAgendamento().getDataHoraString().equals("20/03/2018 08:00:00");
    }
    
    @Test
    public void teste02() throws ParseException{
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "20/03/2018 08:00:00"));
        
        assert oJogo.getEquipeA().getDescricaoComChave().equals("1 - Time 01");
    }

    @Test
    public void teste03() throws ParseException{
        DateFormat dNewDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        Jogo oJogoReagendar = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "20/03/2018 08:00:00"));
        oJogoReagendar.reagendarJogo(dNewDataHora.parse("12/08/2019 08:00:00"));
        
        assert oJogoReagendar.getDataHoraString().equals("12/08/2019 08:00:00");
    }

    @Test
    public void teste04() throws ParseException{
        DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dDateTime = dDataHora.parse("12/08/2019 08:00:00");
 
        assert ControleJogos.getInstance().buscarJogosPorData(dDateTime).get(0).getDataHoraString().equals("12/08/2019 08:00:00");
    }
    
    @Test
    public void teste05() throws ParseException{
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.iniciaJogo();
        oJogo.adicionarPontuacaoEquipeA(new Cesta02());
        
        assert oJogo.getPontosEquipeA() == 2;
    }
    
    @Test
    public void teste06() throws ParseException{
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.adicionarPontuacaoEquipeB(new Cesta03());
        
        assert oJogo.getPontosEquipeB() == 3;
    }
    
    @Test
    public void teste07() throws ParseException{
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.adicionarPontuacaoEquipeB(new Cesta01());
        
        assert oJogo.getPontosEquipeB() == 4;
    }

    @Test
    public void teste08() throws ParseException {
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        oJogo.encerrarJogo();
        
        assert oJogo.getPontosEquipeA() == 2 && oJogo.getPontosEquipeB() == 4;
    }
    
    @Test
    public void teste09() throws ParseException {
        Jogo oJogo = ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(1, 2, "12/08/2019 08:00:00"));
        
        assert oJogo.getEquipeB().getCodigo() == 2;
    }
    
    @Test
    public void teste10() throws ParseException {        
        assert ControleJogos.getInstance().getAllAgendamentos().size() == 1;
    }
    
    @Test
    public void teste11() throws ParseException {        
        assert ControleJogos.getInstance().existemJogosAguardando() == false;
    }
    
    @Test
    public void teste12() throws ParseException {        
        assert ControleJogos.getInstance().buscarJogoPorChave(Jogo.montaChaveJogo(3, 4, "12/12/2015 08:00:00")) == null;
    }
    
    @Test
    public void teste13() throws ParseException{
        assert ControleJogos.getInstance().criaEquipe("Time 02") == null;
    }
}