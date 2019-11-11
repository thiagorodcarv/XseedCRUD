package com.example.lojacrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;

import static com.example.lojacrud.CadastroActivity.filePath;

public class ImagemDetalhesPopUp extends AppCompatActivity {
    Integer id;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem_detalhes_pop_up);
        Intent intent = getIntent();
        id = (Integer) intent.getSerializableExtra("imagem");
        imageView = findViewById(R.id.imagem_popup_detalhes);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;

        File photo;
        photo = new File((getExternalFilesDir(filePath))+"/"+id);
        if(photo.exists()){
            Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
            //Bitmap photoAux = Bitmap.createScaledBitmap(photoBitmap,photoBitmap.getWidth(),photoBitmap.getWidth(),false);
            //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photoAux);
            //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
            imageView.setImageBitmap(photoBitmap);
        }
        imageView.setAdjustViewBounds(true);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
