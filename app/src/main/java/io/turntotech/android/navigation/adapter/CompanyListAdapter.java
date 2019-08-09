package io.turntotech.android.navigation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.turntotech.android.navigation.R;
import io.turntotech.android.navigation.model.entity.Company;

public class CompanyListAdapter extends ArrayAdapter<Company> {

    //Create RequestQueue for Volley:
    RequestQueue requestQ = null;

    public CompanyListAdapter(Context context, List<Company> values) {
        super(context, R.layout.company_row_layout, values);
        requestQ = Volley.newRequestQueue(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Company company = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.company_row_layout, parent, false);
            convertView = rowView;
        }

        TextView textViewCompanyName = convertView.findViewById(R.id.txtViewCompanyName);
        final TextView textViewCompanyStock = convertView.findViewById(R.id.txtViewStockName);
        final TextView textViewStockPrice = convertView.findViewById(R.id.txtViewStockPrice);
        final ImageView imgViewCompanyLogo = convertView.findViewById(R.id.imgViewCompanyLogo);


        String title = company.getCompanyName() + " (" + company.getCompanyStock() + ")";

        textViewCompanyName.setText(title);
        textViewCompanyStock.setText("");
        textViewStockPrice.setText (company.getStockPrice());

        // Using Volley to retrieve ImageRequest.
        ImageRequest imageRequest = new ImageRequest(company.getCompanyLogoUrl(),
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                   imgViewCompanyLogo.setImageBitmap(response);
                }

            }, 0, 0, null, null, null);

        requestQ.add(imageRequest);


        //Using Volley to retrieve JsonObjectRequest:

        String jsonUrl = "https://api.unibit.ai/api/realtimestock/"+ company.getCompanyStock() +"?size=1&AccessKey=TTCTRtBc2jylERpT8j_dpPwOvwIpeKR4";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            JSONArray realTimeStockPrice = jsonObject.getJSONArray("Realtime Stock price");

                            for(int i = 0; i < realTimeStockPrice.length(); i++) {
                                JSONObject jsonObj = realTimeStockPrice.getJSONObject(i);
                                    String price = jsonObj.getString("price");

                                    textViewStockPrice.setText(price);
                            }
                        }catch (
                        JSONException e){
                        Log.e("JSON_ERROR",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        requestQ.add(jsonObjectRequest);

        return convertView;
    }



}
