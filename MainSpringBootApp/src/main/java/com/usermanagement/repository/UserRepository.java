package com.usermanagement.repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public User save(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    public User getUserById(String userid) {
        return dynamoDBMapper.load(User.class, userid);
    }

    public User getUserByEmail(String email) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("email = :email")
                .withExpressionAttributeValues(Map.of(":email", new AttributeValue(email)));

        PaginatedScanList<User> users = dynamoDBMapper.scan(User.class, scanExpression);
        return users.isEmpty() ? null : users.get(0);
    }


    //public User getUserByEmail(String email) {
    //    return dynamoDBMapper.load(User.class, email);
    //}

    public void delete(String userid) {
        User user = dynamoDBMapper.load(User.class, userid);
        dynamoDBMapper.delete(user);
    }
}







