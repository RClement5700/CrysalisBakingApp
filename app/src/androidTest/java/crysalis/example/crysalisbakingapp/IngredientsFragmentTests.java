package crysalis.example.crysalisbakingapp;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;

//@RunWith(AndroidJUnit4.class)
public class IngredientsFragmentTests {

    @Rule public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        testRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ingredients_container, new IngredientsFragment())
                .commit();
    }

    @Test
    public void textViewDisplaysIngredientsTest() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_ingredients_view));
        Espresso.onView(ViewMatchers.withId(R.id.tv_ingredients_view)).check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.recipe_ingredients)));
    }
}
