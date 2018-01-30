package android.frivan.com.testtask.fragments;

import android.frivan.com.testtask.interfaces.AnimalApi;
import android.support.v4.app.Fragment;

/**
 * Список собак
 */

public class DogFragment extends AnimalListFragment {

    @Override
    protected String getQuery() {
        return AnimalApi.DOG;
    }

    /**
     * Создает новый объект фрагмента
     *
     * @return new DogFragment
     */
    public static Fragment newInstance() {
        return new DogFragment();
    }
}
