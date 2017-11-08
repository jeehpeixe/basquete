package br.edu.unidavi.webdev.poo.basics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Basquete {
    
    public void processaDados() {
        
        boolean bSair = false;
        
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                printTextoMenuPrincipal();
                String opcao = scanner.nextLine();
                
                System.out.printf("\n");
                
                switch (opcao) {
                    case "1": processaInclusaoTime(scanner);         break;
                    case "2": processaInclusaoAgendamento(scanner);  break;
                    case "3": processaAlteracaoAgendamento(scanner); break;
                    case "4": processaJogo(scanner);                 break;
                    case "5": bSair = true;                          break;  
                    
                    default: System.out.printf("Opção inválida! \n\n");
                }
                
            } while (bSair == false);
        }
    }
    
    private void printTextoMenuPrincipal(){
        System.out.printf("------------------------------------------------------------------------------------------\n");
        System.out.printf("|1 - Incluir Time | 2 - Agendar Jogo  | 3 - Reagendar Jogo | 4 - Iniciar Jogo | 5 - Sair |\n");
        System.out.printf("------------------------------------------------------------------------------------------\n\n");
    }
    
    private void processaInclusaoTime(Scanner scanner){
        System.out.println("Informe o nome do Time: ");
        
        if (FactoryControleEquipes.criaEquipe(scanner.nextLine()) != null) {
            System.out.printf("\n Time criado com sucesso!\n\n");
        }
        else {
            System.out.printf("\n Time informado já existe!\n\n");
        }
    }
    
    private void processaInclusaoAgendamento(Scanner scanner){
        if (ControleJogos.getInstance().buscaEquipes().size() >= 2) {
            Time oTimeA    = processaSelecaoTime(scanner, "1", null);
            Time oTimeB    = processaSelecaoTime(scanner, "2", oTimeA.getCodigo());
            Date dDataHora = processaSelecaoData(scanner);
            
            ControleJogos.getInstance().adicionaJogo(new Jogo(oTimeA, oTimeB, dDataHora));
        
            System.out.printf("\n Novo Jogo Agendado! \n\n");
        }
        else {
            System.out.printf("\n Deve haver pelo menos dois times cadastrados! \n\n");
        }
    }
    
    private Time processaSelecaoTime(Scanner scanner, String sNumero, Integer iTime1){
        
        Time oTime = null;
        
        do {
            System.out.printf("Selecione o Time "+ sNumero + ": \n\n");

            ArrayList<Time> aTimes = ControleJogos.getInstance().buscaEquipes();
            
            aTimes.forEach((oEquipeAtual) -> {
                if (iTime1 == null || (oEquipeAtual.getCodigo() != iTime1)) {
                    System.out.printf(oEquipeAtual.getDescricaoComChave() + "\n");
                }
            });

            String sValorInformado = scanner.nextLine();

            for (Time oEquipeAtual : aTimes) {
                boolean bTimeExiste = String.valueOf(oEquipeAtual.getCodigo()).equals(sValorInformado);
                if (bTimeExiste && (iTime1 == null || oEquipeAtual.getCodigo() != iTime1)) {
                    oTime = oEquipeAtual;
                }
            }
            
            if (oTime == null) {
                System.out.printf("Time inválido!\n\n");
            }

        } while (oTime == null);
        
        return oTime;
    }
    
    private Date processaSelecaoData(Scanner scanner){
        
        DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        Date dData = null;
        
        do {
            System.out.printf("Informe a Data e Hora do Jogo no formato DD/MM/AAAA HH:MM:SS: \n\n");

            try {
                dData = dDataHora.parse("10/10/2012 12:00:00");
                dDataHora.parse(scanner.nextLine());
            } 
            catch (ParseException ex) {
                System.out.printf("Formato de Data/Hora Inválido! \n");
                dData = null;
            }

        } while (dData == null);
        
        return dData;
    }
    
    private void processaAlteracaoAgendamento(Scanner scanner){
        Jogo oJogo = selecionaJogosAguardando(scanner);
        
        if (oJogo == null) {
            System.out.printf("Nenhum jogo disponível! \n\n");
        }
        else {
            DateFormat dDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
            boolean bDataOK = false;

            do {
                System.out.printf("Informe a nova Data/Hora do Jogo no formato DD/MM/AAAA HH:MM:SS: \n\n");

                String sValorInformado = scanner.nextLine();

                try {
                    
                    Jogo oJogoReagendar = ControleJogos.getInstance().buscarJogoPorChave(oJogo.getChaveJogo());
                    oJogoReagendar.reagendarJogo(dDataHora.parse(sValorInformado));
        
                    System.out.printf("Jogo reagendado com sucesso! \n");
                    bDataOK = true;
                } 
                catch (ParseException ex) {
                    System.out.printf("Formato de Data/Hora Inválido! \n");
                }

            } while (!bDataOK);
        }
    }
    
    private Jogo selecionaJogosAguardando(Scanner scanner){
        
        ArrayList<Jogo> aJogos = ControleJogos.getInstance().getAllAgendamentos();
        
        Jogo oJogo = null;
        
        if (ControleJogos.getInstance().existemJogosAguardando()) {
            printTextoMenuJogosDisponiveis();
            do {
                String sValor = scanner.nextLine();

                try {
                    try {
                        oJogo = aJogos.get(Integer.parseInt(sValor) - 1);
                    }
                    catch(IndexOutOfBoundsException ex){
                        oJogo = null;
                        System.out.printf("Selecione uma opção de jogo válida! \n");
                    }
                } 
                catch (NumberFormatException ex) {
                    oJogo = null;
                    System.out.printf("Selecione uma opção de jogo válida! \n");
                }
            } while (oJogo == null);
        }
        
        return oJogo;
    }
    
    private void printTextoMenuJogosDisponiveis(){
        System.out.printf("Selecione o jogo desejado: \n");
            
        ArrayList<Jogo> aJogos = ControleJogos.getInstance().getAllAgendamentos();

        aJogos.stream().filter((oJogoAtual) -> (oJogoAtual.isAguardando())).forEachOrdered((Jogo oJogoAtual) -> {

            System.out.printf(
                "Jogo: "        + (aJogos.indexOf(oJogoAtual) + 1) +
                " - Time A: "   + oJogoAtual.getEquipeA().getDescricao()+
                ", Time B: "    + oJogoAtual.getEquipeB().getDescricao() +
                ", Data/Hora: " + oJogoAtual.getDataHoraString() +
                " \n"
            );
        });

    }
    
    private void processaJogo(Scanner scanner){
        
        Jogo oJogo = selecionaJogosAguardando(scanner);
        
        if (oJogo == null) {
            System.out.printf("Nenhum jogo disponível! \n\n");
        }
        else {
            oJogo.iniciaJogo();

            boolean bEncerrado = false;

            do {
                printTextoMenuPontos();
                        
                switch(scanner.nextLine()){
                    case "1":
                        oJogo.adicionarPontuacaoEquipeA(selecionaPontuacao(scanner));
                    break;
                    case "2":
                        oJogo.adicionarPontuacaoEquipeB(selecionaPontuacao(scanner));
                    break;
                    case "3":
                        oJogo.encerrarJogo();
                        System.out.printf(
                            "Jogo Encerrado! \n" +
                            "Equipe A: " + oJogo.getPontosEquipeA() + " pontos \n" +
                            "Equipe B: " + oJogo.getPontosEquipeB() + " pontos \n\n"
                        );
                        bEncerrado = true;
                    break;
                    default:
                        System.out.printf("Opção inválida! \n\n");
                }
            } while(!bEncerrado); 
        }
    }
    
    private void printTextoMenuPontos(){
        System.out.printf("----------------------------------------------------------\n");
        System.out.printf("| 1 - Ponto Time A | 2 - Ponto Time B | 3 - Encerrar Jogo|\n");
        System.out.printf("----------------------------------------------------------\n\n");
    }
    
    private InterfacePontuacao selecionaPontuacao(Scanner scanner){
        
        InterfacePontuacao oPontuacao = null;
        
        do {
            printTextoSelecaoPontuacao();
            switch(scanner.nextLine()){
                case "1": oPontuacao = new Cesta01(); break;
                case "2": oPontuacao = new Cesta02(); break;
                case "3": oPontuacao = new Cesta03(); break;
                
                default: System.out.printf("Opção inválida! \n\n");
            }
        } while(oPontuacao == null); 
        
        return oPontuacao;
    }
    
    private void printTextoSelecaoPontuacao(){
        System.out.printf("-------------------------------\n");
        System.out.printf("|1 Ponto | 2 Pontos | 3 Pontos|\n");
        System.out.printf("-------------------------------\n");
    }
}
