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
        // Mantén esta línea para instalar el splash screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Asegúrate de que estás inflando el layout correcto para MainActivity3
        setContentView(R.layout.activity_main3); // Cambia 'activity_main3' al nombre real de tu layout

        // Retraso para simular la carga de la aplicación (puedes ajustar la duración)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Inicia MainActivity2 después del retraso
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);

            // Finaliza MainActivity3 para que el usuario no pueda volver atrás presionando el botón de retroceso
            finish();
        }, 0);
    }
}
