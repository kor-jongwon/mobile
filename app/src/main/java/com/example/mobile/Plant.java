package com.example.mobile;
public class Plant {
    private String plantId;
    private String plantName;
    private String plantDating;
    private String image;

    public String getImage() {
        return image;
    }

    public String getPlantDating() {
        return plantDating;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPlantDating(String plantDating) {
        this.plantDating = plantDating;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}