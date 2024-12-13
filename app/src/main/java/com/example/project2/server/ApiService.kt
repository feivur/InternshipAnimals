package com.example.project2.server

import com.example.project2.server.events.EventsResponse
import com.example.project2.server.version.ServerVersionResponse
import com.example.project2.structure.axxonOne.cameraInfo.CameraList
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("product/version/")
    suspend fun getServerVersion(): ServerVersionResponse

    @GET("camera/list")
    suspend fun getCameras(
        @Query("limit") limit: Int = 1000,
        @Query("filter") filter: String? = null
    ): CameraList

    @GET("live/media/snapshot/{videoSourceId}")
    suspend fun getSnapshot(
        @Path("videoSourceId") videoSourceId: String,
        @Query("w") width: Int? = null,
        @Query("h") height: Int? = null
    ): retrofit2.Response<ResponseBody>

    //todo

    //todo получить список событий детекторов
    // http://136.243.144.109:8000/asip-api/archive/events/detectors/20241212T111205.843/20241211T105705.843?limit=20&offset=0&type=&limit_to_archive=1
    //год месяц день T(разделитель) часы минуты секунды .милисекунды
    //limit - ограничение на количество событий
    //type - тип "сработки" - события(Движение в зоне(MotionDetected), Лицо появилось(FaceAppeared), Пресечение линии(CrossOneLine)) и тд
    //ENDTIME > BEGINTIME ->> события отсортированы по убыванию, если наоброт, то по возрастанию
    //limit_to_archive=1 ->> сервер возвращает только те события, которые уже находятся в архиве
    //offset=0 "параметр пагинации"->> запрос начнется с первого события. Если я получаю много данных, то сервер будет отправлять их порциями (например, по 20 штук), и
    // | указывает, с какого места начинать возвращать события



    @GET("archive/events/detectors/{endTime}/{beginTime}")
    suspend fun getEvents(
        @Path("endTime") endTime: String,
        @Path("beginTime") beginTime: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("type") type: String? = null,
        @Query("limit_to_archive") limitToArchive: Int? = null
    ): EventsResponse
//todo


}


