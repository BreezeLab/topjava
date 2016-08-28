package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getLocalDate() {   return dateTime.toLocalDate();  }
    public LocalTime getLocalTime() {   return dateTime.toLocalTime();  }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean getExceed(){
        return this.exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserMeal ||
                obj instanceof UserMealWithExceed){
            UserMeal equalCandidate = (UserMeal)obj;
            return getCalories() == equalCandidate.getCalories() &&
                    getDateTime().equals(equalCandidate.getDateTime()) &&
                    getDescription().equals(equalCandidate.getDescription());
        }
        return false;
    }

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public UserMealWithExceed(UserMeal userMeal, boolean exceed){
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.exceed = exceed;
    }
}
