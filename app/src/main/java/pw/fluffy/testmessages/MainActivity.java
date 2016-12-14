package pw.fluffy.testmessages;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
                Toast.makeText(MainActivity.this, "(refresh)", Toast.LENGTH_LONG).show();
                m_swipeContainer.setRefreshing(false);
            }
        });

        ListView m_lstMessages = (ListView)findViewById(R.id.lstMessages);
        m_messages = new ArrayList<>();
        m_msgAdapter = new MessageListAdapter(m_messages, this);
        m_lstMessages.setAdapter(m_msgAdapter);
    }

    public void cmdAdd_onclick(View v)
    {
        Toast.makeText(this, "(nuevo mensaje)", Toast.LENGTH_LONG).show();

        // test
        m_messages.add(new MessageItem(1, "a", "nadie"));
        m_msgAdapter.notifyDataSetChanged();
    }
}
