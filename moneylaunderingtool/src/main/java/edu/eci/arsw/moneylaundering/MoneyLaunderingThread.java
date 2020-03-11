package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MoneyLaunderingThread extends Thread {
    private TransactionAnalyzer transactionAnalyzer;
    private List<Transaction> transactions;
    private int inicio;
    private int fin;

    public MoneyLaunderingThread(int a,int b,List<Transaction> transactions){
        transactionAnalyzer = new TransactionAnalyzer();
        this.transactions = transactions;
        inicio = a;
        fin = b;
    }

    @Override
    public void run(){
        System.out.println("entre");
        for(int i=inicio;i<fin;i++) {
            transactionAnalyzer.addTransaction(transactions.get(i));
        }
    }

}
