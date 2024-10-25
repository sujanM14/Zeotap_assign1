package com.Zeotap.AST.Model;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Rule")
public class Rule {

    @Id
    private String id;  // MongoDB's _id field
    private String ruleString;  // To store the original rule string
    private Map<String, Object> astNode;  // To store the AST as a nested Map structure

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public Map<String, Object> getAstNode() {
        return astNode;
    }

    public void setAstNode(Map<String, Object> astNode) {
        this.astNode = astNode;
    }
}
