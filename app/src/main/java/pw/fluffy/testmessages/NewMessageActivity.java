package pw.fluffy.testmessages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMessageActivity extends AppCompatActivity
{
    private EditText m_txtName;
    private EditText m_txtQuestion;
    private EditText m_txtAnswer;
    private Button m_cmdSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        m_txtName = (EditText)findViewById(R.id.txtName);
        m_txtQuestion = (EditText)findViewById(R.id.txtQuestion);
        m_txtAnswer = (EditText)findViewById(R.id.txtAnswer);
        m_cmdSend = (Button)findViewById(R.id.cmdSend);
    }

    public void cmdSend_onclick(View v)
    {
        if (!m_cmdSend.isEnabled())
            return;

        String name = m_txtName.getText().toString().trim();
        Integer question = Utils.tryParseInt(m_txtQuestion.getText().toString());
        String answer = m_txtAnswer.getText().toString().trim();

        if (name.isEmpty())
        {
            Toast.makeText(this, R.string.err_missing_name, Toast.LENGTH_LONG).show();
            return;
        }
        if (question == null)
        {
            Toast.makeText(this, R.string.err_invalid_question, Toast.LENGTH_LONG).show();
            return;
        }
        if (answer.isEmpty())
        {
            Toast.makeText(this, R.string.err_missing_answer, Toast.LENGTH_LONG).show();
            return;
        }

        m_cmdSend.setEnabled(false);
        send_data(question, answer, name);
    }

    private void send_data(Integer question, String answer, String sender)
    {
        MessageService.instance()
            .create(MessageService.auth, new MessageItem(question, answer, sender, new Date()))
            .enqueue(new Callback<MessageItem>()
            {
                @Override
                public void onResponse(Call<MessageItem> call, Response<MessageItem> response)
                {
                    if (response.isSuccessful())
                    {
                        Log.d("TestMessages", response.body().toString());
                        Toast.makeText(NewMessageActivity.this, R.string.msg_send_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        m_cmdSend.setEnabled(true);

                        String err = String.format(Locale.getDefault(), "Error %d: %s", response.code(), response.message());
                        Log.e("TestMessages", err);
                        Toast.makeText(NewMessageActivity.this, err, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageItem> call, Throwable t)
                {
                    m_cmdSend.setEnabled(true);

                    String err = t.getLocalizedMessage();
                    Log.e("TestMessages", err);
                    Toast.makeText(NewMessageActivity.this, err, Toast.LENGTH_LONG).show();
                }
            });
    }
}
