package com.jetruby.dribbbyviewer.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jetruby.dribbbyviewer.R;
import com.jetruby.dribbbyviewer.model.Shot;
import com.jetruby.dribbbyviewer.util.Helper;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Shot> {
    private Context context;
    private int resource;

    public GridViewAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
    }

    public void setDataSet(List<Shot> shots) {
        clear();
        addAll(shots);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.imageTitle = (TextView) view.findViewById(R.id.imageTitle);
            viewHolder.imageDescription = (TextView) view.findViewById(R.id.imageDescription);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Shot shot = getItem(position);
        String url = shot.getImages().getHidpi() != null ? shot.getImages().getHidpi() : shot.getImages().getNormal() != null ? shot.getImages().getNormal() : shot.getImages().getTeaser();
        if (Helper.isNetworkConnected(context)) {
            Picasso.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.loading_failed).into(viewHolder.imageView);
        } else {
            Picasso.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.loading_failed).networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.imageView);
        }
        viewHolder.imageTitle.setText(shot.getTitle());
        viewHolder.imageDescription.setText(Html.fromHtml(shot.getDescription()));

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView imageTitle;
        TextView imageDescription;
    }
}
