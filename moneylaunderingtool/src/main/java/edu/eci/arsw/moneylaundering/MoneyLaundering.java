package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoneyLaundering {
    private TransactionAnalyzer transactionAnalyzer;
    private TransactionReader transactionReader;
    private static int amountOfFilesTotal;
    private static AtomicInteger amountOfFilesProcessed;
    private static ArrayList<MoneyLaunderingThread> threads;
    public static boolean pause;
    public static Object monitor;

    public MoneyLaundering() {
        transactionAnalyzer = new TransactionAnalyzer();
        transactionReader = new TransactionReader();
        amountOfFilesProcessed = new AtomicInteger();
        pause = false;
        monitor = new Object();
    }

    public static boolean getPause() {
        return pause;
    }

    public void processTransactionData(int numberOfThreads) {
        amountOfFilesProcessed.set(0);
        List<File> transactionFiles = getTransactionFileList();
        amountOfFilesTotal = transactionFiles.size();

        for (File transactionFile : transactionFiles) {
            List<Transaction> transactions = transactionReader.readTransactionsFromFile(transactionFile);
            System.out.println("Nuevo archvio de tamaño: " + transactions.size());

            threads = new ArrayList<>();
            System.out.println("Archivos procesados " + amountOfFilesProcessed + "...");

            for (int i = 0; i < numberOfThreads; i++) {
                int inicio = i * (transactions.size() / numberOfThreads);
                int fin = (i + 1) * (transactions.size() / numberOfThreads);
                System.out.println(inicio + " " + fin);
                MoneyLaunderingThread hilo = new MoneyLaunderingThread(inicio, fin, transactions, transactionAnalyzer);
                hilo.start();
                threads.add(hilo);
            }


            for(MoneyLaunderingThread thread: threads){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Cantidad de threads: " + threads.size());
            threads.clear();
            System.out.println(threads.size());
            amountOfFilesProcessed.incrementAndGet();
        }
        System.out.println("Termine................!!!!!!!!!");
    }

    public List<String> getOffendingAccounts() {
        return transactionAnalyzer.listOffendingAccounts();
    }

    private List<File> getTransactionFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public static void setPause() {
        pause = !pause;
    }

    private static void pausarHilos() {
        for (MoneyLaunderingThread hilo : threads) {
            try {
                hilo.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MoneyLaundering moneyLaundering = new MoneyLaundering();
        int numberOfThreads = 5;
        Thread processingThread = new Thread(() -> moneyLaundering.processTransactionData(numberOfThreads));
        processingThread.start();
        while (amountOfFilesProcessed.get()<=amountOfFilesTotal) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("exit")) {
                break;
            }
            if (line.isEmpty()) {
                if (pause) {
                    System.out.println("corriendo");
                    pause = false;
                    synchronized (monitor) {
                        monitor.notifyAll();
                    }
                } else {
                    pause = true;
                    System.out.println("pausado");
                }
                //System.out.println(pause);
            }

            String message = "Processed %d out of %d files.\nFound %d suspect accounts:\n%s";
            List<String> offendingAccounts = moneyLaundering.getOffendingAccounts();
            String suspectAccounts = offendingAccounts.stream().reduce("", (s1, s2) -> s1 + "\n" + s2);
            message = String.format(message, moneyLaundering.amountOfFilesProcessed.get(), moneyLaundering.amountOfFilesTotal, offendingAccounts.size(), suspectAccounts);
            System.out.println(message);
        }
        System.out.println("Salí del while");

    }


}
