package com.example.firenavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MapView extends View{

    private Paint paint = new Paint();
    private static final int TILE_SIZE = 14; // 每個 tile 的大小
    private static final int ROWS = 50; // tile rows
    private static final int COLS = 75; // tile columns
    private static int[][] map = new int[ROWS][COLS];
    private TextView areaTextView;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 覆寫 onMeasure 方法來設置具體的內容大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 根據內容寬高設置測量規範
        int desiredWidth = TILE_SIZE * COLS + 4;
        int desiredHeight = TILE_SIZE * ROWS + 4;

        // 將寬高設置為指定大小或採用父級的測量規範
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        // 設定寬度
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize; // 父級規定了具體的大小
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize); // 父級設定了最大寬度
        } else {
            width = desiredWidth; // 使用我們自定義的寬度
        }

        // 設定高度
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize; // 父級規定了具體的大小
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize); // 父級設定了最大高度
        } else {
            height = desiredHeight; // 使用我們自定義的高度
        }

        // 設置最終的大小
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initializeMap();

        updateMap();

        repaintMap(canvas);


    }

    public void updateMap() {
        if (areaTextView == null) {
            System.out.println("areaTextView is null");
        } else if (areaTextView.getText().equals("C1")) {
            for (int row = 7; row <= 15; row++) {
                for (int col = 8; col <= 11; col++) {
                    if (row == 7 || col == 8 || row == 15 || col == 11) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        }  else if (areaTextView.getText().equals("C2")) {
            for (int row = 15; row <= 30; row++) {
                for (int col = 5; col <= 11; col++) {
                    if (row == 15 || col == 5 || row == 30 || col == 11) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("C3")) {
            for (int row = 30; row <= 42; row++) {
                for (int col = 5; col <= 13; col++) {
                    if (row == 30 || col == 5 || row == 42 || col == 13) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("C4")) {
            for (int row = 21; row <= 26; row++) {
                for (int col = 11; col <= 38; col++) {
                    if (row == 21 || col == 11 || row == 26 || col == 38) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("C5")) {
            for (int row = 21; row <= 26; row++) {
                for (int col = 38; col <= 63; col++) {
                    if (row == 21 || col == 38 || row == 26 || col == 63) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("C6")) {
            for (int row = 21; row <= 34; row++) {
                for (int col = 63; col <= 68; col++) {
                    if (row == 21 || col == 63 || row == 34 || col == 68) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("C7")) {
            for (int row = 34; row <= 43; row++) {
                for (int col = 63; col <= 68; col++) {
                    if (row == 34 || col == 63 || row == 43 || col == 68) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("S1")) {
            for (int row = 30; row <= 42; row++) {
                for (int col = 0; col <= 5; col++) {
                    if (row == 30 || col == 0 || row == 42 || col == 5) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("S2")) {
            for (int row = 42; row <= 49; row++) {
                for (int col = 10; col <= 24; col++) {
                    if (row == 42 || col == 10 || row == 49 || col == 24) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("S3")) {
            for (int row = 43; row <= 49; row++) {
                for (int col = 63; col <= 74; col++) {
                    if (row == 43 || col == 63 || row == 49 || col == 74) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("BC")) {
            for (int row = 15; row <= 30; row++) {
                for (int col = 2; col <= 5; col++) {
                    if (row == 15 || col == 2 || row == 30 || col == 5) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("P1")) {
            for (int row = 7; row <= 15; row++) {
                for (int col = 0; col <= 8; col++) {
                    if (row == 7 || col == 0 || row == 15 || col == 8) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("R1")) {
            for (int row = 26; row <= 42; row++) {
                for (int col = 33; col <= 38; col++) {
                    if (row == 26 || col == 33 || row == 42 || col == 38) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("R2")) {
            for (int row = 26; row <= 42; row++) {
                for (int col = 43; col <= 48; col++) {
                    if (row == 26 || col == 43 || row == 42 || col == 48) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("R3")) {
            for (int row = 26; row <= 42; row++) {
                for (int col = 48; col <= 53; col++) {
                    if (row == 26 || col == 48 || row == 42 || col == 53) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("R4")) {
            for (int row = 26; row <= 42; row++) {
                for (int col = 53; col <= 63; col++) {
                    if (row == 26 || col == 53 || row == 42 || col == 63) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("B1")) {
            for (int row = 26; row <= 31; row++) {
                for (int col = 68; col <= 74; col++) {
                    if (row == 26 || col == 68 || row == 31 || col == 74) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        } else if (areaTextView.getText().equals("B2") || areaTextView.getText().equals("B3")) {
            for (int row = 31; row <= 37; row++) {
                for (int col = 68; col <= 74; col++) {
                    if (row == 31 || col == 68 || row == 37 || col == 74) {
                        map[row][col] = Color.GRAY;
                    } else {
                        map[row][col] = Color.parseColor("#ffca18");
                    }
                }
            }
        }
    }

    private void initializeMap() {
        // 教授室1
        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 11; col++) {
                if (row == 0 || col == 0 || row == 7 || col == 11) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#97c79b");
                }

            }
        }

        // 教授室2
        for (int row = 7; row <= 15; row++) {
            for (int col = 0; col <= 8; col++) {
                if (row == 7 || col == 0 || row == 15 || col == 8) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#97c79b");
                }
            }
        }

        // 陽台1
        for (int row = 15; row <= 30; row++) {
            for (int col = 2; col <= 5; col++) {
                if (row == 15 || col == 2 || row == 30 || col == 5) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#d5a3bf");
                }
            }
        }

        // 走廊1
        for (int row = 7; row <= 15; row++) {
            for (int col = 8; col <= 11; col++) {
                if (row == 7 || col == 8 || row == 15 || col == 11) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 走廊2
        for (int row = 15; row <= 30; row++) {
            for (int col = 5; col <= 11; col++) {
                if (row == 15 || col == 5 || row == 30 || col == 11) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 樓梯1
        for (int row = 30; row <= 42; row++) {
            for (int col = 0; col <= 5; col++) {
                if (row == 30 || col == 0 || row == 42 || col == 5) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#f9ebd6");
                }
            }
        }

        // 討論室1
        for (int row = 42; row <= 49; row++) {
            for (int col = 0; col <= 10; col++) {
                if (row == 42 || col == 0 || row == 49 || col == 10) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#fdf8a4");
                }
            }
        }

        // 走廊3
        for (int row = 30; row <= 42; row++) {
            for (int col = 5; col <= 13; col++) {
                if (row == 30 || col == 5 || row == 42 || col == 13) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 樓梯2
        for (int row = 42; row <= 49; row++) {
            for (int col = 10; col <= 24; col++) {
                if (row == 42 || col == 10 || row == 49 || col == 24) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#f9ebd6");
                }
            }
        }

        // 電梯1
        for (int row = 34; row <= 42; row++) {
            for (int col = 13; col <= 18; col++) {
                if (row == 34 || col == 13 || row == 42 || col == 18) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#a09ff4");
                }
            }
        }

        // 共同整合實驗室1
        for (int row = 0; row <= 21; row++) {
            for (int col = 11; col <= 23; col++) {
                if (row == 0 || col == 11 || row == 21 || col == 23) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#e7cacb");
                }
            }
        }

        // 陽台2
        for (int row = 2; row <= 5; row++) {
            for (int col = 23; col <= 63; col++) {
                if (row == 2 || col == 23 || row == 5 || col == 63) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#d5a3bf");
                }
            }
        }

        // 討論室2
        for (int row = 5; row <= 21; row++) {
            for (int col = 23; col <= 31; col++) {
                if (row == 5 || col == 23 || row == 21 || col == 31) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#fdf8a4");
                }
            }
        }

        // 討論室3
        for (int row = 5; row <= 21; row++) {
            for (int col = 31; col <= 38; col++) {
                if (row == 5 || col == 31 || row == 21 || col == 38) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#fdf8a4");
                }
            }
        }

        // 走廊4
        for (int row = 21; row <= 26; row++) {
            for (int col = 11; col <= 38; col++) {
                if (row == 21 || col == 11 || row == 26 || col == 38) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 走廊5
        for (int row = 21; row <= 26; row++) {
            for (int col = 38; col <= 63; col++) {
                if (row == 21 || col == 38 || row == 26 || col == 63) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 共同整合實驗室2
        for (int row = 26; row <= 42; row++) {
            for (int col = 18; col <= 28; col++) {
                if (row == 26 || col == 18 || row == 42 || col == 28) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#e7cacb");
                }
            }
        }

        // 陽台3
        for (int row = 42; row <= 45; row++) {
            for (int col = 24; col <= 63; col++) {
                if (row == 42 || col == 24 || row == 45 || col == 63) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#d5a3bf");
                }
            }
        }

        // 研究生室1
        for (int row = 26; row <= 42; row++) {
            for (int col = 28; col <= 33; col++) {
                if (row == 26 || col == 28 || row == 42 || col == 33) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 研究生室2
        for (int row = 26; row <= 42; row++) {
            for (int col = 33; col <= 38; col++) {
                if (row == 26 || col == 33 || row == 42 || col == 38) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 研究生室3
        for (int row = 26; row <= 42; row++) {
            for (int col = 38; col <= 43; col++) {
                if (row == 26 || col == 38 || row == 42 || col == 43) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 研究生室4
        for (int row = 26; row <= 42; row++) {
            for (int col = 43; col <= 48; col++) {
                if (row == 26 || col == 43 || row == 42 || col == 48) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 研究生室5
        for (int row = 26; row <= 42; row++) {
            for (int col = 48; col <= 53; col++) {
                if (row == 26 || col == 48 || row == 42 || col == 53) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 共同整合實驗室3
        for (int row = 26; row <= 42; row++) {
            for (int col = 53; col <= 63; col++) {
                if (row == 26 || col == 53 || row == 42 || col == 63) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#e7cacb");
                }
            }
        }

        // 研究生室6
        for (int row = 5; row <= 21; row++) {
            for (int col = 38; col <= 43; col++) {
                if (row == 5 || col == 38 || row == 21 || col == 43) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 研究生室7
        for (int row = 5; row <= 21; row++) {
            for (int col = 43; col <= 48; col++) {
                if (row == 5 || col == 43 || row == 21 || col == 48) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#90cbf0");
                }
            }
        }

        // 共同整合實驗室4
        for (int row = 5; row <= 21; row++) {
            for (int col = 48; col <= 55; col++) {
                if (row == 5 || col == 48 || row == 21 || col == 55) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#e7cacb");
                }
            }
        }

        // 共同整合實驗室5
        for (int row = 5; row <= 21; row++) {
            for (int col = 55; col <= 63; col++) {
                if (row == 5 || col == 55 || row == 21 || col == 63) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#e7cacb");
                }
            }
        }

        // 教授室3
        for (int row = 0; row <= 7; row++) {
            for (int col = 63; col <= 74; col++) {
                if (row == 0 || col == 63 || row == 7 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#97c79b");
                }

            }
        }

        // 教授室4
        for (int row = 7; row <= 15; row++) {
            for (int col = 67; col <= 74; col++) {
                if (row == 7 || col == 67 || row == 15 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#97c79b");
                }
            }
        }

        // 機房資訊電信機房
        for (int row = 15; row <= 21; row++) {
            for (int col = 67; col <= 74; col++) {
                if (row == 15 || col == 67 || row == 21 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 走廊6
        for (int row = 21; row <= 34; row++) {
            for (int col = 63; col <= 68; col++) {
                if (row == 21 || col == 63 || row == 34 || col == 68) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 廁所1
        for (int row = 26; row <= 31; row++) {
            for (int col = 68; col <= 74; col++) {
                if (row == 26 || col == 68 || row == 31 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 廁所2
        for (int row = 31; row <= 37; row++) {
            for (int col = 68; col <= 74; col++) {
                if (row == 31 || col == 68 || row == 37 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 走廊7
        for (int row = 34; row <= 43; row++) {
            for (int col = 63; col <= 68; col++) {
                if (row == 34 || col == 63 || row == 43 || col == 68) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.WHITE;
                }
            }
        }

        // 電梯2
        for (int row = 37; row <= 43; row++) {
            for (int col = 68; col <= 74; col++) {
                if (row == 37 || col == 68 || row == 43 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#a09ff4");
                }
            }
        }

        // 樓梯3
        for (int row = 43; row <= 49; row++) {
            for (int col = 63; col <= 74; col++) {
                if (row == 43 || col == 63 || row == 49 || col == 74) {
                    map[row][col] = Color.GRAY;
                } else {
                    map[row][col] = Color.parseColor("#f9ebd6");
                }
            }
        }

    }

    private void repaintMap(Canvas canvas) {
        // 設置背景顏色
        canvas.drawColor(Color.WHITE);

        // 設定畫筆屬性
        paint.setColor(Color.GRAY);  // 格子默認顏色
        paint.setStyle(Paint.Style.FILL);  // 填充風格


        // 繪製 10x15 的 tile map
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // 計算每個 tile 的左上角和右下角的坐標
                int left = col * TILE_SIZE;
                int top = row * TILE_SIZE;
                int right = left + TILE_SIZE;
                int bottom = top + TILE_SIZE;

                paint.setColor(map[row][col]);

                // 畫每個格子
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        // 設置畫筆屬性
        paint.setColor(Color.BLACK);  // 設置線條顏色
        paint.setStrokeWidth(3);      // 設置線條寬度

//        // 畫水平線條，根據 tileHeight 確定間隔
//        for (int y = 0; y <= TILE_SIZE * ROWS; y += TILE_SIZE) {
//            canvas.drawLine(0, y, TILE_SIZE * COLS, y, paint);
//        }
//
//        // 畫垂直線條，根據 tileWidth 確定間隔
//        for (int x = 0; x <= TILE_SIZE * COLS; x += TILE_SIZE) {
//            canvas.drawLine(x, 0, x, TILE_SIZE * ROWS, paint);
//        }
    }

    public void setAreaTextView(TextView areaTextView) {
        this.areaTextView = areaTextView;
    }
}
