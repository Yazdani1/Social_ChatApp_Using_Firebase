package yazdaniscodelab.lapitchatapps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout displayName;
    private TextInputLayout emailId;
    private TextInputLayout password;

    private Button creataccountbtn;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar=(Toolbar)findViewById(R.id.toolbar_Reg_id);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Registration");

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog=new ProgressDialog(this);


        mAuth=FirebaseAuth.getInstance();


        displayName=(TextInputLayout)findViewById(R.id.displayname);
        emailId=(TextInputLayout)findViewById(R.id.emailid);
        password=(TextInputLayout)findViewById(R.id.password);
        creataccountbtn=(Button)findViewById(R.id.accntbtn);


        creataccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mdisplayName=displayName.getEditText().getText().toString();
                String mEmail=emailId.getEditText().getText().toString();
                String mpass=password.getEditText().getText().toString();

                if (!TextUtils.isEmpty(mdisplayName) || !TextUtils.isEmpty(mEmail) || !TextUtils.isEmpty(mpass)){

                    progressDialog.setTitle("Please wait.");
                    progressDialog.setMessage("Please wait while reg will complete..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    registration_User(mdisplayName,mEmail,mpass);

                }

            }
        });

    }


    private void registration_User(final String displayname, String email, String pass){

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    FirebaseUser mUser=FirebaseAuth.getInstance().getCurrentUser();

                    String uid=mUser.getUid();

                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String,String>userMap=new HashMap<>();

                    userMap.put("name",displayname);
                    userMap.put("status","Hi i am using chat application");
                    userMap.put("default","default");
                    userMap.put("thump_image","default");

                    mDatabase.setValue(userMap);




                    progressDialog.dismiss();
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }else {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                }

            }
        });


    }



}
