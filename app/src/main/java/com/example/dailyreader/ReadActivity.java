package com.example.dailyreader;




import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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
import androidx.lifecycle.ViewModelProvider;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ReadActivity extends AppCompatActivity{
    private ReadPageView readPageView;
    private BookViewModel bookViewModel;
    private Book book;
    private BufferedReader reader;
    private final int bufferSize = 10000;
    private final CharBuffer buffer = CharBuffer.allocate(bufferSize);
    private int endPosition;
    private PopupWindow popupWindow;
    private GestureDetector gestureDetector;
    private int finishReadPosition;
    private RecordReadTime recordReadTime;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(BookViewModel.class);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        int bookId= getIntent().getIntExtra("bookInfo", 0);
        CompletableFuture<Book> bookCompletableFuture = bookViewModel.findByIdFuture(bookId);
        try {
            book = bookCompletableFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

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


        loadBook(book.getFilepath());
        finishReadPosition = book.getReadPosition();
        if (finishReadPosition >= bufferSize) {
            int itr = finishReadPosition / bufferSize;
            int skip = finishReadPosition % bufferSize;
            locateLastPosition(itr, skip);
        } else {
            endPosition = finishReadPosition;
            loadPage(endPosition, -2);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recordReadTime = new RecordReadTime(getApplication());
        recordReadTime.startRecord();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        book.setReadPosition(finishReadPosition);
        bookViewModel.update(book);
        recordReadTime.stopRecord();
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ReadActivity.this).inflate(R.layout.read_popup_window, null);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView backBtn = contentView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ReadActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button finishSetting = contentView.findViewById(R.id.finish);
        Button themeGreen = contentView.findViewById(R.id.theme_green);
        Button themeWhite = contentView.findViewById(R.id.theme_white);
        Button themeOriginal = contentView.findViewById(R.id.theme_original);
        Button textSizeS = contentView.findViewById(R.id.s_button);
        Button textSizeM = contentView.findViewById(R.id.m_button);
        Button textSizeL = contentView.findViewById(R.id.l_button);

        finishSetting.setOnClickListener(v -> popupWindow.dismiss());

        themeWhite.setOnClickListener(v -> ReadActivity.this.findViewById(R.id.read_activity_layout).setBackgroundColor(Color.WHITE));

        themeGreen.setOnClickListener(v -> ReadActivity.this.findViewById(R.id.read_activity_layout).setBackgroundColor(Color.parseColor("#C7EDCC")));

        themeOriginal.setOnClickListener(v -> ReadActivity.this.findViewById(R.id.read_activity_layout).setBackgroundColor(Color.parseColor("#BEA592")));

        textSizeS.setOnClickListener(v -> readPageView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18));

        textSizeM.setOnClickListener(v -> readPageView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20));


        textSizeL.setOnClickListener(v -> readPageView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22));

        popupWindow.showAsDropDown(findViewById(R.id.read_activity_layout));
    }

    private void loadBook(String book) {

        try {
            InputStream in = new FileInputStream(book);
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            reader.read(buffer);
        } catch (IOException e) {
            Toast.makeText(this, "The book does not exist.", Toast.LENGTH_SHORT).show();
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

    private void locateLastPosition(int itr, int skip) {

        if (itr > 0) {
            for (int i = 0; i < itr; i++) {
                try {
                    buffer.clear();
                    reader.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        endPosition = skip;
        loadPage(endPosition, -2);
    }

    private void updateBuffer()
    {
        try {
            buffer.clear();
            int readCharNumber;
            if ((readCharNumber = reader.read(buffer)) != bufferSize) {
                loadPage(0, readCharNumber);
            } else {
                loadPage(0, -2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
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
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() - e2.getX() > 50) {
                finishReadPosition += readPageView.getCharNum();
                if (finishReadPosition >= new File(book.getFilepath()).length()) {
                    Toast.makeText(getApplicationContext(),"The last page" ,Toast.LENGTH_SHORT).show();
                } else {
                    endPosition += readPageView.getCharNum();
                    if (endPosition >= bufferSize) {
                        updateBuffer();
                        endPosition = 0;
                    }
                    loadPage(endPosition, -2);
                }

            }
            if (e2.getX() - e1.getX() > 50) {
                if (finishReadPosition == 0) {
                    Toast.makeText(getApplicationContext(), "The first page", Toast.LENGTH_SHORT).show();
                }

            }

            return true;
        }
    }

}






