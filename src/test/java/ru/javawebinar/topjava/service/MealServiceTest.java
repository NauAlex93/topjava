package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import lombok.extern.slf4j.Slf4j;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Slf4j
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void delete() throws Exception {
        long startTime = System.currentTimeMillis();
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        long endTime = System.currentTimeMillis();
        log.info("Test 'delete' takes {} ms", endTime - startTime);
    }

    @Test
    public void deleteNotFound() throws Exception {
        long startTime = System.currentTimeMillis();
        thrown.expect(NotFoundException.class);
        service.delete(1, USER_ID);
        long endTime = System.currentTimeMillis();
        log.info("Test 'deleteNotFound' takes {} ms", endTime - startTime);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        long startTime = System.currentTimeMillis();
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, ADMIN_ID);
        long endTime = System.currentTimeMillis();
        log.info("Test 'deleteNotOwn' takes {} ms", endTime - startTime);
    }

    @Test
    public void create() throws Exception {
        long startTime = System.currentTimeMillis();
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
        long endTime = System.currentTimeMillis();
        log.info("Test 'create' takes {} ms", endTime - startTime);
    }

    @Test
    public void get() throws Exception {
        long startTime = System.currentTimeMillis();
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
        long endTime = System.currentTimeMillis();
        log.info("Test 'get' takes {} ms", endTime - startTime);
    }

    @Test
    public void getNotFound() throws Exception {
        long startTime = System.currentTimeMillis();
        thrown.expect(NotFoundException.class);
        service.get(1, USER_ID);
        long endTime = System.currentTimeMillis();
        log.info("Test 'getNotFound' takes {} ms", endTime - startTime);
    }

    @Test
    public void getNotOwn() throws Exception {
        long startTime = System.currentTimeMillis();
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
        long endTime = System.currentTimeMillis();
        log.info("Test 'getNotOwn' takes {} ms", endTime - startTime);
    }

    @Test
    public void update() throws Exception {
        long startTime = System.currentTimeMillis();
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
        long endTime = System.currentTimeMillis();
        log.info("Test 'update' takes {} ms", endTime - startTime);
    }

    @Test
    public void updateNotFound() throws Exception {
        long startTime = System.currentTimeMillis();
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
        long endTime = System.currentTimeMillis();
        log.info("Test 'updateNotFound' takes {} ms", endTime - startTime);
    }

    @Test
    public void getAll() throws Exception {
        long startTime = System.currentTimeMillis();
        assertMatch(service.getAll(USER_ID), MEALS);
        long endTime = System.currentTimeMillis();
        log.info("Test 'getAll' takes {} ms", endTime - startTime);
    }

    @Test
    public void getBetween() throws Exception {
        long startTime = System.currentTimeMillis();
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
        long endTime = System.currentTimeMillis();
        log.info("Test 'getBetween' takes {} ms", endTime - startTime);
    }
}