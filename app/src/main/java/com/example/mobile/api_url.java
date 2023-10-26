package com.example.mobile;

public enum api_url {
    LOGIN("http://54.180.2.124:30001/login"),
    SIGNUP("http://54.180.2.124:30001/signup"),
    IDDUPLICATE("http://54.180.2.124:30001/duplicated"),
    FINDID("http://54.180.2.124:30001/findid");

    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
