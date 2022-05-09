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
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ReadActivity extends AppCompatActivity{
    private ReadPageView readPageView;
    private BookViewModel bookViewModel;
    private Book book;
    private PopupWindow popupWindow;
    private GestureDetector gestureDetector;
    private List<Integer> finishReadPosition;
    private RecordReadTime recordReadTime;
    private int startLastPosition;
    private int positionIndex;
    private RandomAccessFile reader;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(BookViewModel.class);

        View.OnTouchListener touchListener = (v, event) -> gestureDetector.onTouchEvent(event);

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
        readPageView.setOnLongClickListener(v -> {
            showPopupWindow();
            return false;
        });

        loadBook(book.getFilepath());
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

    private void loadBook(String bookPath) {
        finishReadPosition = book.getReadPosition();
        positionIndex = finishReadPosition.size() - 1;
        startLastPosition = finishReadPosition.get(positionIndex);
        Toast.makeText(getApplicationContext(),""+startLastPosition,Toast.LENGTH_SHORT).show();

        try {
            File book = new File(bookPath);
            reader = new RandomAccessFile(book, "r");

        } catch (IOException e) {
            Toast.makeText(this, "The book does not exist.", Toast.LENGTH_SHORT).show();
        }
        loadPage(startLastPosition);

    }

    private void loadPage(int position) {
        StringBuilder onePageText = new StringBuilder();
        byte[] buffer = new byte[512];
        try {
            reader.seek(position);
            int hasRead;
            int totalRead = 0;
            while ((hasRead = reader.read(buffer)) != -1 && totalRead <= 1024) {
                onePageText.append(new String(buffer,0,hasRead));
                totalRead += hasRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        readPageView.setText(onePageText);
        readPageView.resize();
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
                startLastPosition += readPageView.getCharNum();
                finishReadPosition.add(startLastPosition);
                positionIndex++;
                if (finishReadPosition.get(positionIndex) >= new File(book.getFilepath()).length()) {
                    Toast.makeText(getApplicationContext(),"The last page" ,Toast.LENGTH_SHORT).show();
                } else {
                    loadPage(startLastPosition);
                }

            }
            if (e2.getX() - e1.getX() > 50) {
                if(positionIndex == 0) {
                    Toast.makeText(getApplicationContext(),"The first page" ,Toast.LENGTH_SHORT).show();
                } else {
                    finishReadPosition.remove(positionIndex);
                    positionIndex--;
                    startLastPosition = finishReadPosition.get(positionIndex);
                    loadPage(startLastPosition);
                }
            }

            return true;
        }
    }

}






