package io.turntotech.android.navigation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import io.turntotech.android.navigation.R;
import io.turntotech.android.navigation.model.entity.Company;
import io.turntotech.android.navigation.model.entity.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {

    //Create RequestQueue for Volley:
    RequestQueue requestQ = null;

    public ProductListAdapter(Context context, List<Product> values) {
        super(context, R.layout.product_row_layout, values);
        requestQ = Volley.newRequestQueue(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.product_row_layout, parent, false);
            convertView = rowView;
        }

        TextView txtViewProductName = convertView.findViewById(R.id.txtViewProductName);
        TextView txtViewProductPrice = convertView.findViewById(R.id.txtViewProductPrice);
        final ImageView imgViewProductLogo = convertView.findViewById(R.id.imgViewProductLogo);
        final ImageView imageViewCompanyLogo = convertView.findViewById(R.id.imgViewCompanyLogo);

        txtViewProductName.setText(product.getProductName());
        txtViewProductPrice.setText (product.getProductPrice());

        // Using Volley, Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest imageRequest = new ImageRequest(product.getProductImgUrl(),

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imgViewProductLogo.setImageBitmap(response);
                    }

                }, 0, 0, null, null, null);

        requestQ.add(imageRequest);
        return convertView;
    }


}
