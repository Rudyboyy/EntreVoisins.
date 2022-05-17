
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.utils.MatcherViewWithIndex.withIndex;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withIndex(withId(R.id.list_neighbours), 0))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withIndex(withId(R.id.list_neighbours), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT - 1));
    }

    //todo rajout a partir d'ici
    @Test
    public void myNeighboursList_neighbourAction_shouldShowNeighbourDetail() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.neighbour_detail)).check(matches(isDisplayed()));
    }

    @Test
    public void myNeighbourDetail_textViewShouldDisplayTheNeighbourName() {// todo rajout
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("Jack")));
    }

    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItemBothList() {// todo rajout (si androidX fonctionne remplacer le test)
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(1));
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(0));
    }

    // todo les ViewMatcher ne servent a rien ??
    @Test
    public void myFavoriteNeighboursList_checkNumberOfNeighboursIsRight() {// todo rajout
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(0));
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(4, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(5, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(5));
    }

    @Test
    public void myFavoriteNeighboursList_shouldBeEmpty() {//todo inutile ?
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(0));
    }

    @Test
    public void myNeighbourDetail_favoriteAction_shouldAddItemToMyNeighboursFavoriteList() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(+1));//todo rajout du + devant le 1
    }

    @Test
    public void myNeighbourDetail_favoriteActionTwice_shouldRemoveItemFromFavorite() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.doubleClick());
/*        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(1));
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());*/
        //todo laquelle choisir double click ou 2fois click (est-ce que le nom du test est correct)
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(0));
    }

    @Test
    public void myNeighbourDetail_backButtonAction_shouldReturnToThePreviousView () {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.imageButton)).perform(ViewActions.click());
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(matches(isDisplayed()));
    }
    //todo rajout
/*    @Test
    public void myFavoriteNeighboursList_deleteAction_shouldRemoveItemInBothList() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT));
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
        onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click());
        pressBack();
        onView(withIndex(withId(R.id.list_neighbours), 1)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT - 1));
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(1));//todo voir avec android X
    }*/
//todo
//    ne fonctionne pas retour l'erreur suivante
//    android.support.test.espresso.PerformException:
//    Error performing 'actionOnItemAtPosition performing ViewAction: single click on item at position: 1'on view 'Animations or transitions are enabled on the target device.
//    test suppression des delai d'animation + le swipe
}
