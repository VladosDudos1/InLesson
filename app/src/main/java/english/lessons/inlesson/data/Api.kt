package english.lessons.inlesson.data

import com.google.gson.GsonBuilder
import english.lessons.inlesson.ui.models.GameList
import english.lessons.inlesson.ui.models.WhoGame
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api  {

    @GET("choose")
    fun getChooseGame() : Observable<GameList>

    @GET("who")
    fun getWhoGame() : Observable<WhoGame>

    companion object{
        fun createApi(): Api {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://eu-central-1.aws.data.mongodb-api.com/app/englishteaching-cwqpe/endpoint/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}