package edu.eci.arsw.exams.moneylaunderingapi.service;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import edu.eci.arsw.exams.moneylaunderingapi.model.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParametersException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service("activos")
public class MoneyLaunderingServiceStub implements MoneyLaunderingService {

    List<SuspectAccount> accounts = new CopyOnWriteArrayList<>();

    public MoneyLaunderingServiceStub(){
        accounts.add(new SuspectAccount("1",23));
        accounts.add(new SuspectAccount("2",12));
        accounts.add(new SuspectAccount("3",30));
    }



    @Override
    public void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException{
        SuspectAccount cuenta = null;
        if(accounts.contains(suspectAccount)){
            cuenta = suspectAccount;
            cuenta.setAmountOfSmallTransactions(suspectAccount.getAmountOfSmallTransactions());
        }
        else{
            throw new MoneyLaunderingException("La cuenta no existe");
        }


    }

    @Override
    public SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException {
        System.out.println("consultando cuenta");
        for(SuspectAccount account:accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        throw new MoneyLaunderingException("La cuenta no existe");
    }

    @Override
    public List<SuspectAccount> getSuspectAccounts() throws MoneyLaunderingException{
        return accounts;
    }

    @Override
    public void addSuspectAccount(SuspectAccount account) throws MoneyLaunderingException{
        for(SuspectAccount cuenta: accounts){
            if(cuenta.getAccountId().equals(account.getAccountId())){
                if(cuenta.getAmountOfSmallTransactions()!=account.getAmountOfSmallTransactions()){
                    throw new MoneyLaunderingException("Una vez registrado una cuenta bancaria, no se permita que se registre de nuevo con un numero de transacciones fraudulentas diferente.");
                }
            }
        }
        accounts.add(account);
    }
}
