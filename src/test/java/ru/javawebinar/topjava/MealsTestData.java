package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealsTestData {
    public static final int USER_MEAL = START_SEQ + 2;

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL + 1, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL + 4, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL1 = new Meal(USER_MEAL + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final List<Meal> MEALS = Arrays.asList(ADMIN_MEAL1, USER_MEAL5, USER_MEAL4,
                                                         USER_MEAL3, USER_MEAL2, USER_MEAL1);

    public static Meal getCreated() {
        return new Meal(null, of(2015, Month.OCTOBER, 30, 14, 0), "Новый ужин", 1300);
    }

    public static Meal getUpdated() {
        return new Meal(USER_MEAL, USER_MEAL1.getDateTime(), "Обновленный завтрак", 900);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
