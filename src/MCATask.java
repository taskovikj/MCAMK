import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;




public class MCATask {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://interview-task-api.mca.dev/qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1");
        String json = stream(url);
        if (json != null) {
            List<Item> items = parseJson(json);
            Collections.sort(items,Comparator.comparing(Item::getName));
            MCATask.printResult(items);
        }
    }


    public static String stream(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Item> parseJson(String json) {
        List<Item> items = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                boolean domestic = jsonObject.getBoolean("domestic");
                double price = jsonObject.getDouble("price");
                String description = jsonObject.getString("description");

                int weight;
                if (jsonObject.isNull("weight")) {
                    weight = -1;
                } else {
                    weight = jsonObject.getInt("weight");
                }

                Item item = new Item(name, domestic, weight,price , description);
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static void printResult(List<Item> products){
        List<Item> domestic = new ArrayList<Item>();
        List<Item> nonDomestic = new ArrayList<Item>();
        double domesticPrice = 0;
        double nonDomesticPrice = 0;
        for (Item product : products) {

            if (product.isDomestic()){
                domestic .add(product);
                domesticPrice+=product.getPrice();
            }
            else {
                nonDomestic.add(product);
                nonDomesticPrice+=product.getPrice();
            }
        }
        System.out.println(". Domestic");
        for (Item product : domestic) {
            System.out.println(product.toString());
        }

        System.out.println(". Imported");
        for (Item product : nonDomestic) {
            System.out.println(product.toString());
        }
        System.out.println("Domestic cost $"+domesticPrice);
        System.out.println("Imported cost $"+nonDomesticPrice);
        System.out.println("Domestic count: "+domestic.size());
        System.out.println("Imported count: "+nonDomestic.size());
    }
}

class Item {
    private String name;
    private boolean domestic;
    private double price;
    private int weight;
    private String description;

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Item(String name, boolean domestic, int weight, double price, String description) {
        this.name = name;
        this.domestic = domestic;
        this.price = price;
        this.description = description;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\t.."+name+"..\n \tPrice: $"+ price +"\n"+"\t"+
                description.substring(0,Math.min(description.length(), 10))+"...\n"
                +"\tWeight: "+(weight > 0 ? weight + "g":"N/A\n");
    }


    public boolean isDomestic() {
        return domestic;
    }


    public double getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }

}
