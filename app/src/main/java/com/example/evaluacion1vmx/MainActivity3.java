package com.example.evaluacion1vmx;

import android.content.Intent; // Import necesario para iniciar la Activity2
import android.os.Bundle;
import android.os.Handler; // Import necesario para el retraso
import android.os.Looper; // Import necesario para el hilo principal
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.activity_main3); 

        
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);

          
            finish();
        }, 0);
    }
}
