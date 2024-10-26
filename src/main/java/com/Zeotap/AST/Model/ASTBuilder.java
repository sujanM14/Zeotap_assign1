package com.Zeotap.AST.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ASTBuilder {
    
    // Tokenize the input string based on the pattern used in Python code
    public static List<String> tokenize(String ruleString) {
        // System.out.println(ruleString);
        Pattern pattern = Pattern.compile("\\s*(=>|<=|==|!=|>=|>|<|AND|OR|\\(|\\)|'[^']*'|\"[^\"]*\"|[\\w_]+\\s*[=><!]+\\s*[\\w_]+|[\\w_]+)\\s*");
        Matcher matcher = pattern.matcher(ruleString);
        List<String> tokens = new ArrayList<>();
        
        while (matcher.find()) {
            String token = matcher.group().trim(); // Trim to remove any leading/trailing whitespace
            if (!token.isEmpty()) { // Check for non-empty tokens
                // System.out.println("token - " + token);
                tokens.add(token);
            }
        }
        return tokens;
    }

    // Helper method to create an operator node by combining operands with an operator
    private static void createOperatorNode(Stack<String> operators, Stack<ASTNode> operands) {
        String operator = operators.pop(); 
        ASTNode right = operands.pop(); 
        ASTNode left = operands.pop(); 
        operands.push(new ASTNode("operator", operator, left, right)); 
    }

    // Method to parse the rule string and create an AST
    public static ASTNode createRule(String ruleString) {
        List<String> tokens = tokenize(ruleString);
        System.out.println(tokens.size());
        Stack<String> operators = new Stack<>();
        Stack<ASTNode> operands = new Stack<>();

        Map<String, Integer> precedence = Map.of("AND", 2, "OR", 1);


        for (String token : tokens) {
            // System.out.println("token - "+token);
            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    createOperatorNode(operators, operands);
                }
                operators.pop(); // Remove '('
            } else if (precedence.containsKey(token)) {
                while (!operators.isEmpty() && precedence.getOrDefault(operators.peek(), 0) >= precedence.get(token)) {
                    createOperatorNode(operators, operands);
                }
                operators.push(token);
            } else {
                operands.push(new ASTNode("operand",token));
            }
        }

        while (!operators.isEmpty()) {
            createOperatorNode(operators, operands);
        }

        return operands.pop();
    }
    public void helper(){
        System.out.println("hello");
    }

    // Combine multiple rules into one AST
    public static ASTNode combineRules(List<String> rules) {
        Map<String, Integer> operatorCount = new HashMap<>();
        operatorCount.put("AND", 0);
        operatorCount.put("OR", 0);

        // Count occurrences of AND and OR in the rules
        for (String rule : rules) {
            List<String> tokens = tokenize(rule);
            for (String token : tokens) {
                if (operatorCount.containsKey(token)) {
                    operatorCount.put(token, operatorCount.get(token) + 1);
                }
            }
        }

        // Determine the main operator (OR if it is more common, otherwise AND)
        String mainOperator = (operatorCount.get("OR") >= operatorCount.get("AND")) ? "OR" : "AND";

        // Combine all rules into a single AST
        Stack<ASTNode> combinedOperands = new Stack<>();
        for (String rule : rules) {
            combinedOperands.push(createRule(rule));
        }

        ASTNode root = combinedOperands.pop();
        while (!combinedOperands.isEmpty()) {
            ASTNode nextOperand = combinedOperands.pop();
            root = new ASTNode("operator", mainOperator, root, nextOperand);
        }

        return root; // Return the root of the combined AST
    }

  

    public static boolean evaluateNode(ASTNode node, Map<String, String> data) {
        if ("operator".equals(node.getType())) {
            boolean leftEval = evaluateNode(node.getLeft(), data);
            boolean rightEval = evaluateNode(node.getRight(), data);
    
            return switch (node.getCondition()) {
                case "AND" -> leftEval && rightEval;
                case "OR" -> leftEval || rightEval;
                default -> throw new IllegalStateException("Unexpected operator: " + node.getCondition());
            };
        } else if ("operand".equals(node.getType())) {
            String condition = node.getCondition().trim();
            
            // Regex to capture left operand, operator, and right operand with flexible spacing
            Pattern pattern = Pattern.compile("(.+?)\\s*(==|!=|>=|<=|>|<)\\s*(.+)");
            Matcher matcher = pattern.matcher(condition);
    
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid operand format: " + condition);
            }
    
            String left = matcher.group(1).trim();
            String operator = matcher.group(2).trim();
            String right = matcher.group(3).trim();
    
            // Ensure that `data` has the key for the condition's left operand
            if (!data.containsKey(left)) {
                throw new IllegalArgumentException("Data key not found: " + left);
            }
    
            String leftValue = data.get(left);
    
            // Check if the right side is numeric to decide between numeric or string comparison
            if (right.matches("-?\\d+(\\.\\d+)?")) { // Numeric comparison
                double rightValue = Double.parseDouble(right);
                double leftNum;
                try {
                    leftNum = Double.parseDouble(leftValue); // Convert leftValue to double
                } catch (NumberFormatException e) {
                    return false; // Left value is not a number, so return false for numeric comparisons
                }
    
                return switch (operator) {
                    case "==" -> leftNum == rightValue;
                    case "!=" -> leftNum != rightValue;
                    case ">" -> leftNum > rightValue;
                    case "<" -> leftNum < rightValue;
                    case ">=" -> leftNum >= rightValue;
                    case "<=" -> leftNum <= rightValue;
                    default -> throw new IllegalStateException("Unexpected comparison: " + operator);
                };
            } else { // String comparison
                String rightValue = right.replace("'", "").replace("\"", "");
                return switch (operator) {
                    case "==" -> leftValue.equals(rightValue);
                    case "!=" -> !leftValue.equals(rightValue);
                    default -> throw new IllegalStateException("Unexpected comparison: " + operator);
                };
            }
        } else {
            throw new IllegalStateException("Unexpected node type: " + node.getType());
        }
    }
    
}    