package com.Zeotap.AST.Implementation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Zeotap.AST.Model.ASTBuilder;
import com.Zeotap.AST.Model.ASTNode;
import com.Zeotap.AST.Model.Rule;
import com.Zeotap.AST.Repository.RuleRepository;
import com.Zeotap.AST.Service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    private final ASTBuilder astBuilder;

    
    public RuleServiceImpl(RuleRepository ruleRepository, ASTBuilder astBuilder) {
        this.ruleRepository = ruleRepository;
        this.astBuilder = astBuilder;
    }

    @Override
    public Rule save(String expression) {
        ASTNode ast = astBuilder.createRule(expression);
        // astBuilder.helper();
        Rule rule = new Rule();
        rule.setRuleString(expression);
        Map<String,Object> mp=ast.toMap();
        // System.out.println(mp);
        rule.setAstNode(mp);
       

        return ruleRepository.save(rule);
    }

    @Override
    public void delete(String id) {
        ruleRepository.deleteById(id);
    }

    @Override
    public Rule findById(String id) {
        Optional<Rule> rule = ruleRepository.findById(id);
        return rule.orElseThrow(() -> new RuntimeException("Rule not found with id: " + id));
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Boolean evaluateRuleById(String id, Map<String, String> data) {
        Rule rule = findById(id); // Retrieve the rule by ID
        if (rule == null) {
        throw new IllegalArgumentException("Rule not found for ID: " + id);
       }

       Map<String, Object> mp = rule.getAstNode(); // Get the AST node map from the rule
       ASTNode node = ASTNode.fromMap(mp); // Convert the map to an ASTNode

    // Evaluate the AST node with the provided data
    Boolean evaluation = ASTBuilder.evaluateNode(node,data);

    return evaluation;  // Logic for evaluating will go here
    }
}
