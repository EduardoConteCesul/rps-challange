package com.conteduu.rpschallange.util;

/*
    "IA adaptativa"
    - Manter as N (window) ultimas jogadas do HUMANO
    - Contar qual jogada aparece mais nesse intervalo
    - Devolver a jogada que vence essa mais frequente
*/

import com.conteduu.rpschallange.model.Move;

import java.util.ArrayDeque;
import java.util.HashMap;

public class CpuStratagy {
    /*
        Historico "circular" das jogadas do player
        Usar ArrayDeque -> addLast / removeFirst

        Variavel int com o tamnho da "janela"

        Construtor que inicializa a janela de jogadas

        Metodo que vai ser responsavel por calcular qual a
        jogada a CPU deve fazer agora

    * */

    ArrayDeque<Move> historic = new ArrayDeque<>();

    HashMap<Move, Integer> qntdJogadas = new HashMap<>(){{
            put(Move.PEDRA, 0);
            put(Move.PAPEL, 0);
            put(Move.TESOURA, 0);
    }};

    Move maisVezesApareceu = null;
    Integer qntdDeterminadaJogadaApareceu = 0;

    public Move proximaJogada(Move jogadaAtual, int window){
        /*
            Atualizar a Deque com a jogada mais recente
            Se passou o limite da janela, descartar o elemento
            mais antigo(removeFirst)

            Tratar se ainda é a primeira jogada

            Contar quantas vezes cada jogada aparece na janela
            Ex: se o mais frequente for PAPEL então retorna TESOURA

         */

        if (historic.size() < window) {
            historic.add(jogadaAtual);
            qntdJogadas.put(jogadaAtual, qntdJogadas.getOrDefault(jogadaAtual, 0) + 1);
            return Move.random();
        }



        for (Move move: qntdJogadas.keySet()){

            Integer qntdJogada = qntdJogadas.get(move);

            if ( qntdJogada > qntdDeterminadaJogadaApareceu){
                qntdDeterminadaJogadaApareceu = qntdJogada;
                maisVezesApareceu = move;
            }
        }

        switch (maisVezesApareceu){
            case PEDRA -> {
                removeFirstAddFinal(jogadaAtual);
                return Move.PAPEL;
            }
            case PAPEL -> {
                removeFirstAddFinal(jogadaAtual);
                return Move.TESOURA;
            }
            case TESOURA -> {
                removeFirstAddFinal(jogadaAtual);
                return Move.PEDRA;
            }
            default -> {
                return  null;
            }
        }
    }

    private void removeFirstAddFinal(Move jogadaAtual){
        Move primeiroLista = historic.getFirst();
        historic.removeFirst();
        historic.add(jogadaAtual);
        qntdJogadas.put(primeiroLista, qntdJogadas.getOrDefault(primeiroLista, 0) - 1);
        qntdJogadas.put(jogadaAtual, qntdJogadas.getOrDefault(jogadaAtual, 0) + 1);
        maisVezesApareceu = null;
        qntdDeterminadaJogadaApareceu = 0;
    }

    public void resetStrategy(){
        historic.clear();
        qntdJogadas.put(Move.PEDRA, 0);
        qntdJogadas.put(Move.PAPEL, 0);
        qntdJogadas.put(Move.TESOURA, 0);
    }
}
