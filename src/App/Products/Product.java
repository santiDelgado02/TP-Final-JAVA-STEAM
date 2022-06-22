package App.Products;

import App.Review;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Product {

    private static int lastID =  -1;
    private int id;
    private String name;
    private float price;
    private ArrayList<Review> reviews;
    private float rating;
    private int sells;
    private ESRBClassification ESRB;
    private float income;
    /**
     * Use this constructor to create a new product.
     */
    public Product (String name, float price, ESRBClassification ESRB)
    {

        id = ++lastID;
        this.name = name;
        this.price = price;
        this.reviews = new ArrayList<Review>();
        this.rating = 0;
        this.ESRB = ESRB;
        this.income = 0;

    }

    /**
     * Use this constructor when importing a product from a file.
     */
    public Product (int id, String name, float price, ArrayList<Review> reviews, ESRBClassification ESRB)
    {

        this.id = id;
        this.name = name;
        this.price = price;
        this.reviews = reviews;
        this.ESRB = ESRB;

        lastID = Math.max(lastID, id);

        float i = 0;
        for (Review review : reviews)
        {
            i += review.getRating();
        }

        this.rating = i / reviews.size();

    }

    public Product( JSONObject id, JSONObject name, JSONObject price, JSONArray rewiews, JSONObject esrb, JSONObject income )
    {
        try
        {
            ArrayList <Review> reviewCollection= new ArrayList();

            for (int i=0; i< rewiews.length() ; i++)
            {
                JSONObject reviewObject= (JSONObject) rewiews.get(i);
                reviewCollection.add(new Review(reviewObject.get("user").toString(), reviewObject.get("text").toString(), Integer.parseInt(reviewObject.get("rating").toString())));
            }

            this.id = Integer.parseInt(id.toString());
            this.name = name.toString();
            this.price = Float.parseFloat(price.toString());
            this.reviews = reviewCollection;
            this.ESRB = ESRBClassification.valueOf(esrb.toString());
            this.income= Float.parseFloat(income.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static int getLastID()
    {
        return lastID;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public float getPrice()
    {
        return price;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public int getSells()
    {
        return sells;
    }

    public void addSell()
    {
        this.sells++;
    }

    public static JSONArray arrayToJSONObject(ArrayList array)
    {
        JSONArray jsonList = new JSONArray();

        if (!array.isEmpty())
        {
          for (int i = 0; i < array.size(); i++)
            {
                jsonList.put(array.get(i));
            }
        }

        return jsonList;

    }

    @Override
    public String toString()
    {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", reviews=" + reviews +
                ", rating=" + rating +
                ", sells=" + sells +
                ", ESRB=" + ESRB;
    }
    
    public JSONObject toJSON()
    {
        try
        {
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("name", name);
            json.put("price", price);

            JSONArray jsonReviews = new JSONArray();
            for (Review review : reviews)
            {
                jsonReviews.put(review.toJSON());
            }
            json.put("reviews", jsonReviews);

            json.put("rating", rating);
            json.put("sells", sells);
            json.put("ESRB", ESRB);
            return json;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
