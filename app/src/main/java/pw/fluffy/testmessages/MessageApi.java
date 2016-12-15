package pw.fluffy.testmessages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface MessageApi
{
    // trae la lista de mensajes
    @GET("/messages.json")
    Call<List<MessageItem>> list(
        @Header("Authorization") String auth
    );

    // crea uno nuevo
    @POST("/messages.json")
    Call<MessageItem> create(
        @Header("Authorization") String auth,
        @Body() MessageItem obj
    );

    // trae los datos del objeto con el id especificado
    @GET("/messages/{id}.json")
    Call<MessageItem> get(
        @Header("Authorization") String auth,
        @Path("id") int id
    );

    // actualiza la información del objeto con el id especificado
    @PUT("/messages/{id}.json")
    Call<MessageItem> update(
        @Header("Authorization") String auth,
        @Path("id") int id,
        @Body() MessageItem obj
    );
}
