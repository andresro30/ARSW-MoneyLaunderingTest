package edu.eci.arsw.exams.moneylaunderingapi.model;

import org.springframework.scheduling.support.SimpleTriggerContext;

public class SuspectAccount {
    public String accountId;
    public int amountOfSmallTransactions;

    public SuspectAccount(String accountId, int amountOfSmallTransactions){
        this.accountId = accountId;
        this.amountOfSmallTransactions = amountOfSmallTransactions;
    }

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public int getAmountOfSmallTransactions(){
        return amountOfSmallTransactions;
    }

    public void setAmountOfSmallTransactions(int valor){
        this.amountOfSmallTransactions = valor;
    }

}
