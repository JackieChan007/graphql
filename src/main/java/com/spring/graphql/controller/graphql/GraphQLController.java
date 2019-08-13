package com.spring.graphql.controller.graphql;

import com.alibaba.fastjson.JSONObject;
import com.spring.graphql.service.graphql.GraphQLProvider;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：JackieChan
 * @date ：2019/8/9 22:21
 * @version: 1.0
 * @description：graphql通用接口
 */
@RestController("/graphql")
@Slf4j
public class GraphQLController {
    @Autowired
    private GraphQLProvider graphQLProvider;

    /**
     * get
     * @param body
     * @return
     */


    @RequestMapping(value = "/graphql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object executeOperation(@RequestBody Map body) {
        String query = (String) body.get("query");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        if (variables == null) {
            variables = new LinkedHashMap<>();
        }
        GraphQL graphQL=graphQLProvider.graphQL();
        ExecutionResult executionResult = graphQL.execute(query, (Object) null, variables);
        Map<String, Object> result = new LinkedHashMap<>();
        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors());
            log.error("Errors: {}", executionResult.getErrors());
        }
        result.put("data", executionResult.getData());
        return result;
    }


    /**
     * post
     * @param query
     * @return
     */
    @PostMapping
    public Map<String,Object> postGraphql(@RequestBody String query){
        JSONObject jsonObject= JSONObject.parseObject(query);
        String queryBody= jsonObject.getString("query");
        ExecutionInput executionInput=ExecutionInput.newExecutionInput().query(queryBody).build();
        GraphQL build =graphQLProvider.graphQL();
        ExecutionResult executionResult=build.execute(executionInput);
        return executionResult.toSpecification();
    }

}
