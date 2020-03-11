package edu.eci.arsw.exams.moneylaunderingapi;


import edu.eci.arsw.exams.moneylaunderingapi.model.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.service.MoneyLaunderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.MalformedParametersException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping( value = "/fraud-bank-accounts")
public class MoneyLaunderingController {

    @Autowired
    MoneyLaunderingService moneyLaunderingService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getSuspectAccounts(){
        try{
            return new ResponseEntity<>(moneyLaunderingService.getSuspectAccounts(), HttpStatus.ACCEPTED);
        }catch (MalformedParametersException | MoneyLaunderingException e){
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<?> addSuspectAccounts(@RequestBody SuspectAccount account) {
        try {
            moneyLaunderingService.addSuspectAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MalformedParametersException | MoneyLaunderingException e) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping( value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountsById(@PathVariable String id){
        try{
            return new ResponseEntity<>(moneyLaunderingService.getAccountStatus(id), HttpStatus.ACCEPTED);
        }catch (MalformedParametersException | MoneyLaunderingException ep){
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, ep);
            return new ResponseEntity<>(ep.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(  value = "/{accountId}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateSuspectAccounts(@PathVariable SuspectAccount account) {
        try {
            moneyLaunderingService.updateAccountStatus(account);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MoneyLaunderingException e) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
