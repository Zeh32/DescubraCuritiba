package com.example.jgaug.descubracuritiba;

/**
 * Created by eadcn on 12/01/2018.
 */

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


/**
 * Created by RenanTeles on 31/10/2016.
 */

public class GlideUtil {

    //utilizando caching strategy: importante notar que ele guarda a url referente a imagem em cache,
    //logo se a imagem for alterada no lado do servidor mas continuar com a mesma url, no lado do cliente
    //a imagem sera sempre a mesma. Logo sempre que uma imagem for atualizada, uma nova url deverá ser criada
    //para ela

    public static void loadImage(final Context context, final String imageUrl, final ImageView imageView,
                                 final ProgressBar progress){
        if (progress != null){
            progress.setVisibility(View.VISIBLE);
        }

        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        try {
                            progress.setVisibility(View.GONE);
                        } catch(NullPointerException nullException) {
                            nullException.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        System.out.println("Glide.loadImage: "+"FromMemory- "+isFromMemoryCache);
                        System.out.println("Glide.loadImage: "+"FirstResource- "+isFirstResource);
                        try {
                            progress.setVisibility(View.GONE);
                        } catch(NullPointerException nullException) {
                            nullException.printStackTrace();
                        }
                        return false;
                    }
                })
                .error(null)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.itinerary_placeholder)
                .into(imageView);
    }

    public static void loadImage(final Context context, final String imageUrl, final ImageView imageView){
        Glide.with(context)
                .load(imageUrl)
                .priority(Priority.IMMEDIATE)
                //.placeholder(context.getResources().getDrawable(R.drawable.ic_placeholder_gde))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(context.getResources().getDrawable(R.drawable.itinerary_placeholder))
                .into(imageView);
    }

    public static void loadImage(final Context context, final int resource, final ImageView imageView){
        Glide.with(context)
                .load(resource)
                .into(imageView);
    }

    public static void loadImage(final Context context, final Uri resource, final ImageView imageView){
        Glide.with(context)
                .load(resource)
                .into(imageView);
    }

    public static void loadImageFinal(final Context context, final String imageUrl, final ImageView imageView){
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadCircleImage(final Context context, final int resource, final ImageView imageView){
        Glide.with(context)
                .load(resource)
//                .transform(new CircleTransform(context))
//                .dontAnimate()
                //.error(R.drawable.place_holder)
                //.placeholder(R.drawable.place_holder)
                .into(imageView);
    }

    public static void loadImage(final Context context, final String imageUrl, final ImageView imageView, final GlideResponseListener listener){

        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        System.out.println("glide_exception "+e.toString());
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        System.out.println("Glide.loadImage: "+"FromMemory- "+isFromMemoryCache);
                        System.out.println("Glide.loadImage: "+"FirstResource- "+isFirstResource);
                        listener.onResponse();
                        return false;
                    }
                })
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImage(final Context context, final String imageUrl, final ImageView imageView,
                                 final ProgressBar progress, final GlideResponseListener listener){
        if (progress != null){
            progress.setVisibility(View.VISIBLE);
        }

        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        try {
                            progress.setVisibility(View.GONE);
                        } catch(NullPointerException nullException) {
                            nullException.printStackTrace();
                        }
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        System.out.println("Glide.loadImage: "+"FromMemory- "+isFromMemoryCache);
                        System.out.println("Glide.loadImage: "+"FirstResource- "+isFirstResource);
                        try {
                            progress.setVisibility(View.GONE);
                        } catch(NullPointerException nullException) {
                            nullException.printStackTrace();
                        }
                        listener.onResponse();
                        return false;
                    }
                })
                .error(null)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImageTransform(final Context context, final String image, final Transformation transformation,
                                          final ImageView imageView){
        Glide.with(context)
                .load(image)
                .bitmapTransform(transformation)
                .into(imageView);
    }

    public static void loadImageTransform(final Context context, final int image, final Transformation transformation,
                                          final ImageView imageView){
        Glide.with(context)
                .load(image)
                .crossFade(0)
                .bitmapTransform(transformation)
                .into(imageView);
    }

    public interface GlideResponseListener {
        void onResponse();
        void onException();
    }

}

