package com.example;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class BookController {
    private DynamoDbClient dynamoDbClient;
    public BookController(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Post("/books")
    public BookSaved save(@Valid @Body Book book) {
        BookSaved bookSaved = new BookSaved();
        bookSaved.setName(book.getName());
        bookSaved.setIsbn(UUID.randomUUID().toString());
        Map<String, AttributeValue> items = new HashMap<>();
        items.put("id", AttributeValue.builder().s(bookSaved.getIsbn()).build());
        items.put("name", AttributeValue.builder().s(bookSaved.getName()).build());
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("SUraj-Test-Greetings-Table")
                .item(items)
                .build());
        return bookSaved;
    }
}
