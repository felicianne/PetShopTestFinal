package com.btg.PetShopTestFinal.utils;

public class Validador {


    public static Boolean passwordValidate(String password){
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$");
    }

    public static Boolean emailValidate(String email){
        return email.matches("^\\S{3,}@\\w{2,}\\.[a-zA-Z]{2,}$");
    }

    public static Boolean name(String name){
        return name.matches("^\\p{L}{3,}(\\s\\p{L}+)*$");
    }
}


