package com.example.startar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import uk.co.appoly.arcorelocation.LocationScene;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class ArNav extends AppCompatActivity {

    private LocationScene locationScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_nav);

        final CompletableFuture<ModelRenderable> andy = ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build();


        CompletableFuture.allOf(andy)
                .handle(
                        new BiFunction<Void, Throwable, Object>() {
                            @Override
                            public Object apply(Void notUsed, Throwable throwable) {
                                if (throwable != null) {
                                    uk.co.appoly.sceneform_example.DemoUtils.displayError(ArNav.this, "Unable to load renderables", throwable);
                                    return null;
                                }

                                try {
                                    ModelRenderable andyRenderable = andy.get();

                                } catch (InterruptedException | ExecutionException ex) {
                                    uk.co.appoly.sceneform_example.DemoUtils.displayError(ArNav.this, "Unable to load renderables", ex);
                                    Log.d("aieie", "hdhd");

                                }
                                return null;
                            }
                        });

    }
}
