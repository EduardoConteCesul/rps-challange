package com.conteduu.rpschallange.model;

// Representa as 3 jogadas possiveis de jogo

public enum Move {
    PEDRA, PAPEL, TESOURA;

    /*
        - Metodo que compara a jogada atual com outra.
        - Retornos:
            -> Vitoria
            -> Empate
            -> Derota
     */

    public int versus(Move other){
        if (this == other) return 0;

        return (this == PEDRA && other == TESOURA) ||
                (this == PAPEL && other == PEDRA) ||
                (this == TESOURA && other == PAPEL) ? 1 : -1;
    }

    public static Move random(){
        return values()[(int) (Math.random() * 3)];
    }
}
