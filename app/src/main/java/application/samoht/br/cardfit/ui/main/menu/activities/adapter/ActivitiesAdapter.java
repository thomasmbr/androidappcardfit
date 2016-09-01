package application.samoht.br.cardfit.ui.main.menu.activities.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.models.StandardActivity;

/**
 * Created by Thomas on 17/08/16.
 */
public class ActivitiesAdapter extends RecyclerView.Adapter {

    private ArrayList<StandardActivity> arrayListActivties;
    private Context context;

    public ActivitiesAdapter(ArrayList<StandardActivity> arrayListActivities, Context context){
        this.arrayListActivties = arrayListActivities;
        this.context = context;
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
        Picasso.with(context).load(standardActivity.getThumbnail()).resize(200,200).into(holder.image);
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
                    createDialogDetails(view,arrayListActivties.get(getAdapterPosition()));
                }
            });
            image = (ImageView) view.findViewById(R.id.iv_image);
            name = (TextView) view.findViewById(R.id.tv_name);
        }

        private void createDialogDetails(View view, StandardActivity activity){
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_details);
            dialog.setTitle(activity.getName());
            TextView description = (TextView) dialog.findViewById(R.id.details_description);
            description.setText(activity.getDescription());
            TextView note = (TextView) dialog.findViewById(R.id.details_note);
            note.setText(activity.getNote());
            ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
            Picasso.with(context).load(activity.getThumbnail()).resize(500,500).into(imageView);
            Button dialogButtonClose = (Button) dialog.findViewById(R.id.dialog_button_close);
            dialogButtonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
