package com.quickbase;

import com.quickbase.devint.ConcreteStatService;
import com.quickbase.devint.CountryInfo;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main( String args[] ) {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c ) {
            System.out.println("failed.");
            System.exit(1);
        }

        List<CountryInfo> countriesFromSQL = dbm.getInfoFromDB(c);

        ConcreteStatService csservice = new ConcreteStatService();
        List<Pair<String, Integer>> countriesFromAPI = csservice.GetCountryPopulations();

        Map<String, Integer> countryPopulation = aggregateCountryLists(countriesFromSQL, countriesFromAPI);

        System.out.println();
        System.out.println("|  Country  |  Population  |");
        countryPopulation.forEach((key, value) -> {
            System.out.println(key + " : "+value);
        });

    }

    private static Map<String, Integer> aggregateCountryLists(List<CountryInfo> fromSql, List<Pair<String, Integer>> fromApi) {
        Map<String, Integer> allCountries = new HashMap<>();

        for(CountryInfo country : fromSql) {
            allCountries.put(country.getCountryName(), country.getCountryPopulation());
        }

        for(Pair<String, Integer> country : fromApi) {
            if(!allCountries.containsKey(country.getLeft())) {
                if(country.getLeft().equals("United States of America")
                        && (allCountries.containsKey("U.S.A.")
                        || allCountries.containsKey("United States of America"))) {
                    continue;
                }
                allCountries.put(country.getLeft(), country.getRight());
            }
        }

        return allCountries;
    }
}