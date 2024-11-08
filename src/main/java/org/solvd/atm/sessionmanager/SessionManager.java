package org.solvd.atm.sessionmanager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    static SessionManager sessionManager;
    ConcurrentHashMap<String,Integer> activeAccounts;
    ConcurrentHashMap<String,String> activeATMs;

    private SessionManager(){}

    public static SessionManager getInstance(){
        if(sessionManager == null){
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public boolean login(String accountNumber,Integer id,String atmSerie){
        boolean loginSuccessfully = activeAccounts.putIfAbsent(accountNumber,id) != null;
        if(loginSuccessfully){
            activeATMs.put(atmSerie,atmSerie);
        }
        return loginSuccessfully;
    }

    public void logout(String accountNumber,String atmSerie){
        activeATMs.remove(atmSerie);
        activeAccounts.remove(accountNumber);
    }

    public Integer getSessionId(String accountNumber){
        return activeAccounts.get(accountNumber);
    }

    public List<String> getAllActiveATMs(){
        return activeATMs.values().stream().toList();
    }
}
