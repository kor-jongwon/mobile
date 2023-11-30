package com.example.mobile;

public enum api_url {
    LOGIN("http://3.36.113.184:30001/login"),
    SIGNUP("http://3.36.113.184:30001/signup"),
    IDDUPLICATE("http://3.36.113.184:30001/duplicated"),
    FINDID("http://3.36.113.184:30001/findid"),
    SENSORDUPLICATE("http://100.100.113.176:30001/duplicated_sensor_id"),

    ADDSENSOR("http://100.100.113.176:30001/addSensor"),


    PLANTDUPLICATE("http://3.36.113.184:30001/duplicatePlantName"),
    REGISTPLANT("http://3.36.113.184:30001/addplant"),
    GETPLANT("http://3.36.113.184:30001/getPlantByName"),
    GETPLANTS("http://3.36.113.184:30001/getPlantsBySessionUserId"),
    UPDATEPLANT("http://3.36.113.184:30001/updatePlant"),
    DELETEPLANT("http://3.36.113.184:30001/deletePlant");




    private final String value;

    api_url(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }

}
