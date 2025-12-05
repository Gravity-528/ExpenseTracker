package com.Project.ExpenseTracker.repository;

import com.Project.ExpenseTracker.entities.UserInfo;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserInfo,Long> {
    public UserInfo findByUsername(String username);
}
