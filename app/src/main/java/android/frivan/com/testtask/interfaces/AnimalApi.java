package android.frivan.com.testtask.interfaces;

import android.frivan.com.testtask.models.Data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Для вывода модели животного
 */

public interface AnimalApi {
    String DOG = "dog";
    String CAT = "cat";

    /**
     * Выводит список животных
     *
     * @param query строка запроса {@link AnimalApi#DOG}, {@link AnimalApi#CAT}
     * @return Observable<Data>
     */
    @GET("api.php")
    Observable<Data> getAnimals(@Query("query") String query);
}
