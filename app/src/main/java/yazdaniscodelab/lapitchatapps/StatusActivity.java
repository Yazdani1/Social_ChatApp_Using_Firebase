package yazdaniscodelab.lapitchatapps;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private TextInputLayout mStatus;
    private Button btn;

    private DatabaseReference mDatabasereference;
    private FirebaseUser mUser;
    private ProgressDialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        toolbar=findViewById(R.id.status_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Status Updae");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mStatus=findViewById(R.id.sttus_edit);
        btn=findViewById(R.id.status_update_id);

        String status_value=getIntent().getStringExtra("status_key");

        mStatus.getEditText().setText(status_value);


        mUser= FirebaseAuth.getInstance().getCurrentUser();

        String current_uid=mUser.getUid();

        mDatabasereference= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mdialog=new ProgressDialog(StatusActivity.this);
                mdialog.setTitle("Updating");
                mdialog.setMessage("Please wait status is updating");
                mdialog.show();

                String status=mStatus.getEditText().getText().toString();
                mDatabasereference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            mdialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"There is some Problem",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });



    }
}
