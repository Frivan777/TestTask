package android.frivan.com.testtask.fragments;

import android.frivan.com.testtask.R;
import android.frivan.com.testtask.models.Animal;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Детальный экран
 */

public class AboutAnimalFragment extends Fragment {
    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_BITMAP = "bitmap";
    private static final String EXTRA_DATA = "data";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_animal, container, false);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * Инициализирует используемые view
     *
     * @param view корневое представление
     */
    private void initView(View view, Bundle bundle) {
        ImageView image = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);

//        Bitmap bitmap = bundle.getParcelable(EXTRA_BITMAP);
//        image.setImageBitmap(bitmap);
//        String title = bundle.getString(EXTRA_TITLE);
        Animal data = getArguments().getParcelable(EXTRA_DATA);
        if (data != null) {
            title.setText(data.getTitle());
            Glide.with(this)
                    .load(Uri.parse(data.getUrl()))
                    .asBitmap()
                    .placeholder(R.drawable.plug)
                    .fitCenter()
                    .into(image);
        }
    }

    /**
     * Создает новый объект фрагмента
     *
     * @param data объект данных для фрагмента
     * @return new AboutAnimalFragment
     */
    public static Fragment newInstance(Animal data) {
        AboutAnimalFragment fragment = new AboutAnimalFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Создает новый объект фрагмента
     *
     * @param title  заглавие
     * @param bitmap картинка
     * @return new AboutAnimalFragment
     */
    public static Fragment newInstance(String title, Bitmap bitmap) {
        AboutAnimalFragment fragment = new AboutAnimalFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);
        args.putParcelable(EXTRA_BITMAP, bitmap);
        fragment.setArguments(args);
        return fragment;
    }
}
