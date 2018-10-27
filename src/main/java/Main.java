import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Main
{
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
MyBot mb= new MyBot();
if(mb!=null)
            botapi.registerBot(mb);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
