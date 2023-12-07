package com.example.books;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.books.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final int PHOTO_REQUEST_CODE = 1000;
    private ActivityMainBinding binding;
    private Bitmap takenPhotoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveButton.setOnClickListener(v -> {

            String bookTitle = binding.bookTitle.getText().toString();
            String bookAuthor = binding.bookAuthors.getText().toString();
            String bookComment = binding.bookComment.getText().toString();
            float bookRating = binding.ratingBook.getRating();

            openDetailActivity(bookTitle, bookAuthor,bookComment,bookRating);
        });

        binding.bookImage.setOnClickListener(v->{
            openCamera();
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            if (data != null) {
                takenPhotoBitmap = data.getExtras().getParcelable("data");
                binding.bookImage.setImageBitmap(takenPhotoBitmap);
            } else {
                Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDetailActivity(String title, String author,
                                    String comment,
                                    float rating) {
        Book book = new Book(title, author, comment, rating);

        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.BOOK_KEY, book);
        intent.putExtra(DetailActivity.BITMAP_KEY, takenPhotoBitmap);
        startActivity(intent);
    }

}