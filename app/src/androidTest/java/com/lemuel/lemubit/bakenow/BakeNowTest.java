package com.lemuel.lemubit.bakenow;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.lemuel.lemubit.bakenow.Models.Recipe;

import java.util.List;

/**
 * Created by charl on 02/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BakeNowTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipe() {

        /**
         * Not best option, will change to IdlingResources Later :)
         * */
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get the recipes downloaded with Retrofit
        List<Recipe> recipes= mActivityTestRule.getActivity().testrecipes();

        //click the RecyclerView at position 0
        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if the Fragment which holds the ingredients was displayed
        onView(withId(R.id.FRGingredients)).check(matches(isDisplayed()));

        //Check if the correct Title was displayed according to the clicked recipe
        onView(withId(R.id.ToolBar_Recipe_Title))
                .check(matches(withText(recipes.get(0).getName())));


        //click the step descriptions at position 0
        onView(withId(R.id.description_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if the Instruction was displayed according to the clicked step
        onView(withId(R.id.InstructionTXT)).check(matches(isDisplayed()));

        //Check if the right Step Description is shown
        onView(withId(R.id.InstructionTXT))
                .check(matches(withText(recipes.get(0).getSteps().get(0).getDescription())));

    }

}
