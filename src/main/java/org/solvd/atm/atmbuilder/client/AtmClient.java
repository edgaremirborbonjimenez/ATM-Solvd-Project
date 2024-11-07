package org.solvd.atm.atmbuilder.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.atmbuilder.builders.ATMUSA;
import org.solvd.atm.atmbuilder.builders.AtmBuilder;
import org.solvd.atm.atmbuilder.director.AtmDirector;
import org.solvd.atm.atmbuilder.products.AbstractAtmMachine;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtmClient {
    private static final Logger logger = LogManager.getLogger();
    private static AtmClient atmClient;
    static Stack<AbstractAtmMachine> atmList;
    ExecutorService executorService;
    int poolSize;

    private AtmClient(){
        Properties properties = new Properties();
        try (FileReader input = new FileReader("src/main/resources/env.properties")) {
            properties.load(input);
            executorService = Executors.newFixedThreadPool(Integer.parseInt(properties.getProperty("thread.pool")));
            atmList = new Stack<>();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public static AtmClient getInstance(){
        if(atmClient == null){
            atmClient = new AtmClient();
        }
        return atmClient;
    }

    public void startNewATM(){
        if(!atmList.isEmpty()){
            atmList.peek().pauseExecution();
        }
        AtmBuilder atmUSA = ATMUSA.getInstance();
        AtmDirector.getInstance().makeATM(atmUSA);
        AbstractAtmMachine atm = atmUSA.getResult();
        atmList.push(atm);
        executorService.submit(atm::run);
    }

    public void stopATM(){
        if(!atmList.isEmpty()){
            atmList.pop();

            if(!atmList.isEmpty()){
                atmList.peek().resumeExecution();
            }
        }
    }
}
