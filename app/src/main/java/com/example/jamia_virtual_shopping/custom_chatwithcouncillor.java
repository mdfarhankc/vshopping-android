package com.example.jamia_virtual_shopping;
 import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_chatwithcouncillor extends BaseAdapter{
    private Context context;
    String[] sid,sname;
    public custom_chatwithcouncillor(Context applicationContext, String[] sid,  String[] sname) {
        this.context=applicationContext;
        this.sid=sid;
//        this.photo1=photo;
        this.sname=sname;
    }

    @Override
    public int getCount() {
        return sname.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.customchatwithstudent,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView69);
//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);
        tv1.setTextColor( Color.BLACK);//color setting
//        tv3.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
        SharedPreferences sh1= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor e1=sh1.edit();
        e1.putString( "sid",sid[i] );
        e1.putString( "sname",sname[i] );
        e1.commit();
//                //in the case of custom page the intent

//            }
//        } );

        tv1.setText(sname[i]);
//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("url","");
//        String url=ip+photo1[i];

//        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);//circle





        return gridView;
    }
}