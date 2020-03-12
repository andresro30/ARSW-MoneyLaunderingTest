package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MoneyLaunderingThread extends Thread {
    private TransactionAnalyzer transactionAnalyzer;
    private List<Transaction> transactions;
    private int inicio;
    private int fin;

    public MoneyLaunderingThread(int a,int b,List<Transaction> transactions,TransactionAnalyzer transactionAnalyzer){
        this.transactionAnalyzer = transactionAnalyzer;
        this.transactions = transactions;
        inicio = a;
        fin = b;
    }

    @Override
    public void run(){
        for(int i=inicio;i<fin;i++) {

                synchronized (MoneyLaundering.monitor){
                    if(MoneyLaundering.getPause()){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
            transactionAnalyzer.addTransaction(transactions.get(i));
        }

    }

    public synchronized void reanudar(){
        notifyAll();
        this.start();
    }

}
