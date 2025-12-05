package com.Project.ExpenseTracker.util;

public class VailidationUser {
    public static Boolean checkPassword(String password){
        if(password.length()<8){
            return false;
        }
        if(password.contains("ab")){
            return false;
        }
        return true;
    }
}
