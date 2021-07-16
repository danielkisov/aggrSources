package com.quickbase.devint;

import java.util.ArrayList;
import java.util.List;

public class CountryInfo {
    private int countryID;
    private String countryName;
    private int countryPopulation;
    private List<StateInfo> stateInfoList;

    public CountryInfo(int countryID, String countryName, List<StateInfo> stateInfoList) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.stateInfoList = stateInfoList;
    }

    public CountryInfo(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.stateInfoList = new ArrayList<>();
    }

    public int getCountryID() {
        return countryID;
    }

    private void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    private void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryPopulation() {
        return this.countryPopulation;
    }

    private void setCountryPopulation(int countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public List<StateInfo> getStateInfoList() {
        return stateInfoList;
    }

    private void setStateInfoList(List<StateInfo> stateInfoList) {
        this.stateInfoList = stateInfoList;
    }

    public void addStateToList(StateInfo stateInfo) {
        this.stateInfoList.add(stateInfo);
    }

    public void calcCountryPopulation() {
        int pop = 0;
        for(StateInfo state : this.getStateInfoList()) {
            state.calcStatePopulation();
            pop += state.getStatePopulation();
        }
        this.setCountryPopulation(pop);
    }
}
