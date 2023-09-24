package com.example.mobile;

public enum api_url {
    LOGIN("http://172.20.10.3:3000/login"),
    SIGNUP("http://172.20.10.3:3000/signup"),
    IDDUPLICATE("http://172.20.10.3:3000/duplicated");

    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
