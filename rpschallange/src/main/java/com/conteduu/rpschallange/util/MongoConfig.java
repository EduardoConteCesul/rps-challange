package com.conteduu.rpschallange.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

public final class MongoConfig {
    private static final MongoClient CLIENT = MongoClients.create("mongodb://localhost:27017");

    public static MongoCollection<Document> scores(){
        return CLIENT.getDatabase("rps_db").getCollection("scores");
    }
}
