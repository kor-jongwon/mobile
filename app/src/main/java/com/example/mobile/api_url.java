package com.example.mobile;

public enum api_url {
    LOGIN("http://3.36.113.184:30001/login"),
    SIGNUP("http://3.36.113.184:30001/signup"),
    IDDUPLICATE("http://3.36.113.184:30001/duplicated"),
    FINDID("http://3.36.113.184:30001/findid"),
    SENSORDUPLICATE("http://3.36.113.184:30001/duplicated_sensor_id"),
    REGISTPLANT("http://3.36.113.184:30001/addplant"),
    UPDATEPLANT("http://3.36.113.184:30001/updatePlant"),
    GETPLANTS("http://3.36.113.184:30001/getPlantsBySessionUserId"),
    GETPLANT("http://3.36.113.184:30001/getPlantByName"),
    DELETEPLANT("http://3.36.113.184:30001/deletePlant"),
    PLANTDUPLICATE("http://3.36.113.184:30001/duplicatePlantName");




    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
