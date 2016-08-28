package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        for (UserMealWithExceed userMealWithExceed: getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)) {
            System.out.println(userMealWithExceed.toString());

        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList,
                                                                    LocalTime startTime,
                                                                    LocalTime endTime,
                                                                    int caloriesPerDay) {

        Set<LocalDate> datesWithExceed = new HashSet<LocalDate>();
        Map<LocalDate, Integer> dateExceedMap = getCaloriesAtDays(mealList);
        for (Map.Entry<LocalDate,Integer> entry: dateExceedMap.entrySet()) {
            if(entry.getValue() > caloriesPerDay){
                datesWithExceed.add(entry.getKey());
            }

        }

        List<UserMealWithExceed> returnList = new ArrayList<UserMealWithExceed>();
        for (UserMeal userMeal: mealList) {

            //if(userMealTime.isAfter(startTime) && userMealTime.isBefore(endTime)){
            if(TimeUtil.isBetween(userMeal.getLocalTime(), startTime, endTime)){
                boolean exceed = false;
                if (datesWithExceed.contains(userMeal.getLocalDate())) {
                    exceed = true;
                }
                returnList.add(
                        new UserMealWithExceed(userMeal,
                                exceed)
                            );
            }
        }

        return returnList;
    }

    public static Map<LocalDate, Integer> getCaloriesAtDays(List<UserMeal> mealList){
        Map<LocalDate, Integer> dateExceedMap = new HashMap<LocalDate, Integer>();
        for (UserMeal userMeal: mealList) {
            if(dateExceedMap.containsKey(userMeal.getLocalDate())) {
                Integer curCalories = dateExceedMap.get(userMeal.getLocalDate());
                dateExceedMap.put(userMeal.getLocalDate(), curCalories + userMeal.getCalories());
            }
            else
            {
                dateExceedMap.put(userMeal.getLocalDate(), userMeal.getCalories());
            }
        }
        return dateExceedMap;
    }

    public static List<UserMeal> getMealsByDay(List<UserMeal> mealList, LocalDate day){
        List<UserMeal> mealAtDay = new ArrayList<UserMeal>();
        for (UserMeal meal: mealList) {
            if(day.equals(meal.getDateTime().toLocalDate())){
                mealAtDay.add(meal);
            }
        }
        return mealAtDay;
    }
}
