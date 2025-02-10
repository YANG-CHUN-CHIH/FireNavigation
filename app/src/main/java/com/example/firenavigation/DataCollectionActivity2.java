package com.example.firenavigation;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class DataCollectionActivity2 extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer, gyroscope;
    private WifiManager wifiManager;
    private TextView positionTextView, wifiTextView, azimuthTextView;

    private float[] gravity, geomagnetic;
    private float azimuth = 0f; // 方向角
    private float stepCount = 0;
    private float stepLength = 0.7f; // 假設每一步長 70cm
    private float x = 0, y = 0; // (x, y) 初始座標


    // 動態步長估計
    private float lastMaxAcceleration = 0;
    private float lastMinAcceleration = 9.8f; // 地球重力加速度
    private long lastStepTime = 0;
    private static final long STEP_INTERVAL_THRESHOLD = 300; // 兩步之間的最小間個

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_collection2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        positionTextView = findViewById(R.id.positionTextView);
        wifiTextView = findViewById(R.id.wifiTextView);
        azimuthTextView = findViewById(R.id.azimuthTextView);

        // 初始化感測器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // 初始化 Wi-Fi
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        // 註冊感測器監聽
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone();
            detectStep(event.values);
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone();
        }

        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                if (azimuth < 0) {
                    azimuth += 360;
                }
                azimuthTextView.setText(String.format("Azimuth: %.0f", azimuth));
            }
        }


    }

    // 偵測步伐 使用動態步長估計
    private void detectStep(float[] values) {
        float acceleration = (float) Math.sqrt(values[0] * values[0] + values[1] * values[1] + values[2] * values[2]);

        long currentTime = System.currentTimeMillis();

        if (acceleration > 11 && (currentTime - lastStepTime > STEP_INTERVAL_THRESHOLD)) { // 若超過步行偵測閾值，則計算步長
            stepCount++;
            lastStepTime = currentTime;

            // 動態步長估計公式
            stepLength = 0.4f + 0.2f * (float) Math.sqrt(Math.abs(lastMaxAcceleration - lastMinAcceleration));

            // 更新最大與最小加速度
            lastMaxAcceleration = Math.max(lastMaxAcceleration, acceleration);
            lastMinAcceleration = Math.min(lastMinAcceleration, acceleration);

            scanWifi();
            updatePosition();
        }
    }

    // 更新 x, y 座標
    private void updatePosition() {
        double radian = Math.toRadians(azimuth);
        x += stepLength * Math.cos(radian);
        y += stepLength * Math.sin(radian);
        positionTextView.setText("X: " + x + "m, Y: " + y + "m");
    }

    // Wi-Fi 掃描
    private void scanWifi() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        wifiManager.startScan();
        List<ScanResult> results = wifiManager.getScanResults();

        StringBuilder wifiInfo = new StringBuilder();
        for (ScanResult result : results) {
            String ssid = result.SSID;
            String bssid = result.BSSID;
            int rssi = result.level;
            Toast.makeText(DataCollectionActivity2.this, "SSID: " + ssid + ", BSSID: " + bssid + ", RSSI: " + rssi, Toast.LENGTH_SHORT).show();
            wifiInfo.append("SSID: ").append(result.SSID).append(", BSSID: ").append(result.BSSID).append(", RSSI: ").append(result.level).append("\n");
        }

        // wifiTextView.setText(wifiInfo.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}