package com.example.alarm.DAO;

import com.example.alarm.model.City;

import java.util.ArrayList;

public interface ICityDAO {
    public void saveCity(String name); //Saves a city into the favourites list.
    public void deleteCity(String name);
    public ArrayList<City> load (boolean selectedOnly); //Either loads all Cities from database or only the selected ones (favourites)
    public boolean isDbEmpty(); //We need this to check if we need to fill the database for the first use.
    public void fillDb(ArrayList<City> cities); //When the app is launched for the very first time it fills the db.
}
