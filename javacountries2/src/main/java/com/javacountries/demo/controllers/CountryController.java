package com.javacountries.demo.controllers;

import com.javacountries.demo.models.Country;
import com.javacountries.demo.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//http://localhost:2019/names/all
//http://localhost:2019/names/start/u
//http://localhost:2019/population/total
//http://localhost:2019/population/min
//http://localhost:2019/population/max
@RestController
public class CountryController
{
    @Autowired
    CountryRepository countryrepos;
    //Creating the endpoints using @GetMapping

    private List <Country> findCountries(List<Country> countriesList, CountryChecker tester )
    {
        List<Country> tempList = new ArrayList<>();
        for(Country c : countriesList)
        {
            if(tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }

    //http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    //Response Entity working with a method do get all the names
    public ResponseEntity<?> listAllCountryNames()
    {
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }



    //http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> listAllCountriesByLetter(@PathVariable char letter)
    {
        List<Country> countryLetterList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryLetterList::add);
        List<Country> returnList = findCountries(countryLetterList,e -> e.getName().charAt(0) == letter );
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

    //http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> totalPopulationCount()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        double total = 0.0;
        for(Country c : myList)
        {
            total = total + c.getPopulation();
        }

        System.out.println("The total population is " + total);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> populationMinimumCount()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1,c2) -> (int) (c1.getPopulation() - (c2.getPopulation())));
        return new ResponseEntity<>(myList, HttpStatus.OK);

    }

    //http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = "application/json")
    public ResponseEntity<?> populationMaximumCount()
    {
        List<Country> myMaxList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myMaxList::add);
        myMaxList.sort((c1,c2) -> (int) (c2.getPopulation() - (c1.getPopulation())));
        return new ResponseEntity<>(myMaxList, HttpStatus.OK);

    }

    //http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = "application/json")
    public ResponseEntity<?> populationMedianCount()
    {
        List<Country> myMedianList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myMedianList::add);
        myMedianList.sort((c1,c2) -> (int) (c2.getPopulation() - (c1.getPopulation())));
        Country returnCountry = myMedianList.get((myMedianList.size() / 2) +1);
        return new ResponseEntity<>(returnCountry, HttpStatus.OK);

    }
}
