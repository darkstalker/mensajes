package pw.fluffy.testmessages;

public class MessageItem
{
    public Integer id;
    public Integer question;
    public String answer;
    public String sender;
    //public String send_at;

    MessageItem(int question, String answer, String sender)
    {
        this.question = question;
        this.answer = answer;
        this.sender = sender;
    }
}
