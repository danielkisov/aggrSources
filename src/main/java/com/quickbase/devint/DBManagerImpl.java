package com.quickbase.devint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {
    public Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            System.out.println("Opened database successfully");


        } catch (ClassNotFoundException cnf) {
            System.out.println("could not load driver");
        } catch (SQLException sqle) {
            System.out.println("sql exception:" + sqle.getStackTrace());
        }
        return c;
    }
    //TODO: Add a method (signature of your choosing) to query the db for population data by country

    public List<CountryInfo> getInfoFromDB(Connection connection) {
        List<CountryInfo> countryInfoList = fillCountryList(connection);

        fillStateListsOfCountries(connection, countryInfoList);

        fillCityListsOfStates(connection, countryInfoList);

        for(CountryInfo country : countryInfoList) {
            country.calcCountryPopulation();
        }

        return countryInfoList;
    }

    private List<CountryInfo> fillCountryList(Connection connection) {
        List<CountryInfo> countryInfoList = new ArrayList<>();
        String queryForCountries = "SELECT CountryId, CountryName from Country";

        try(Statement st = connection.createStatement()){
            ResultSet countries = st.executeQuery(queryForCountries);

            while(countries.next()){
                int ctrID = countries.getInt("CountryId");
                String ctrName = countries.getString("CountryName");
                countryInfoList.add(new CountryInfo(ctrID, ctrName));
            }

        } catch (SQLException sqle) {
            System.out.println("sql exception:" + sqle.getStackTrace());
        }

        return countryInfoList;
    }

    private void fillStateListsOfCountries(Connection connection, List<CountryInfo> countryInfoList) {
        for (CountryInfo country : countryInfoList) {
            String queryForStates = "SELECT StateId, StateName, CountryId FROM State where CountryId = ?";

            try(PreparedStatement pst = connection.prepareStatement(queryForStates)) {
                pst.setInt(1, country.getCountryID());
                ResultSet states = pst.executeQuery();

                while(states.next()) {
                    int sID = states.getInt("StateId");
                    String sName = states.getString("StateName");

                    country.addStateToList(new StateInfo(sID, sName));
                }

            } catch(SQLException sqle) {
                System.out.println("sql exception:" + sqle.getStackTrace());
            }
        }
    }

    private void fillCityListsOfStates(Connection connection, List<CountryInfo> countries) {
        for(CountryInfo country : countries) {
            for(StateInfo state : country.getStateInfoList()) {
                String queryForCities = "SELECT CityId, CityName, StateId, Population FROM City where StateId = ?";

                try(PreparedStatement pst = connection.prepareStatement(queryForCities)) {
                    pst.setInt(1, state.getStateID());
                    ResultSet cities = pst.executeQuery();

                    while(cities.next()) {
                        int cityID = cities.getInt("CityId");
                        String cityName = cities.getString("CityName");
                        int cityPop = cities.getInt("Population");

                        state.addCityToList(new CityInfo(cityID, cityName, cityPop));
                    }

                } catch(SQLException sqle) {
                    System.out.println("sql exception:" + sqle.getStackTrace());
                }
            }
        }
    }

}
