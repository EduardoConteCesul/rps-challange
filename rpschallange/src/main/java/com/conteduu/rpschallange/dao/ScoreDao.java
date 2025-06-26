package com.conteduu.rpschallange.dao;


import com.conteduu.rpschallange.model.Score;
import com.conteduu.rpschallange.util.MongoConfig;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

//Data Access Object
public class ScoreDao {
    // Metodo save(score), vai salvar o score no banco
    // Metodo list(), vai devolver List<String> prontas para exibir num listView
    // O metodo List deveria retornar uma lista de scores.

    private final MongoCollection<Document> collection = MongoConfig.scores();

    public void save(Score score){

        if (score == null) return;
        collection.insertOne(
        new Document("date", score.getDate()).
                append("wins", score.getWins()).
                append("draws", score.getDraws()).
                append("losses", score.getLosses()).
                append("winRate", score.getWinRate()).
                append("window", score.getWindow())
        );
    }

    public List<String> list(){

        List<String> scores = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        documents.forEach((document) ->
                    scores.add("Data " + document.getString("date") +
                        " Vitorias: " + document.getInteger("wins") +
                        " Empates: " + document.getInteger("draws") +
                        " Derrotas: " +document.getInteger("losses"))
        );
        return scores;
    }

}
