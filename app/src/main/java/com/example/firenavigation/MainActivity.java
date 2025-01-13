package com.example.firenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {


    private Button btnDataCollection;
    private Button btnIndoorPosition;
    private Button btnDataCollectionVisual;
    private Button btnIndoorPositionVisual;

    public static final String SERVER_IP = "192.168.0.182";
    public static final int SERVER_PORT = 34567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sendMessagesToServer("Main Activity");

        btnDataCollection = findViewById(R.id.btnDataCollection);
        btnDataCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessagesToServer("Collection Mode");
                Intent intent = new Intent(MainActivity.this, DataCollectionActivity.class);
                startActivity(intent);
            }
        });

        btnIndoorPosition = findViewById(R.id.btnIndoorPosition);
        btnIndoorPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessagesToServer("Position Mode");
                Intent intent = new Intent(MainActivity.this, IndoorPosition.class);
                startActivity(intent);
            }
        });

        // Activity mapping for visual navigation
        btnDataCollectionVisual = findViewById(R.id.btnDataCollectionVisual);
        btnDataCollectionVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To be implemented, server handling for visual navigation.
                Intent intent = new Intent(MainActivity.this, VisualDataCollectionActivity.class);
                startActivity(intent);
            }
        });
        btnIndoorPositionVisual = findViewById(R.id.btnIndoorPositionVisual);
        btnIndoorPositionVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To be implemented, server handling for visual navigation.
                Intent intent = new Intent(MainActivity.this, VisualIndoorPosition.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void sendMessagesToServer(String message) {
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                // 使用標準 UTF-8 編碼字節數組來發送訊息
                byte[] utf8Message = message.getBytes(StandardCharsets.UTF_8);
                outputStream.write(utf8Message); // 傳送字節數組
                outputStream.flush(); // 確保所有數據都被寫入輸出流

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}