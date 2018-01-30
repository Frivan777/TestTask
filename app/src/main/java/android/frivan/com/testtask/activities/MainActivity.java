package android.frivan.com.testtask.activities;

import android.frivan.com.testtask.R;
import android.frivan.com.testtask.adapters.AnimalAdapter;
import android.frivan.com.testtask.fragments.AboutAnimalFragment;
import android.frivan.com.testtask.fragments.MainFragment;
import android.frivan.com.testtask.interfaces.Constants;
import android.frivan.com.testtask.models.Animal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements Constants, AnimalAdapter.CallBacks {
    private static final String BACK_STACK_FRAGMENT = "backStackFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    /**
     * Инициализирует используемые view
     */
    private void initView() {
        showFragment();
    }

    @Override
    public void onClickItem(Animal data) {
        showFragment(AboutAnimalFragment.newInstance(data));
    }

    /**
     * Отображает фрагмент
     */
    private void showFragment() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = MainFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * Отображает фрагмент
     *
     * @param fragment - фрагмент, который нужно отобразить
     */
    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(BACK_STACK_FRAGMENT)
                .commit();
    }

}
