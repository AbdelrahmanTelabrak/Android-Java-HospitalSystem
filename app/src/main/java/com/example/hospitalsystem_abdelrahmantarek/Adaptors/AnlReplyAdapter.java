package com.example.hospitalsystem_abdelrahmantarek.Adaptors;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AnalysisReply;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.databinding.ItemAnalysisBinding;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AnlReplyAdapter extends RecyclerView.Adapter<AnlReplyAdapter.AnlReplyHolder> {
    ArrayList<AnalysisReply> list;
    URL url;
    Context context;
    private static final String TAG = "AnlReplyAdapter";

    public AnlReplyAdapter(ArrayList<AnalysisReply> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AnlReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnalysisBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_analysis, parent, false);
        return new AnlReplyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnlReplyHolder holder, int position) {
        AnalysisReply reply = list.get(position);
        holder.binding.tvIAName.setText(reply.getEmpName());
        Log.i(TAG, "onBindViewHolder: emp name = "+ reply.getEmpName());
        Log.i(TAG, "onBindViewHolder: image url = "+ reply.getImageUrl());
        Picasso.get().load(reply.getImageUrl()).into(holder.binding.ivIAImage);
        holder.binding.ibIADownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImageNew("record", reply.getImageUrl(), context);
//                try {
//                    Log.i(TAG, "onClick: ");
//                    url = new URL(reply.getImageUrl());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                new DownloadTask().execute(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AnlReplyHolder extends RecyclerView.ViewHolder{
        ItemAnalysisBinding binding;
        public AnlReplyHolder(@NonNull ItemAnalysisBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void downloadImageNew(String filename, String downloadUrlOfImage, Context context){
        try{
            DownloadManager dm = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename + ".jpg");
            dm.enqueue(request);
        }catch (Exception e){
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadTask extends AsyncTask<URL,Void,Bitmap>{

        protected Bitmap doInBackground(URL...urls){
            URL url = urls[0];
            HttpURLConnection connection = null;
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
