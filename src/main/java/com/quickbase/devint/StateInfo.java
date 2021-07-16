package com.quickbase.devint;

import java.util.ArrayList;
import java.util.List;

public class StateInfo {
    private int stateID;
    private String stateName;
    private int statePopulation;
    private List<CityInfo> cityInfoList;

    public StateInfo(int stateID, String stateName, List<CityInfo> cityInfoList) {
        this.stateID = stateID;
        this.stateName = stateName;
        this.cityInfoList = cityInfoList;
    }

    public StateInfo(int stateID, String stateName) {
        this.stateID = stateID;
        this.stateName = stateName;
        this.cityInfoList = new ArrayList<>();
    }

    public int getStateID() {
        return stateID;
    }

    private void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    private void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getStatePopulation() {
        return this.statePopulation;
    }

    private void setStatePopulation(int statePopulation) {
        this.statePopulation = statePopulation;
    }

    public List<CityInfo> getCityInfoList() {
        return this.cityInfoList;
    }

    private void setCityInfoList(List<CityInfo> cityInfoList) {
        this.cityInfoList = cityInfoList;
    }

    public void addCityToList(CityInfo city) {
        this.cityInfoList.add(city);
    }

    public void calcStatePopulation() {
        int pop = 0;
        for(CityInfo city : this.getCityInfoList()) {
            pop += city.getCityPopulation();
        }
        this.setStatePopulation(pop);
    }
}
