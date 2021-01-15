package com.javacountries.demo.repositories;

import com.javacountries.demo.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>
{

}
