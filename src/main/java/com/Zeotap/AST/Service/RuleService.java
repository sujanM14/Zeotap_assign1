package com.Zeotap.AST.Service;

import java.util.List;
import java.util.Map;

import com.Zeotap.AST.Model.Rule;

public interface RuleService {
    // Method to parse an expression and save the created rule
    Rule save(String expression);

    // Rule createRule(String expression);
    // Method to delete a rule by its ID
    void delete(String id);
    
    // Method to find a rule by its ID
    Rule findById(String id);

    // Method to retrieve all rules from the database
    List<Rule> findAll();

    // Method to evaluate a rule against given JSON data
    Boolean evaluateRuleById(String id, Map<String, String> data);
}
