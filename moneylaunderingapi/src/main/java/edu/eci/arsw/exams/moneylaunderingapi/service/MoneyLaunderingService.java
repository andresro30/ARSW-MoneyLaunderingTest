package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;

import java.util.List;

public interface MoneyLaunderingService {
    void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException;
    SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException;
    List<SuspectAccount> getSuspectAccounts() throws MoneyLaunderingException;

    void addSuspectAccount(SuspectAccount account) throws MoneyLaunderingException;
}
