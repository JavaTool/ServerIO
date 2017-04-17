package com.fanxing.server.account;

import java.util.Map;

import com.google.common.collect.Maps;

public class AccountFactorys {
	
    private static class SingletonHolder {
    	
        private static AccountFactorys instance = new AccountFactorys();
        
    }
    
    public static AccountFactorys getInstance() {
        return SingletonHolder.instance;
    }
    
    private AccountFactorys() {
    	factorys = Maps.newHashMap();
    }
    
    private Map<AccountType, IAccountFactory> factorys;
    
    public void addFactory(AccountType type, IAccountFactory factory) {
    	factorys.put(type, factory);
    }
    
    public IAuthenticateAccount createAccount(AccountType type, String value) {
    	return factorys.get(type).createAccount(value);
    }

}
