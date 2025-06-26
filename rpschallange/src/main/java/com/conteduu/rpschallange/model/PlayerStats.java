package com.conteduu.rpschallange.model;

/*

    Guarda as estatisticas do jogador durante UMA partida
    - Não mostra nada em tela, nem acessa banco
    - Sua funcao é guardar e atualizar os dados sobre a partida
    e calcular informacoes derivadas (total de vitorias, % de vitorias)

* */

public class PlayerStats {

    private int wins;
    private int draws;
    private int losses;

    // Metodo que atualiza os contadores com base no resultado da rodada
    public void register(int resultado){
        switch (resultado){
            case 1 -> wins++;
            case 0 -> draws++;
            case -1 -> losses++;
        }
    }

    public int totalRounds(){
        return wins + losses + draws;
    }

    public double winRate(){
        return totalRounds() == 0 ? 0.0 : wins * 100 / totalRounds();
    }

    @Override
    public String toString(){
        return "V: " + wins + "E: " + draws + "D: " + losses;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }
}
