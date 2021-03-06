package com.example.dailyreader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.dailyreader.R;
import com.example.dailyreader.databinding.AddBooksFragmentBinding;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class AddBooksFragment extends Fragment{
    private AddBooksFragmentBinding addBooksBinding;
    private BookViewModel bookViewModel;
    private File parentFolder;
    private File[] currentFiles;
    private final String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public AddBooksFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        addBooksBinding = AddBooksFragmentBinding.inflate(inflater, container, false);
        View view = addBooksBinding.getRoot();
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(BookViewModel.class);

        int permissionCode = ContextCompat.checkSelfPermission(requireContext(), permission[0]);
        if (permissionCode != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }

        if (Environment.getExternalStorageDirectory().exists()) {
            parentFolder = Environment.getExternalStorageDirectory();
            currentFiles = parentFolder.listFiles();
            if (currentFiles != null || currentFiles.length != 0) {
                createListView(currentFiles);
            } else {
                Toast.makeText(getContext(), "No subfolder/file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No SD card.", Toast.LENGTH_SHORT).show();
        }

        addBooksBinding.folderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentFiles[position].isFile() && currentFiles[position].getName().endsWith(".txt")) {
                    String filepath = currentFiles[position].getAbsolutePath();
                    int length = currentFiles[position].getName().length() - 4;
                    String bookName = currentFiles[position].getName().substring(0, length);
                    List<Integer> readPosition = new ArrayList<>();
                    readPosition.add(0);
                    CompletableFuture<Book> result = bookViewModel.findByNameFuture(bookName);
                    Book book = null;
                    try {
                        book = result.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (book != null) {
                        Toast.makeText(getContext(), "Book exists", Toast.LENGTH_SHORT).show();
                    } else {
                        bookViewModel.insert(new Book(bookName, filepath, readPosition, 0));
                        Toast.makeText(getContext(), "Add to the bookshelf successfully", Toast.LENGTH_SHORT).show();
                    }


                } else if (currentFiles[position].isDirectory()){
                    File[] tmpFiles = currentFiles[position].listFiles();
                    if (tmpFiles != null) {
                        parentFolder = currentFiles[position];
                        currentFiles = tmpFiles;
                        createListView(currentFiles);
                    }
                }
            }
        });

        addBooksBinding.backBtn.setOnClickListener(v -> {
            if (!parentFolder.getAbsoluteFile().equals(Environment.getExternalStorageDirectory())) {
                parentFolder = parentFolder.getParentFile();
                assert parentFolder != null;
                currentFiles = parentFolder.listFiles();
                assert currentFiles != null;
                createListView(currentFiles);
            } else {
                Toast.makeText(getContext(), "This is the root", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void createListView(File[] currentFiles) {
        ArrayList<Map<String, Object>> items = new ArrayList<>();
        for (File currentFile : currentFiles) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("filename", currentFile.getName());
            if (currentFile.isDirectory()) {
                item.put("image", R.drawable.folder_image);
            } else {
                item.put("image", R.drawable.file_image);
            }

            items.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), items, R.layout.file_item_layout, new String[]{"image", "filename"}, new int[]{R.id.image, R.id.filename});
        addBooksBinding.folderList.setAdapter(adapter);
        String path = "You are at: " + parentFolder.getAbsolutePath();
        addBooksBinding.path.setText(path);

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), permission, 321);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBooksBinding = null;
    }
}
