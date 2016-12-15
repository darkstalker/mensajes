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
    private final int REQUEST_SENDMSG = 42;

    private SwipeRefreshLayout m_swipeContainer;

    private ArrayList<MessageItem> m_messages;
    private MessageListAdapter m_msgAdapter;

    @SuppressWarnings("unchecked")
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
        if (savedInstanceState != null)
        {
            // java hace type erasure en parametros genericos, asi que no se puede escribir
            // una version type-safe de esto porque no hay informacion runtime.
            // en teoría no deberia fallar
            m_messages = (ArrayList<MessageItem>)savedInstanceState.getSerializable("messages");
        }
        else
        {
            m_messages = new ArrayList<>();
        }
        m_msgAdapter = new MessageListAdapter(m_messages, this);
        m_lstMessages.setAdapter(m_msgAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // esto deberia ejecutarse solo en la carga inicial de datos
        if (m_messages.isEmpty())
        {
            update_list(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable("messages", m_messages);
    }

    public void cmdAdd_onclick(View v)
    {
        startActivityForResult(new Intent(this, NewMessageActivity.class), REQUEST_SENDMSG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // hace un refresh despues de enviar
        if (requestCode == REQUEST_SENDMSG && resultCode == RESULT_OK)
        {
            update_list(false);
        }
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
