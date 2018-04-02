package com.example.murodjonrahimov.wecare.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;
import com.example.murodjonrahimov.wecare.CameraPopUpActivity;
import com.example.murodjonrahimov.wecare.PatientPostForm;
import com.example.murodjonrahimov.wecare.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraPopUpFragment extends Fragment {

  private View root;
  public static final int CAMERA_REQUEST_CODE = 1;
  private static final int PATIENT_POST = 2;
  private ImageButton imageButtonGallery;
  private ImageButton imageButtonCamera;
  private ProgressDialog progressDialog;
  CameraPopUpFragment.UriSender uriSender;


  public CameraPopUpFragment() {
    // Required empty public constructor
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try{
      uriSender =(CameraPopUpFragment.UriSender) context;
      }catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must implement listener");
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    root = inflater.inflate(R.layout.fragment_camera_pop_up, container, false);


    imageButtonCamera = root.findViewById(R.id.image_button_camera);
    imageButtonGallery = root.findViewById(R.id.image_button_gallery);

    //DisplayMetrics dm = new DisplayMetrics();
    //root.getWindowManager().getDefaultDisplay()
    //  .getMetrics(dm);

    //progressDialog = new ProgressDialog(this);

    //int width = dm.widthPixels;
    //int height = dm.heightPixels;

    //getWindow().setLayout((int) (width * .6), (int) (height * .3));

    imageButtonCamera.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
      }
    });

    imageButtonGallery.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PATIENT_POST);
      }
    });



    return root;
  }

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
  ///super.onActivityResult(requestCode, resultCode, data);

  if (requestCode == PATIENT_POST && resultCode == RESULT_OK) {

  //progressDialog.setMessage("Uploading image...");
  //progressDialog.show();
  Uri uri = data.getData();
  uriSender.sendURI(uri);

  //Intent intent1 = new Intent(getContext(), PatientPostForm.class);
  //intent1.putExtra("uriPatientPost", uri.toString());
  //progressDialog.dismiss();
  //Toast.makeText(getContext(), "Uploading finished", Toast.LENGTH_SHORT).show();
  //startActivity(intent1);
  }

  else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

  //progressDialog.setMessage("Uploading Image...");
  //progressDialog.show();
  Uri uri = data.getData();
  uriSender.sendURI(uri);

  //Intent intent1 = new Intent(getContext(), PatientPostForm.class);
  //intent1.putExtra("uriPatientPost", uri.toString());
  //progressDialog.dismiss();
  //Toast.makeText(getContext(), "Uploading finished", Toast.LENGTH_SHORT).show();
  //startActivity(intent1);
  }
  }

  public interface UriSender{
    void sendURI(Uri uri);
  }
}
