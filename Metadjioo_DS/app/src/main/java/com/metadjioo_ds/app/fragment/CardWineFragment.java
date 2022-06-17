package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.used.LogInActivity;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.entity.IsWineVideo;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.Video;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;
import com.metadjioo_ds.utils.ImgSaver;

public class CardWineFragment extends Fragment {
    private final Wine wine; //Atm Wine has no attributes other than its id but it could have one in the future
    private final WineCuvee wineCuvee;
    private final WineDatas wineDatas;
    private final WineCuveeDatas wineCuveeDatas;
    private final Video video;

    public CardWineFragment(int id_wine_cuvee, int id_video) {
        super(R.layout.experience_wine_card);
        AppDatabase apd = AppDatabase.getInstance(getContext());
        wineCuvee = apd.wineCuveeDAO().get(id_wine_cuvee);
        wine = apd.wineDAO().get(wineCuvee.id_wine);
        wineDatas = apd.wineDatasDAO().get(wineCuvee.id_wine);
        wineCuveeDatas = apd.wineCuveeDatasDAO().get(id_wine_cuvee);
        video = apd.videoDAO().get(id_video);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton btnPlay = view.findViewById(R.id.start_video_wine);
        ImageButton btnInfo = view.findViewById(R.id.wine_datasheet);
        ImageView wineImg = view.findViewById(R.id.wine_img);
        Bitmap bmp = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
        wineImg.setImageBitmap(bmp);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Play "+video.title_video, Toast.LENGTH_SHORT).show();
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), wineDatas.name+" infos", Toast.LENGTH_SHORT).show();
            }
        });
        TextView wine_title = view.findViewById(R.id.wine_title);
        wine_title.setText(wineCuveeDatas.name);
    }
}
