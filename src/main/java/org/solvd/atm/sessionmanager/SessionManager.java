package org.solvd.atm.sessionmanager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    static SessionManager sessionManager;
    static ConcurrentHashMap<String,Integer> activeAccounts;
    static ConcurrentHashMap<String,String> activeATMs;

    private SessionManager(){}

    public static SessionManager getInstance(){
        if(sessionManager == null){
            sessionManager = new SessionManager();
            activeAccounts = new ConcurrentHashMap<>();
            activeATMs = new ConcurrentHashMap<>();

        }
        return sessionManager;
    }

    public boolean login(String accountNumber,String atmSerie){
        boolean loginSuccessfully = activeAccounts.putIfAbsent(accountNumber,0) == null;
        if(loginSuccessfully){
            activeATMs.put(atmSerie,atmSerie);
        }
        return loginSuccessfully;
    }

    public boolean isActive(String accountNumber){
        return this.activeAccounts.get(accountNumber) != null;
    }

    public void logout(String accountNumber,String atmSerie){
        activeATMs.remove(atmSerie);
        activeAccounts.remove(accountNumber);
    }

    public Integer getSessionId(String accountNumber){
        return activeAccounts.get(accountNumber);
    }

    public static void assignIdToAccount(String accountNumber,Integer id){
        activeAccounts.replace(accountNumber,id);
    }

    public List<String> getAllActiveATMs(){
        return activeATMs.values().stream().toList();
    }
}
