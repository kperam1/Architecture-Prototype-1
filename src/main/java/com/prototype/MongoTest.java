package com.prototype;

import com.mongodb.client.*;
import org.bson.Document;
import java.time.Duration;
import java.time.Instant;

public class MongoTest {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("agile_re");
            MongoCollection<Document> collection = database.getCollection("user_story");

            System.out.println("Connected to MongoDB!");

            // Clear collection
            collection.drop();

            int numStories = 1000;

            // Insert Benchmark
            Instant startInsert = Instant.now();
            for (int i = 0; i < numStories; i++) {
                Document story = new Document("title", "Story " + i)
                        .append("description", "Description for story " + i)
                        .append("priority", (i % 2 == 0) ? "High" : "Low");
                collection.insertOne(story);
            }
            Instant endInsert = Instant.now();

            // Fetch Benchmark
            Instant startFetch = Instant.now();
            long count = collection.countDocuments();
            for (Document doc : collection.find()) {
                // iterate without printing, just to simulate fetch
            }
            Instant endFetch = Instant.now();

            // Print timings
            System.out.println("\n⏱ Mongo Insert Time: " +
                    Duration.between(startInsert, endInsert).toMillis() + " ms");
            System.out.println("⏱ Mongo Fetch Time: " +
                    Duration.between(startFetch, endFetch).toMillis() + " ms");
            System.out.println("Total stories fetched: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
