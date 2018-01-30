package android.frivan.com.testtask.adapters;

import android.content.Context;
import android.frivan.com.testtask.R;
import android.frivan.com.testtask.models.Animal;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Адаптер для RecyclerView
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private List<Animal> mValues;
    private Context mContext;
    private CallBacks mCallBacks;

    public AnimalAdapter(Context context, List<Animal> list, CallBacks callBacks) {
        mContext = context.getApplicationContext();
        mValues = list;
        mCallBacks = callBacks;
    }

    @Override
    public AnimalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_animal, parent, false);
        return new AnimalHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalHolder holder, int position) {
        holder.bindAlarm(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Назначить список данных
     *
     * @param values объект данных
     */
    public void setValues(List<Animal> values) {
        mValues = values;
    }

    class AnimalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private ImageView mImage;

        private AnimalHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mImage = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        /**
         * Перерабатывает используемую view
         *
         * @param data текущий объект данных
         */
        private void bindAlarm(Animal data) {
            mTitle.setText(data.getTitle());
            Glide.with(mContext)
                    .load(Uri.parse(data.getUrl()))
                    .asBitmap()
                    .fitCenter()
                    .into(mImage);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.root:
                    if (mCallBacks != null) {
                        mCallBacks.onClickItem(mValues.get(getAdapterPosition()));
                    }
                    break;
            }
        }
    }

    public interface CallBacks {
        void onClickItem(Animal data);
    }
}
