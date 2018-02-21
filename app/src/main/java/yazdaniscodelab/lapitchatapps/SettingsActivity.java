package yazdaniscodelab.lapitchatapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUsersDatabase;
    private FirebaseUser mUser;

    //layout connection

    private CircleImageView mDisplayImage;
    private TextView mDisplayname;
    private TextView mStatus;

    private Button mImagebutton;
    private Button mStatusButton;

    private ProgressDialog mDialog;

    private static final int GALARY_PICK = 1;

    private StorageReference mImageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = findViewById(R.id.circule_imageview);
        mDisplayname = findViewById(R.id.display_name);
        mStatus = findViewById(R.id.status);
        mImagebutton = findViewById(R.id.change_img);
        mStatusButton = findViewById(R.id.change_status);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = mUser.getUid();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);


        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mname=dataSnapshot.child("name").getValue().toString();
                String images=dataSnapshot.child("image").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String thumbimage=dataSnapshot.child("thump_image").getValue().toString();

                mDisplayname.setText(mname);
                mStatus.setText(status);

                Picasso.with(SettingsActivity.this).load(images).into(mDisplayImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StatusActivity.class);

                String mStatusintent = mStatus.getText().toString();
                intent.putExtra("status_key", mStatusintent);
                startActivity(intent);
            }
        });

        mImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallaryIntent=new Intent();
                gallaryIntent.setType("image/*");
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallaryIntent,"SELECT IMAGE"),GALARY_PICK);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALARY_PICK && resultCode==RESULT_OK){


            Uri image_uri=data.getData();
            CropImage.activity(image_uri)
                    .setAspectRatio(1,1)
                    .start(this);

//            String image_uri=data.getDataString();
//            Toast.makeText(getApplicationContext(),image_uri,Toast.LENGTH_SHORT).show();

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mDialog=new ProgressDialog(SettingsActivity.this);
                mDialog.setTitle("Please wait");
                mDialog.setMessage("This will complete in a few moments..");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();



                Uri resultUri = result.getUri();

                String usrid=mUser.getUid();


                StorageReference filepath=mImageStorage.child("profile_Images").child(usrid+".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){

                            String download_uri=task.getResult().getDownloadUrl().toString();

                            mUsersDatabase.child("image").setValue(download_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        mDialog.dismiss();

                                        Toast.makeText(getApplicationContext(),"Upload successfully",Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });


                        }else {
                            Toast.makeText(getApplicationContext(),"Errow",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}