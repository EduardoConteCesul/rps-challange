package com.conteduu.rpschallange.model;

/*
    Representa o resumo final de uma partida (Apos 5 rodadas sera armazenada no mongo)

*/

public class Score {
    private String id;
    private String date;
    private int wins;
    private int draws;
    private int losses;
    private double winRate; // Percentual de vitorias
    private int window; // Qual é o range de otimizacao

    public Score(String date, int wins, int draws, int losses, double winRate, int window) {
        this.date = date;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.winRate = winRate;
        this.window = window;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public int getWindow() {
        return window;
    }

    public void setWindow(int window) {
        this.window = window;
    }
}
