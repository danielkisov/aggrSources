package com.quickbase.devint;

public class CityInfo {
    private int cityID;
    private String cityName;
    private int cityPopulation;

    public CityInfo(int cityID, String cityName, int cityPopulation) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.cityPopulation = cityPopulation;
    }

    public int getCityID() {
        return cityID;
    }

    private void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    private void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityPopulation() {
        return cityPopulation;
    }

    private void setCityPopulation(int cityPopulation) {
        this.cityPopulation = cityPopulation;
    }
}
