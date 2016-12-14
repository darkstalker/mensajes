package pw.fluffy.testmessages;

public class MessageItem
{
    public int question;
    public String answer;
    public String sender;
    //public String timestamp;

    MessageItem(int question, String answer, String sender)
    {
        this.question = question;
        this.answer = answer;
        this.sender = sender;
    }
}
