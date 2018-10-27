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


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        System.out.println(update.getMessage().getText());
        SendMessage message = new SendMessage();
        message.setText(update.getMessage().getText());

        message.setChatId(update.getMessage().getChatId());
        // String txt=update.getMessage().getText().toString();
        // sendMsg(message, " чем могу помочь?");
        if (message != null) {
            switch (message.getText()) {
                case "/help": {
                    System.out.println("+");
                    sendMsg(message, " Чим можу допомогти?");

                }
                default: {
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "Місто не знайдено");
                    }

                }
            }
        }
    }


    private void sendMsg(SendMessage message, String s) {

        System.out.println("Method");
        //message.setText(message.getText() + " - " + s);
        message.setText(s);
        try {
            setButton(message);
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


        keyboardRowList.add(keyboardfirstRow);


        replykeyboard.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "SashW_bot";
    }

    public String getBotToken() {
        return "712029557:AAEP4HQMs8PBUC7Wf05VIp_xfpDU7aADpNI";
    }
}
