package com.alan.slidingmenu.Classe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alan.slidingmenu.R;
import java.lang.Object;
import java.lang.Override;

/**
 * Created by ifigm on 13/02/2016.
 */
public class CustomPagerAdapter extends PagerAdapter {
    private final Context context;
    private final LayoutInflater mLayoutInflater;
    int[] mResources;

    public CustomPagerAdapter(Context pContext) {
       context = pContext;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void setmResources(String[] image){
        int compteur = 0;
        int i = 0;
        while(i<image.length){
            if(image[i]!=null)
                compteur++;
            i++;
        }
        mResources = new int[compteur];
        for(i = 0;i<mResources.length;i++){
            if(image[i]!=null)
            mResources[i]=context.getResources().getIdentifier(image[i],"drawable",context.getPackageName());
        }
    }
}
