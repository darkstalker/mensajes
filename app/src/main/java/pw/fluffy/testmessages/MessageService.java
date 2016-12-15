package pw.fluffy.testmessages;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class MessageService
{
    private static final String endpoint = "http://api-test-fabianprado.c9users.io";
    static final String auth = "qwer1234";

    private static MessageApi m_instance;

    static MessageApi instance()
    {
        if (m_instance == null)
        {
            m_instance = new Retrofit.Builder()
                .baseUrl(MessageService.endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MessageApi.class);
        }

        return m_instance;
    }
}
