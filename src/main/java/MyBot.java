//import org.telegram.telegrambots.api.objects.Update;
//import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {

    String au;

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        System.out.println(update.getMessage().getText());
        SendMessage message = new SendMessage();
        message.setText(update.getMessage().getText());
        setButton(message);
        message.setChatId(update.getMessage().getChatId());
        // String txt=update.getMessage().getText().toString();
        // sendMsg(message, " чем могу помочь?");
      /*  if (update.getMessage().getText() == "/help" ) {
            au = message.getText();
        }
        if ( update.getMessage().getText() =="/current" ) {
            au = message.getText();
        }
        if ( update.getMessage().getText() == "/5daysforecast") {
            au = message.getText();
        }*/
        if (message.getText() != null) {
            switch (message.getText()) {
                case "/help": {
                    System.out.println("+");
                    sendMsg(message, " Щоб обрати формат погоди, скористуйтеся командами:"+"\n"+"/current-поточна погода "+"\n"+
                            "/5daysforecast- прогноз погоди на 5 днів .");
                    break;
                }
                case "/current": {
                    sendMsg(message, " Для того, щоб дізнатися поточну погоду, введіть назву міста.");
                    au = "cur";

                    break;
                }
                case "/5daysforecast": {
                    sendMsg(message, " Для того, щоб дізнатися прогноз погоди на 5 днів, введіть назву міста.");
                    au = "5days";

                    break;
                }

                default:
                {
                    switch (au)
                    {
                        case "cur": {
                            try {
                                sendMsg(message, Weather.getWeather(message.getText(), model));
                                break;
                            } catch (IOException e) {
                                sendMsg(message, "Місто не знайдено");
                                break;

                            }
                        }
                        case "5days": {
                            try {
                                sendMsg(message, Weather.getWeather5(message.getText(), model));
                                break;
                            } catch (IOException e) {
                                sendMsg(message, "Місто не знайдено");
                                break;

                            }



                        }
                    }
                }
            }
        }
    }


    private void sendMsg(SendMessage message, String s) {

        //  System.out.println("Method");
        //message.setText(message.getText() + " - " + s);
        message.setText(s);
        try {

            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButton(SendMessage sm) {
        ReplyKeyboardMarkup replykeyboard = new ReplyKeyboardMarkup();

        sm.setReplyMarkup(replykeyboard);
        replykeyboard.setSelective(true);
        replykeyboard.setResizeKeyboard(true);
        replykeyboard.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboardRowList = new ArrayList();

        KeyboardRow keyboardfirstRow = new KeyboardRow();

        keyboardfirstRow.add(new KeyboardButton("/help"));
        KeyboardRow keyboard2Row = new KeyboardRow();
        keyboard2Row.add(new KeyboardButton("/current"));
        KeyboardRow keyboard3Row = new KeyboardRow();
        keyboard3Row.add(new KeyboardButton("/5daysforecast"));
        keyboardRowList.add(keyboardfirstRow);
        keyboardRowList.add(keyboard2Row);
        keyboardRowList.add(keyboard3Row);


        replykeyboard.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "SashW_bot";
    }

    public String getBotToken() {
        return "712029557:AAEP4HQMs8PBUC7Wf05VIp_xfpDU7aADpNI";
    }
}
