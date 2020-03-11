package edu.eci.arsw.exams.moneylaunderingapi.model;

import edu.eci.arsw.exams.moneylaunderingapi.MoneyLaunderingController;

public class MoneyLaunderingException extends Exception {

    public MoneyLaunderingException(String msg){
        super(msg);
    }
}
