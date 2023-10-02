package com.example.mobile;

public enum api_url {
    LOGIN("http://172.30.1.8:3000/login"),
    SIGNUP("http://172.30.1.8:3000/signup"),
    IDDUPLICATE("http://172.30.1.8:3000/duplicated"),
    FINDID("http://172.30.1.8:3000/findid");

    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
