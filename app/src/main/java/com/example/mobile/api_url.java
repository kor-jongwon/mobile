package com.example.mobile;

public enum api_url {
    LOGIN("http://43.200.129.182:30002/login"),
    SIGNUP("http://43.200.129.182:30002/signup"),
    IDDUPLICATE("http://43.200.129.182:30002/duplicated"),
    FINDID("http://43.200.129.182:30002/findid");

    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
