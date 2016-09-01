package application.samoht.br.cardfit.ui.newcard.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.Serie;
import application.samoht.br.cardfit.ui.newcard.INewCardView;
import application.samoht.br.cardfit.ui.newcard.NewCardActivity;

/**
 * Created by Thomas on 19/08/16.
 */
public class NewCardsAdapter extends RecyclerView.Adapter {

    private ArrayList<CardItem> arrayListCardItem;
    private NewCardActivity activity;

    public NewCardsAdapter(ArrayList<CardItem> arrayListCardItem, NewCardActivity activity){
        this.arrayListCardItem = arrayListCardItem;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_item_new, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        CustomViewHolder holder = (CustomViewHolder) viewHolder;
        CardItem cardItem = arrayListCardItem.get(position);
        Picasso.with(activity).load(cardItem.getThumbnail()).resize(200,200).into(holder.image);
        holder.name.setText(cardItem.getName());

        holder.series.setText(String.valueOf(cardItem.getSeries().size()));
        String strRep = "";
        String strCharge = "";
        ArrayList<Serie> listSeries = cardItem.getSeries();
        for (Serie serie:listSeries) {
            strRep = strRep + serie.getRepetition()+"/";
            strCharge = strCharge + serie.getCharge()+"/";
        }
        if(strRep.length() > 0)
            holder.repetitions.setText(strRep.substring(0,strRep.length()-1));
        if(strCharge.length() > 0)
            holder.charge.setText(strCharge.substring(0,strCharge.length()-1));
        holder.restTime.setText(String.valueOf(cardItem.getRestTime()));

    }

    @Override
    public int getItemCount() {
        return arrayListCardItem.size();
    }

    //Custom Holder
    private class CustomViewHolder extends RecyclerView.ViewHolder{

        final CheckBox checkBox;
        final ImageView image;
        final TextView name;
        final TextView series;
        final TextView repetitions;
        final TextView charge;
        final TextView restTime;

        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createDialogDetails(view.getContext(), arrayListCardItem.get(getAdapterPosition()));
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    createAlertRemoveItem(arrayListCardItem.get(getAdapterPosition()));
                    return false;
                }
            });
            checkBox = (CheckBox) view.findViewById(R.id.check);
            image = (ImageView) view.findViewById(R.id.thumbnail);
            name = (TextView) view.findViewById(R.id.tv_name);
            series = (TextView) view.findViewById(R.id.tv_series);
            repetitions = (TextView) view.findViewById(R.id.tv_repetitions);
            charge = (TextView) view.findViewById(R.id.tv_charge);
            restTime = (TextView) view.findViewById(R.id.tv_rest_time);
        }

        public void createDialogDetails(final Context context, CardItem cardItem){
            final Dialog dialog = new Dialog(context);
            dialog.setTitle(cardItem.getName());
            dialog.setContentView(R.layout.dialog_details);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
            Picasso.with(context).load(cardItem.getThumbnail()).resize(500,500).into(imageView);
            TextView tvDescription = (TextView) dialog.findViewById(R.id.details_description);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { Toast.makeText(context,"Abrir video youtube",Toast.LENGTH_SHORT).show(); }
            });
            tvDescription.setText(cardItem.getDescription());
            TextView tvNote = (TextView) dialog.findViewById(R.id.details_note);
            tvNote.setText(cardItem.getNote());
            Button dialogButtonClose = (Button) dialog.findViewById(R.id.dialog_button_close);
            dialogButtonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {dialog.dismiss();
                }
            });
            dialog.show();
        }

        public void createAlertRemoveItem(final CardItem cardItem){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(activity.getString(R.string.remove_activity))
                    .setPositiveButton(activity.getText(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            arrayListCardItem.remove(cardItem);
                            notifyDataSetChanged();
                            activity.changeVisibilityTextInfo();
                        }
                    })
                    .setNegativeButton(activity.getText(R.string.no),null)
                    .create()
                    .show();
        }
    }

}
