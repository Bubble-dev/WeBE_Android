package com.example.dongkyoo.webe.createGroup;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dongkyoo.webe.R;
import com.example.dongkyoo.webe.vos.Group;

public class CreateGroupFragment extends Fragment {

    private TextInputEditText nameEditText, descriptionEditText;
    private ImageButton setTitleImageWithCameraButton, setTitleImageWithGalleryButton;
    private ImageView titleImageView;

    private CreateGroupViewModel viewModel;
    private OnCreateGroupFragmentHandler handler;

    private static final int REQUEST_CAPTURING_PICTURE_CODE = 1;

    public static CreateGroupFragment newInstance() {
        return new CreateGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        nameEditText = view.findViewById(R.id.fragment_create_group_name);
        descriptionEditText = view.findViewById(R.id.fragment_create_group_description);
        titleImageView = view.findViewById(R.id.fragment_create_group_titleImage);
        setTitleImageWithCameraButton = view.findViewById(R.id.fragment_create_group_setTitleImageWithCamera);
        setTitleImageWithGalleryButton = view.findViewById(R.id.fragment_create_group_setTitleImageWithGallery);

        setTitleImageWithCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });
        return view;
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                Log.i("??", "??");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURING_PICTURE_CODE);
            }
        } else {
            Intent cameraIntent = new Intent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAPTURING_PICTURE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURING_PICTURE_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            titleImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() != null && getActivity() instanceof OnCreateGroupFragmentHandler) {
            handler = (OnCreateGroupFragmentHandler) getActivity();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_group_save:
                // TODO: Success 신호는 바꿔야함
                if (nameEditText.getText().toString().equals(""))
                    nameEditText.setError(getActivity().getResources().getString(R.string.require_group_name));

                // 그룹저장 서버 요청
                Group newGroup = viewModel.saveNewGroup();
                if (newGroup != null) handler.onCreateNewGroup(newGroup);
                else Snackbar.make(getView(), R.string.error, BaseTransientBottomBar.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
    }

    public interface OnCreateGroupFragmentHandler {
        void onCreateNewGroup(Group group);
    }
}
