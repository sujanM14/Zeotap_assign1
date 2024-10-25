package com.Zeotap.AST.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Zeotap.AST.Model.Rule;
@Repository
public interface RuleRepository extends MongoRepository<Rule, String> {
    // Custom query methods can be added here if needed
}
