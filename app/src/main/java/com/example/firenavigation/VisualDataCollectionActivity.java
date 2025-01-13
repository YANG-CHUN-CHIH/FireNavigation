package com.example.firenavigation;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VisualDataCollectionActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private static final String SERVER_IP = MainActivity.SERVER_IP;
    private static final int SERVER_PORT = MainActivity.SERVER_PORT;
    private EditText areaNameEdtTxt;
    private String areaName;
    private Button StartBtn;
    private Button finishBtn;
    private Button trainBtn;
    private ProgressBar progressBar;
    private int number_of_collection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_collection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Enabling Wifi...", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Allow network operations in the main thread (not recommended for production code)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        areaNameEdtTxt = findViewById(R.id.AreaNameEdtTxt);

        registerReceiver(new BroadcastReceiver() {
            @SuppressLint("MissingPermission")
            @Override
            public void onReceive(Context context, Intent intent) {
                scanResults = wifiManager.getScanResults();
                for (ScanResult scanResult : scanResults) {
                    String ssid = scanResult.SSID;
                    String bssid = scanResult.BSSID;
                    int rssi = scanResult.level;
                    // 收集RSSI值
                    Toast.makeText(VisualDataCollectionActivity.this, "SSID: " + ssid + ", BSSID: " + bssid + ", RSSI: " + rssi, Toast.LENGTH_SHORT).show();
                    if (areaName != null && !areaName.isEmpty()) {
                        System.out.println("Room: " + areaName + ", SSID: " + ssid +  ", BSSID: " + bssid + ", RSSI: " + rssi + ", Number of collection: " + number_of_collection);
                        // Send RSSI values to server
                        sendMessagesToServer(areaName + "," + ssid + "," + bssid + "," + rssi + "," + number_of_collection);
                    }
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(5);


        StartBtn = findViewById(R.id.StartBtn);

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areaName = areaNameEdtTxt.getText().toString();
                //System.out.println("Room name is " + roomName);
                if (areaName != null && !areaName.isEmpty()) {
                    wifiManager.startScan();
                    progressBar.incrementProgressBy(1);
                    number_of_collection++;
                } else {
                    Toast.makeText(VisualDataCollectionActivity.this, "Please enter an area name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishBtn = findViewById(R.id.finishBtn);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VisualDataCollectionActivity.this, "Finish Scanning", Toast.LENGTH_SHORT).show();
                progressBar.setProgress(0);
                areaNameEdtTxt.setText("");
                sendMessagesToServer("Finish Scanning");
            }
        });

        trainBtn = findViewById(R.id.trainBtn);

        trainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VisualDataCollectionActivity.this, "Analyzing Data", Toast.LENGTH_SHORT).show();
                sendMessagesToServer("Training Data");
            }
        });

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