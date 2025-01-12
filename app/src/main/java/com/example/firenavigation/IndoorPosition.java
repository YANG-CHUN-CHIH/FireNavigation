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
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IndoorPosition extends AppCompatActivity {

    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    private static final String SERVER_IP = MainActivity.SERVER_IP;
    private static final int SERVER_PORT = MainActivity.SERVER_PORT;
    private TextView areaTextView;
    private Button detectBtn;
    private MapView mapView;
    private String pressed_btn = "";
    private Handler handler = new Handler();
    private boolean isReceiving = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_indoor_position);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_indoor_position);

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
                    Toast.makeText(IndoorPosition.this, "SSID: " + ssid + ", BSSID: " + bssid + ", RSSI: " + rssi, Toast.LENGTH_SHORT).show();
                    System.out.println("SSID: " + ssid + ", BSSID: " + bssid + ", RSSI: " + rssi);
                    // Send RSSI values to server
                    sendMessagesToServer(ssid + "," + bssid + "," + rssi);
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        areaTextView = findViewById(R.id.areaTextView);
        areaTextView.setText("R4");

        mapView = findViewById(R.id.mapView);
        mapView.setAreaTextView(areaTextView);

        detectBtn = findViewById(R.id.detectBtn);
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifiManager.startScan();
                isReceiving = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isReceiving) {
                            receiveMessagesFromServer(); // 呼叫接收訊息的函數
                            handler.postDelayed(this, 3000); // 再次安排3秒後執行
                        }
                    }
                }, 0);
                pressed_btn = "detectBtn";
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

    private void receiveMessagesFromServer() {
        detectBtn.setEnabled(false);
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

                System.out.println("Connected to server");

                // Create a buffer to read the message
                byte[] buffer = new byte[1024]; // Adjust size as needed
                int bytesRead;

                // Read the incoming message
                if ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Decode the UTF-8 message
                    String message = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                    System.out.println("Message from server: " + message);

                    // Update the UI with the received message
                    runOnUiThread(() -> {
                        areaTextView.setText(message);
                        detectBtn.setEnabled(true); // Re-enable the button after receiving the message
                        isReceiving = false;
                    });
                } else {
                    System.out.println("No message received from server");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}