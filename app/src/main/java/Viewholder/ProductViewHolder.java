package Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kart.R;

import Interface.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
public ItemClickListner listner;

     public ProductViewHolder(@NonNull View itemView)
     {
        super(itemView);

        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtProductName = itemView.findViewById(R.id.product_name);
        imageView = itemView.findViewById(R.id.select_product_image);
        txtProductPrice = itemView.findViewById(R.id.product_price);
     }

     public void setItemClickListner(ItemClickListner listner)
     {
         this.listner = listner;
     }


    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
