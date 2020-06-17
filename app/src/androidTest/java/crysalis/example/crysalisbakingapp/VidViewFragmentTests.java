package crysalis.example.crysalisbakingapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

//@RunWith(AndroidJUnit4.class)
public class VidViewFragmentTests {

    @Rule public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        testRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ingredients_container, new IngredientsFragment())
                .add(R.id.step_container, new StepFragment())
                .add(R.id.vid_view_container, new VideoViewFragment())
                .commit();
    }

    @Test
    public void imageViewDisplaysDefaultImage() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_placeholder_image));
        Espresso.onView(ViewMatchers.withId(R.id.iv_placeholder_image))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
