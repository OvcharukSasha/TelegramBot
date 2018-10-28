import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Weather {




    private static NumberFormat nf = new DecimalFormat("#.#");
    //b14faadebbd3c6e6919497439bf916e9
    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=b14faadebbd3c6e6919497439bf916e9");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));

        }
        return "Сity: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "°C" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Weather: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";

    }

    public static String getWeather5(String message, Model model) throws IOException {
        Model md[] = new Model[4];
        URL url = new URL("https://samples.openweathermap.org/data/2.5/forecast?q=" + message + "&appid=b14faadebbd3c6e6919497439bf916e9");
        //https://samples.openweathermap.org/data/2.5/forecast?q=M%C3%BCnchen&appid=b14faadebbd3c6e6919497439bf916e9
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        JSONObject object = new JSONObject(result);


        JSONArray getArray = object.getJSONArray("list");
        int z = 0;
        for (int i = 0; i < getArray.length(); i++) {
            int y = Math.floorMod(i, 9);
            if (y == 1) {
                System.out.println(i);
                System.out.println();

                JSONObject obj = getArray.getJSONObject(i);
                JSONObject main = obj.getJSONObject("main");

                System.out.println(main.toString());
                double r = main.getDouble("temp");
                double l=(1.8 *(r - 273) + 32-32)*5/9;
                double nmb = Double.parseDouble(nf.format(l).replace(',', '.'));

                System.out.println("temp=" + nmb);
                md[z] = new Model();
                md[z].setTemp(nmb);
                double h = main.getDouble("humidity");
                md[z].setHumidity(h);
                System.out.println("humidity=" + h);
                System.out.println();

                System.out.println(obj.toString());
                JSONArray gg = obj.getJSONArray("weather");
                for (int k = 0; k < gg.length(); k++) {
                    JSONObject ob = gg.getJSONObject(k);
                    md[z].setIcon((String) ob.get("icon"));
                    md[z].setMain((String) ob.get("main"));

                }


                md[z].setName(message);
                z++;
            }

        }
        String res = "";


        Calendar c = new GregorianCalendar();
        c.setTime(c.getTime());
        //c.set(2018, 10, 28);

        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("day: " + day_of_week);
        res+= " Сity:" + md[0].getName() + "\n" +"\n";
        for (int y = 0; y < md.length; y++) {
            res += "<<" + GetDay((Math.floorMod(day_of_week + y, 7))) + ">>" + "\n" +"\n"+

                    "Temperature: " + md[y].getTemp() + "°C" + "\n" +
                    "Humidity: " + md[y].getHumidity() + "%" + "\n" +
                    "Weather: " + md[y].getMain() + "\n"+"\n";
        }
        System.out.println(res);
        return res;
    }

    public static String GetDay(int d) {
        switch (d) {
            case 1: {
                return "Saturday";

            }
            case 2: {
                return "Monday";

            }
            case 3: {
                return "Tuesday";

            }
            case 4: {
                return "Wednesday";

            }
            case 5: {
                return "Thursday";

            }
            case 6: {
                return "Friday";

            }
            case 7: {
                return "Sunday";

            }


        }
        return "__";
    }


}
