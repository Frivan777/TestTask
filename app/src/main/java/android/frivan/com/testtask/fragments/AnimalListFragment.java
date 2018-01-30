package android.frivan.com.testtask.fragments;

import android.content.Context;
import android.frivan.com.testtask.R;
import android.frivan.com.testtask.adapters.AnimalAdapter;
import android.frivan.com.testtask.models.Animal;
import android.frivan.com.testtask.models.Data;
import android.frivan.com.testtask.utils.RetrofitController;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Базовый класс для списка
 */

public abstract class AnimalListFragment extends Fragment {
    private static final String KEY_LIST = "list";
    private static final String KEY_SCROLL_POSITION = "scrollPosition";

    private AnimalAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AnimalAdapter.CallBacks mCallBacks;
    private List<Animal> mList;
    private CompositeDisposable mCompositeDisposable;

    {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnimalAdapter.CallBacks) {
            mCallBacks = (AnimalAdapter.CallBacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_list, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * Инициализирует используемые view
     *
     * @param view корневое представление
     */
    private void initView(View view, Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        int scrollPosition;

        if (savedInstanceState != null) {
            mList = savedInstanceState.getParcelableArrayList(KEY_LIST);
            //linearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_SCROLL_POSITION));
            scrollPosition = savedInstanceState.getInt(KEY_SCROLL_POSITION, 0);
            linearLayoutManager.scrollToPosition(scrollPosition);
        }

        mRecyclerView = view.findViewById(R.id.list);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
        }

        if (mList == null) {
            Disposable disposable = RetrofitController.getAnimalApi().getAnimals(getQuery())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Data>() {
                        @Override
                        public void accept(Data data) throws Exception {
                            mList = data.getData();
                            if (isAdded() && data != null && data.getData() != null) {
                                updateUI(data);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (isAdded()) {
                                if (getView() != null) {
                                    Snackbar.make(getView(), R.string.error_msg, Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
            mCompositeDisposable.add(disposable);
        } else {
            updateUI(mList);
        }

    }

    /**
     * Обновление списка данных
     *
     * @param data объект данных
     */
    private void updateUI(Data data) {
        updateUI(data.getData());
    }

    /**
     * Обновление списка данных
     *
     * @param list список объектов
     */
    private void updateUI(List<Animal> list) {
        if (mAdapter == null) {
            mAdapter = new AnimalAdapter(getActivity(), list, mCallBacks);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setValues(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(KEY_SCROLL_POSITION, mRecyclerView.getLayoutManager().onSaveInstanceState());
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int scrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(KEY_SCROLL_POSITION, scrollPosition);
        if (mList != null) {
            outState.putParcelableArrayList(KEY_LIST, new ArrayList<>(mList));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    /**
     * Возвращает константу запроса
     *
     * @return {@link android.frivan.com.testtask.interfaces.AnimalApi#CAT} и т.д
     */
    protected abstract String getQuery();

}
