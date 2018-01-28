package yazdaniscodelab.lapitchatapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextInputLayout emil;
    private TextInputLayout pass;
    private Button btnlogin;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar=(Toolbar)findViewById(R.id.toolbar_login_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log in ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        emil=(TextInputLayout)findViewById(R.id.emailid_login_xml);
        pass=(TextInputLayout)findViewById(R.id.password_login_xml);
        btnlogin=findViewById(R.id.loginbtn);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String memail = emil.getEditText().getText().toString();
                String mpass = pass.getEditText().getText().toString();

                progressDialog.setTitle("Please wait..");
                progressDialog.setMessage("Please wait while login....");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if (!TextUtils.isEmpty(memail) || !TextUtils.isEmpty(mpass)) {
                    login(memail, mpass);
                }

            }


        });


    }

    private void login(String myemail, String mypass) {

        mAuth.signInWithEmailAndPassword(myemail,mypass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }else {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(),"Login fail..",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
