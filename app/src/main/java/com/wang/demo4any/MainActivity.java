package com.wang.demo4any;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wang.demo4any.fragments.navigation.FunctionFragment;
import com.wang.demo4any.fragments.navigation.NavigationFragment;
import com.wang.demo4any.fragments.navigation.TestFragment;
import com.wang.demo4any.utils.ToastUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements NavigationFragment.NavigationCallback {

    private static final String TAG = "MainActivity";

    private static final String FRAGMENT_TAG_NAVIGATION_FUNCTION = "fragment.tag.navigation.function";

    private static final String FRAGMENT_TAG_NAVIGATION_TEST = "fragment.tag.navigation.test";

    private Fragment mShowingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.getId() == R.id.container && fragment.isVisible()) {
                mShowingFragment = fragment;
                break;
            }
        }

        if (mShowingFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Fragments.getTestFragments().get(0).fragment.get())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * invalidateOptionsMenu() 触发 onCreateOptionsMenu & onPrepareOptionsMenu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.navigation_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        IntStream.range(0, menu.size()).forEach(index -> {
            MenuItem item = menu.getItem(index);
            switch (item.getItemId()) {
                case R.id.menu_function:
                    Fragment functionFragment = getSupportFragmentManager()
                            .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_FUNCTION);
                    if (functionFragment == null) {
                        functionFragment = FunctionFragment.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.navigation, functionFragment, FRAGMENT_TAG_NAVIGATION_FUNCTION)
                                .hide(functionFragment)
                                .commit();
                    }
                    break;
                case R.id.menu_test:
                    Fragment testFragment = getSupportFragmentManager()
                            .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_TEST);
                    if (testFragment == null) {
                        testFragment = TestFragment.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.navigation, testFragment, FRAGMENT_TAG_NAVIGATION_TEST)
                                .hide(testFragment)
                                .commit();
                    }
                    break;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (item.getItemId() != R.id.menu_function) {
            closeFunctionNavigation();
        }
        if (item.getItemId() != R.id.menu_test) {
            closeTestNavigation();
        }
        switch (item.getItemId()) {
            case R.id.menu_function:
                ToastUtils.showShort("function clicked");
                Fragment functionFragment = getSupportFragmentManager()
                        .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_FUNCTION);
                if (functionFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Log.d(TAG, "onOptionsItemSelected: " + functionFragment.isVisible());
                    if (functionFragment.isVisible()) {
                        transaction.hide(functionFragment);
                    } else {
                        transaction.show(functionFragment);
                    }
                    transaction.commit();
                }
                break;
            case R.id.menu_test:
                Fragment testFragment = getSupportFragmentManager()
                        .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_TEST);
                if (testFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Log.d(TAG, "onOptionsItemSelected: " + testFragment.isVisible());
                    if (testFragment.isVisible()) {
                        transaction.hide(testFragment);
                    } else {
                        transaction.show(testFragment);
                    }
                    transaction.commit();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationClick(Fragment fragment) {
        closeFunctionNavigation();
        closeTestNavigation();
        if (mShowingFragment == fragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        mShowingFragment = fragment;
        Log.d(TAG, "onNavigationClick: replaced");
    }

    private void closeFunctionNavigation() {
        findFunctionFragment().ifPresent(fragment -> {
            if (fragment.isVisible()) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment)
                        .commit();
            }
        });
    }

    private void closeTestNavigation() {
        findTestFragment().ifPresent(fragment -> {
            if (fragment.isVisible()) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment)
                        .commit();
            }
        });
    }

    private Optional<Fragment> findFunctionFragment() {
        return Optional.ofNullable(getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_FUNCTION));
    }

    private Optional<Fragment> findTestFragment() {
        return Optional.ofNullable(getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG_NAVIGATION_TEST));
    }
}
