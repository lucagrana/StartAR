package com.example.startar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;

import com.google.ar.core.Frame;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class ArNav extends AppCompatActivity {

    private LocationScene locationScene;
    private  ModelRenderable andyRenderable = null;
    private ArSceneView arSceneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_nav);
        arSceneView = findViewById(R.id.arSceneView);

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
                                    andyRenderable = andy.get();

                                } catch (InterruptedException | ExecutionException ex) {
                                    uk.co.appoly.sceneform_example.DemoUtils.displayError(ArNav.this, "Unable to load renderables", ex);


                                }
                                return null;
                            }
                        });
        arSceneView
                .getScene()
                .addOnUpdateListener (
                        new Scene.OnUpdateListener() {
                            @Override
                            public void onUpdate(FrameTime frameTime) {
                                Frame frame = arSceneView.getArFrame();
                                if (locationScene == null) {

                                    locationScene = new LocationScene(ArNav.this, arSceneView);
                                    locationScene.mLocationMarkers.add(
                                            new LocationMarker(
                                                    9.29280804860439,
                                                    45.49454033279547,
                                                    ArNav.this.getAndy()));
                                }


                                if (locationScene != null) {
                                    locationScene.processFrame(frame);
                                }

                            }
                        });

    }

    private Node getAndy() {
        Node base = new Node();
        base.setRenderable(andyRenderable);
        final Context c = this;
        base.setOnTapListener(new Node.OnTapListener() {
            @Override
            public void onTap(HitTestResult v, MotionEvent event) {
                Toast.makeText(
                        c, "Andy touched.", Toast.LENGTH_LONG)
                        .show();
            }
        });
        return base;
    }

}
