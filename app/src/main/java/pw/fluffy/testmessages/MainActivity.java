package pw.fluffy.testmessages;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private SwipeRefreshLayout m_swipeContainer;

    private ArrayList<MessageItem> m_messages;
    private MessageListAdapter m_msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        m_swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                update_list(true);
            }
        });

        ListView m_lstMessages = (ListView)findViewById(R.id.lstMessages);
        m_messages = new ArrayList<>();
        m_msgAdapter = new MessageListAdapter(m_messages, this);
        m_lstMessages.setAdapter(m_msgAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        update_list(false);
    }

    public void cmdAdd_onclick(View v)
    {
        startActivity(new Intent(this, NewMessageActivity.class));
    }

    void update_list(final boolean is_refresh)
    {
        MessageService.instance().list(MessageService.auth).enqueue(new Callback<List<MessageItem>>()
        {
            @Override
            public void onResponse(Call<List<MessageItem>> call, Response<List<MessageItem>> response)
            {
                if (is_refresh)
                    m_swipeContainer.setRefreshing(false);

                if (response.isSuccessful())
                {
                    m_messages.clear();
                    for (MessageItem msg : response.body())
                    {
                        Log.d("TestMessages", msg.toString());
                        m_messages.add(msg);
                    }
                    m_msgAdapter.notifyDataSetChanged();
                }
                else
                {
                    String err = String.format(Locale.getDefault(), "Error %d: %s", response.code(), response.message());
                    Log.e("TestMessages", err);
                    Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MessageItem>> call, Throwable t)
            {
                if (is_refresh)
                    m_swipeContainer.setRefreshing(false);

                String err = t.getLocalizedMessage();
                Log.e("TestMessages", err);
                Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
}
