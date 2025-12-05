package com.Project.ExpenseTracker.models;

import com.Project.ExpenseTracker.entities.UserInfo;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UserInfoDto extends UserInfo {
    private String userName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
