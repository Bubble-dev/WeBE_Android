package com.example.dongkyoo.webe.createGroup;

import android.Manifest;
import android.app.Activity;
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

    private static final int REQUEST_CAPTURING_PICTURE = 1;
    private static final int REQUEST_GET_IMAGE_IN_GALLERY = 2;
    private static final int REQUEST_CAPTURING_PICTURE_PERMISSION = 1;

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

        setTitleImageWithGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGalleryPermission();
            }
        });
        return view;
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                Log.d(getClass().getName(), "카메라 권한 요청 다신 보이지 않기");
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURING_PICTURE_PERMISSION);
        } else {
            sendTakePhotoIntent();
        }
    }

    private void requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d(getClass().getName(), "갤러리 권한 요청 다신 보지않기");
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GET_IMAGE_IN_GALLERY);
        } else {
            sendGetImageInGalleryIntent();
        }
    }

    private void sendTakePhotoIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAPTURING_PICTURE);
        }
    }

    private void sendGetImageInGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_GET_IMAGE_IN_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0)
            return;

        switch (requestCode) {
            case REQUEST_CAPTURING_PICTURE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendTakePhotoIntent();
                } else {
                    Log.i(getClass().getName(), "Camera is not Granted");
                }
                break;

            case REQUEST_GET_IMAGE_IN_GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendGetImageInGalleryIntent();
                } else {
                    Log.i(getClass().getName(), "Gallery is not Granted");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURING_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extra = data.getExtras();
                    Bitmap bitmap = (Bitmap) extra.get("data");
                    titleImageView.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_GET_IMAGE_IN_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    titleImageView.setImageURI(data.getData());
                }
                break;
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
        inflater.inflate(R.menu.menu_create_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_group_save:
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
