package com.btplanner.btripex.ui.utils;

import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.module.AppGlideModule;

// new since Glide v4
@GlideModule
/*
@Excludes(OkHttpLibraryGlideModule.class) // initialize OkHttp manually
*/
public final class BtripGlideModule extends AppGlideModule {
/*    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        OkHttpClient okHttpClient = new OkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }*/
}
