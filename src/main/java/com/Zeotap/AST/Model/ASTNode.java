package com.Zeotap.AST.Model;

import java.util.HashMap;
import java.util.Map;

public class ASTNode {

    private String type;
    private String condition;
    private ASTNode left;
    private ASTNode right;

    public ASTNode(){}
    // Constructor for leaf nodes (operands)
    public ASTNode(String type, String condition) {
        this.type = type;
        this.condition = condition;
        this.left=null;
        this.right=null;
    }

    // Constructor for operator nodes with left and right children
    public ASTNode(String type, String condition, ASTNode left, ASTNode right) {
        this.type = type;
        this.condition = condition;
        this.left = left;
        this.right = right;
    }

    // Getter methods
    public String getType() {
        return type;
    }

    public String getCondition() {
        return condition;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    // Convert node to a map for easy traversal or conversion to JSON
    public Map<String, Object> toMap() {
        Map<String, Object> nodeMap = new HashMap<>();
        
        // Add node type (operator or operand)
        nodeMap.put("type", type);
        
        // Add condition (operator value like AND/OR, or operand condition like age > 30)
        nodeMap.put("condition", condition);
        
        // Recursively map left and right children if they exist
        nodeMap.put("left", (left != null) ? left.toMap() : null);
        nodeMap.put("right", (right != null) ? right.toMap() : null);
        
        return nodeMap;
    }

    // Override toString for easier debugging (prints the AST structure)
    @Override
    public String toString() {
        return toMap().toString(); // Convert node structure to string
    }
    
    public static ASTNode fromMap(Map<String, Object> data) {
        ASTNode node = new ASTNode((String) data.get("type"), (String) data.get("condition")); // Use "condition" instead of "value"
        if (data.get("left") != null) node.left = ASTNode.fromMap((Map<String, Object>) data.get("left"));
        if (data.get("right") != null) node.right = ASTNode.fromMap((Map<String, Object>) data.get("right"));
        return node;
    }

}
