package pw.fluffy.testmessages;

import java.util.Date;

class MessageItem
{
    Integer id;
    Integer question;
    String answer;
    String sender;
    Date send_at;
    //Date created_at;
    //Date updated_at;

    MessageItem(Integer question, String answer, String sender, Date send_at)
    {
        this.question = question;
        this.answer = answer;
        this.sender = sender;
        this.send_at = send_at;
    }

    @Override
    public String toString()
    {
        return "MessageItem{" +
            "id=" + id +
            ", question=" + question +
            ", answer='" + answer + '\'' +
            ", sender='" + sender + '\'' +
            ", send_at=" + send_at +
            '}';
    }
}
