package crysalis.example.crysalisbakingapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

//@RunWith(AndroidJUnit4.class)
public class StepFragmentTests {

    @Rule public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        testRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ingredients_container, new IngredientsFragment())
                .add(R.id.step_container, new StepFragment())
                .commit();
    }

    @Test
    public void textViewDisplaysInstructionsTest() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_step_instruction));
        Espresso.onView(ViewMatchers.withId(R.id.tv_step_instruction)).check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.instructions)));
    }
}
