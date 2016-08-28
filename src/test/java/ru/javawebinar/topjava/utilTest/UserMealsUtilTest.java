package ru.javawebinar.topjava.utilTest;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import ru.javawebinar.topjava.util.UserMealsUtil;

/**
 * Created by breeze on 28.08.2016.
 */
public class UserMealsUtilTest {
    static List<UserMeal> mealList;
    @BeforeClass
    public static void initMealList(){
        mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
    }

    @Test
    public void getCaloriesAtDays(){

        Map<LocalDate, Integer> caloriesPerDay = UserMealsUtil.getCaloriesAtDays(mealList);

        LocalDate thirtyMay = LocalDate.of(2015,Month.MAY,30);
        LocalDate thirtyOneMay = LocalDate.of(2015,Month.MAY,31);

        Assert.assertNotNull(caloriesPerDay.get(thirtyMay));
        Assert.assertNotNull(caloriesPerDay.get(thirtyOneMay));
        Assert.assertEquals(2000,caloriesPerDay.get(thirtyMay).intValue());
        Assert.assertEquals(2010,caloriesPerDay.get(thirtyOneMay).intValue());

        Assert.assertEquals(2, caloriesPerDay.entrySet().size());
    }

    @Test
    public void getFilteredByExceed_wholeDay(){
        List<UserMealWithExceed> mealWithExceeds =
                UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(1, 0), LocalTime.of(23,0), 2000);

        Assert.assertEquals(6, mealWithExceeds.size());
    }

    @Test
    public void getFilteredByExceed_onlyDinner(){
        List<UserMealWithExceed> mealWithExceeds =
                UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(11, 0), LocalTime.of(16,0), 2000);

        Assert.assertEquals(2, mealWithExceeds.size());
    }

    @Test
    public void getFilteredByExceed_exceedCorrect(){
        List<UserMealWithExceed> mealWithExceeds =
                UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23,0), 2000);

        int countExceed = 0;
        int countNotExceed = 0;
        for (UserMealWithExceed meal: mealWithExceeds) {
            if(meal.getExceed()){
                countExceed += 1;
            } else {
                countNotExceed += 1;
            }
        }
        Assert.assertEquals(3, countExceed);
        Assert.assertEquals(3, countNotExceed);
    }
}
