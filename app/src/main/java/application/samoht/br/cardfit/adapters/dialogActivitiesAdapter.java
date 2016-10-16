package application.samoht.br.cardfit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.ui.main.menu.cards.ICardsView;
import application.samoht.br.cardfit.ui.newcard.INewCardView;

/**
 * Created by Thomas on 24/08/16.
 */
public class dialogActivitiesAdapter extends RecyclerView.Adapter {

    private ArrayList<StandardActivity> arrayListActivties;
    private Context context;
    private ICardsView cardsView;
    private INewCardView newCardView;

    public dialogActivitiesAdapter(ArrayList<StandardActivity> arrayListActivities, Context context, ICardsView iCardsView){
        this.arrayListActivties = arrayListActivities;
        this.context = context;
        this.cardsView = iCardsView;
    }

    public dialogActivitiesAdapter(ArrayList<StandardActivity> arrayListActivities, Context context, INewCardView iNewCardView){
        this.arrayListActivties = arrayListActivities;
        this.context = context;
        this.newCardView = iNewCardView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_activities, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CustomViewHolder holder = (CustomViewHolder) viewHolder;
        StandardActivity standardActivity = arrayListActivties.get(position);
        //Picasso.with(context).load(standardActivity.getThumbnail()).resize(200,200).into(holder.image);
        Picasso.with(context)
                .load(standardActivity.getThumbnail())
                .placeholder(R.drawable.ic_file_image)
                .error(R.drawable.ic_image_broken)
                .resize(200,200)
                .into(holder.image);
        holder.name.setText(standardActivity.getName());
    }

    @Override
    public int getItemCount() {
        return arrayListActivties.size();
    }


    //Custom Holder
    private class CustomViewHolder extends RecyclerView.ViewHolder{

        final ImageView image;
        final TextView name;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cardsView != null){
                        cardsView.createDialogParamsActivity(arrayListActivties.get(getAdapterPosition()));
                    }
                    if(newCardView != null){
                        newCardView.createDialogParamsActivity(arrayListActivties.get(getAdapterPosition()));
                    }
                }
            });
            image = (ImageView) view.findViewById(R.id.iv_image);
            name = (TextView) view.findViewById(R.id.tv_name);
        }
    }

}
