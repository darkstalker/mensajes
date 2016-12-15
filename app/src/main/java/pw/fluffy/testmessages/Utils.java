package pw.fluffy.testmessages;

final class Utils
{
    // me pregunto porque no lo hicieron asi desde un principio..
    static Integer tryParseInt(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
}
