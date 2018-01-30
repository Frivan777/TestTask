package android.frivan.com.testtask.fragments;

import android.frivan.com.testtask.interfaces.AnimalApi;
import android.support.v4.app.Fragment;

/**
 * Cписок котов
 */

public class CatFragment extends AnimalListFragment {

    @Override
    protected String getQuery() {
        return AnimalApi.CAT;
    }

    /**
     * Создает новый объект фрагмента
     *
     * @return new CatFragment
     */
    public static Fragment newInstance() {
        return new CatFragment();
    }
}
