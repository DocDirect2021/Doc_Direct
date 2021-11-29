package com.projetdam.docdirect;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.projetdam.docdirect.commons.ModelDoctor;

import java.util.ArrayList;

public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.ViewHolder>  {
    Context context;
    ArrayList<ModelDoctor> doctorArrayList;

    public AdapterDoctor.OnItemClickListener onItemClickListener;

    public AdapterDoctor(Context context, ArrayList<ModelDoctor> doctorArrayList) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_doctor,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(doctorArrayList.get(position).getName());
        //holder.artist.setText(doctorArrayList.get(position).getLikes());
        RequestOptions options=new RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_baseline_person_pin_24);
        Context context=holder.cover.getContext();
        Uri imgUri=doctorArrayList.get(position).getAvatar();
        Glide.with(context).load(imgUri).apply(options).fitCenter().override(150,150).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos,View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,artist;
        ImageView cover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvTitle);
            artist=itemView.findViewById(R.id.tvArtist);
            cover=itemView.findViewById(R.id.ivCover);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition(),view);
                }
            });

        }
    }
    public void setOnItemClickListener(AdapterDoctor.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;

    }

}
