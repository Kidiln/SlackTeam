package com.slack.slackteam.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.slack.slackteam.R;
import com.slack.slackteam.model.SLMember;
import com.slack.slackteam.utils.SLUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by jacobkoikkara on 8/12/15.
 */
public class SLTeamGridAdapter extends BaseAdapter {

    private int memberCount = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private Context mContext = null;
    private SLMember[] mSLMembers = null;
    private LayoutInflater inflater = null;


    public SLTeamGridAdapter(Context context, SLMember[] slMembers) {
        this.mContext = context;
        this.memberCount = slMembers.length;
        this.mSLMembers = slMembers;

    }

    @Override
    public int getCount() {
        return memberCount;
    }

    @Override
    public Object getItem(int position) {
        return mSLMembers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SLHolder holder = null;

        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_slmember, null);
            holder = new SLHolder();
            holder.imgSlIcon = (ImageView) convertView.findViewById(R.id.grid_membericon);
            holder.txtSlName = (TextView) convertView.findViewById(R.id.grid_membername);
            holder.fltSlMember = (FrameLayout) convertView.findViewById(R.id.fmlt_memberlayout);

            convertView.setTag(holder);
        } else {
            holder = (SLHolder) convertView.getTag();
        }

        SLUtils.showLog(position);

        Picasso.with(mContext)
                .load(mSLMembers[position].getProfile().getImage_192())
                .placeholder(R.drawable.empty_photo)   // optional
                .error(R.drawable.empty_photo)    // optional
                .resize(mWidth, mWidth)                        // optional
//                .rotate(90)                             // optional
                .into(holder.imgSlIcon);

        holder.fltSlMember.setBackgroundColor(Color.parseColor("#" + mSLMembers[position].getColor()));
        holder.txtSlName.setText(mSLMembers[position].getName());

        return convertView;
    }

    public void setWidthAndHeight(int width, int height) {

        mWidth = width;
        mHeight = height;
        notifyDataSetChanged();
    }

    public class SLHolder {
        TextView txtSlName;
        ImageView imgSlIcon;
        FrameLayout fltSlMember;
    }
}
