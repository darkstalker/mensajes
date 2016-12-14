package pw.fluffy.testmessages;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

class MessageListAdapter extends BaseAdapter
{
    private List<MessageItem> m_list;
    private Activity m_activity;

    MessageListAdapter(List<MessageItem> list, Activity act)
    {
        super();
        m_list = list;
        m_activity = act;
    }

    @Override
    public int getCount()
    {
        return m_list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return m_list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            view = m_activity.getLayoutInflater().inflate(R.layout.list_row, viewGroup, false);
        }

        TextView question = (TextView)view.findViewById(R.id.msg_row_question);
        TextView answer = (TextView)view.findViewById(R.id.msg_row_answer);
        TextView sender = (TextView)view.findViewById(R.id.msg_row_sender);

        MessageItem item = m_list.get(i);
        question.setText(String.format(Locale.getDefault(), "%d", item.question));
        answer.setText(item.answer);
        sender.setText(item.sender);

        return view;
    }
}
