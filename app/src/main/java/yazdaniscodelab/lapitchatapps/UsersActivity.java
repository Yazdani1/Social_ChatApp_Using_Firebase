package yazdaniscodelab.lapitchatapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar=findViewById(R.id.alluser_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Users Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserList=findViewById(R.id.recyclerview_allusers);


    }
}
