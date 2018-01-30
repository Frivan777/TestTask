package android.frivan.com.testtask.fragments;

import android.frivan.com.testtask.R;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Главный фрагмент приложение
 */

public class MainFragment extends Fragment {
    private static final int FIRST_FRAGMENT = 0;
    private static final int SECOND_FRAGMENT = 1;
    private static final String KEY_LAST_POSITION = "lastPosition";
    private static final String KEY_FIRST_FRAGMENT = "first_fragment";
    private static final String KEY_SECOND_FRAGMENT = "second_fragment";

    private int mLastPosition = 0;
    private Fragment mFirstFragment;
    private Fragment mSecondFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            mFirstFragment = fragmentManager.getFragment(savedInstanceState, KEY_FIRST_FRAGMENT);
            if (mFirstFragment == null) {
                mFirstFragment = CatFragment.newInstance();
            }
            mSecondFragment = fragmentManager.getFragment(savedInstanceState, KEY_SECOND_FRAGMENT);
            if (mSecondFragment == null) {
                mSecondFragment = DogFragment.newInstance();
            }
        } else {
            mFirstFragment = CatFragment.newInstance();
            mSecondFragment = DogFragment.newInstance();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * Инициализирует используемые view
     *
     * @param view корневое представление
     */
    private void initView(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastPosition = savedInstanceState.getInt(KEY_LAST_POSITION, FIRST_FRAGMENT);
        }

        TabLayout tabLayout = view.findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab_1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab_2));
        //tabLayout.setScrollPosition(mLastPosition, 0, true);
        tabLayout.getTabAt(mLastPosition).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case FIRST_FRAGMENT:
                        showChildFragment(mSecondFragment, mFirstFragment);
                        mLastPosition = FIRST_FRAGMENT;
                        break;
                    case SECOND_FRAGMENT:
                        showChildFragment(mFirstFragment, mSecondFragment);
                        mLastPosition = SECOND_FRAGMENT;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.child_fragment_container);
        if (fragment == null) {
            addChildFragment(mSecondFragment);
            addChildFragment(mFirstFragment);
            showChildFragment(mSecondFragment, mFirstFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_LAST_POSITION, mLastPosition);
        FragmentManager fm = getChildFragmentManager();
        if (mFirstFragment.isAdded()) {
            fm.putFragment(outState, KEY_FIRST_FRAGMENT, mFirstFragment);
        }
        if (mSecondFragment.isAdded()) {
            fm.putFragment(outState, KEY_SECOND_FRAGMENT, mSecondFragment);
        }
    }


    /**
     * Показать дочерний фрагмент
     *
     * @param hide фрагмент который требуется скрыть
     * @param show фрагмент который требуется отообразить
     */
    private void showChildFragment(Fragment hide, Fragment show) {
        getChildFragmentManager()
                .beginTransaction()
                .hide(hide)
                .show(show)
                .commit();
    }

    /**
     * Добавляет дочерний фрагмент в текущий
     *
     * @param fragment требующийся фрагмент
     */
    private void addChildFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.child_fragment_container, fragment)
                .commit();
    }

    /**
     * Создает новый объект фрагмента
     *
     * @return new MainFragment
     */
    public static Fragment newInstance() {
        return new MainFragment();
    }
}
