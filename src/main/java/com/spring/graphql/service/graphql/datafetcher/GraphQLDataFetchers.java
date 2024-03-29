package com.spring.graphql.service.graphql.datafetcher;

import com.google.common.collect.ImmutableMap;
import com.spring.graphql.service.GraphqlInterface;
import com.spring.graphql.util.aop.graphql.GraphqlComponent;
import com.spring.graphql.util.aop.graphql.GraphqlMethodAnnotation;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：JackieChan
 * @date ：2019/8/9 22:12
 * @version: 1.0
 * @description：graphql数据获取（每个方法建立不同的datafetcher）
 */
@GraphqlComponent
public class GraphQLDataFetchers implements GraphqlInterface {
    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    @GraphqlMethodAnnotation(typeName = "Query",dataFetcherName = "getBookByIdDataFetcher")
    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
    public DataFetcher getBooksDataFetcher() {
        return dataFetchingEnvironment -> {
//            Map<String, String> book = dataFetchingEnvironment.getSource();
//            String authorId = book.get("authorId");
            return books;
        };
    }

}
