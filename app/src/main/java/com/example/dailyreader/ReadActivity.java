package com.example.dailyreader;




import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class ReadActivity extends AppCompatActivity{
    private ReadPageView readPageView;
    private BufferedReader reader;
    private final CharBuffer buffer = CharBuffer.allocate(8000);
    private int endPosition = 0;
    private final ArrayList<Integer> startPosition = new ArrayList<Integer>();
    private int positionIndex = 0;
    private PopupWindow popupWindow;
    private GestureDetector gestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        View v = this.getWindow().getDecorView();
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // pass the events to the gesture detector
                // a return value of true means the detector is handling it
                // a return value of false means the detector didn't
                // recognize the event
                return gestureDetector.onTouchEvent(event);

            }
        };
        readPageView = findViewById(R.id.read_page_view);
        gestureDetector= new GestureDetector(this, new GestureListener());
        readPageView.setOnTouchListener(touchListener);
        readPageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupWindow();
                return false;
            }
        });
        //get filepath and book name from home fragment adapter
        String book = getIntent().getStringExtra("book");
        startPosition.add(0);

        loadBook(book);
        loadPage(0, -2);
    }



    private void loadBook(String book) {

        try {
            InputStream in = new FileInputStream(book);
//            InputStream in = getAssets().open("HP.txt");
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            reader.read(buffer);
        } catch (IOException e) {
            Toast.makeText(this, "The book is not exist.", Toast.LENGTH_SHORT).show();
        }

        readPageView.setText(buffer);
    }

    private void loadPage(int position, int limit) {
        buffer.position(position);
        if (limit != -2) {
            buffer.limit(limit);
        }
        readPageView.setText(buffer);

    }

    private void updateBuffer()
    {
        try {
            buffer.clear();
            int readCharNumber = 0;
            if ((readCharNumber = reader.read(buffer)) != 8000) {
                loadPage(0, readCharNumber);
            } else {
                loadPage(0, -2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ReadActivity.this).inflate(R.layout.read_popup_window, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView backBtn = contentView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button finishSetting = contentView.findViewById(R.id.finish);
        finishSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(findViewById(R.id.read_activity_layout));
    }

    public void nextPage() {
        endPosition += readPageView.getCharNum();
        if (endPosition >= 8000) {
            updateBuffer();
            endPosition = 0;
        }
        loadPage(endPosition, -2);
        readPageView.resize();

//        endPosition += readPageView.getCharNum();
//        if (!startPosition.contains(endPosition)) {
//            startPosition.add(endPosition);
//            loadPage(startPosition.get(positionIndex));
//            readPageView.resize();
//        } else {
//            loadPage(startPosition.get(positionIndex + 1));
//        }
//        positionIndex++;
////        Toast.makeText(getApplicationContext(),"SP" + positionIndex + "EP" ,Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(),"SP" + startPosition + "EP" + endPosition,Toast.LENGTH_SHORT).show();
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("TAG","onDown: ");

            // don't return false here or else none of the other
            // gestures will work
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            showPopupWindow();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 50) {
                endPosition += readPageView.getCharNum();
                if (endPosition >= 8000) {
                    updateBuffer();
                    endPosition = 0;
                }
                loadPage(endPosition, -2);
                readPageView.resize();
            }
            if (e2.getX() - e1.getX() > 50) {
                if (positionIndex > 0) {
                    loadPage(startPosition.get(positionIndex-1), -2);
                    readPageView.resize();
                    positionIndex--;
                } else if (positionIndex == 0){
                    loadPage(0, -2);
                    readPageView.resize();
                    Toast.makeText(getApplicationContext(),"first page" ,Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }
    }
}






