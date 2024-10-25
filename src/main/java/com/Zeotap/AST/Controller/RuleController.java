package com.Zeotap.AST.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Zeotap.AST.Model.Rule;
import com.Zeotap.AST.Service.RuleService;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    // Create a new rule from an expression
    @PostMapping("/create")
    public ResponseEntity<Rule> createRule(@RequestBody String expression) {
        return new ResponseEntity<>(ruleService.save(expression), HttpStatus.CREATED);
    }

    // Delete a rule by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable String id) {
        ruleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Get a rule by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Rule> getRuleById(@PathVariable String id) {
        Rule rule = ruleService.findById(id);
        return new ResponseEntity<>(rule, HttpStatus.OK);
    }

    // Get all rules
    @GetMapping("/all")
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = ruleService.findAll();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    // Evaluate a rule against JSON data by rule ID
    @PostMapping("/evaluate/{id}")
    public ResponseEntity<Boolean> evaluateRule(@PathVariable String id, @RequestBody Map<String, String> jsonData) {
        Boolean result = false;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
