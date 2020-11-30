package com.wist.tensquare.spit.test;

import com.mongodb.client.*;
import org.bson.Document;

public class SpitTest {
/*    public static void main(String[] args) {
        MongoClient client= MongoClients.create("mongodb://192.168.88.130:27017");
        MongoDatabase spitdb = client.getDatabase("spitdb");
        MongoCollection<Document> spit = spitdb.getCollection("spit");
        FindIterable<Document> documents = spit.find();
        for (Document document1 : documents) {
            System.out.println("编号："+document1.getObjectId("_id"));
            System.out.println("内容："+document1.getString("content"));
            System.out.println("用户Id："+document1.getString("userid"));
        }
        client.close();
    }*/
}
