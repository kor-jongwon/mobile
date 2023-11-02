package com.example.mobile;

public enum api_url {
    LOGIN("http://3.36.113.184/login"),
    SIGNUP("http://3.36.113.184/signup"),
    IDDUPLICATE("http://3.36.113.184/duplicated"),
    FINDID("http://3.36.113.184/findid");

    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
