package yazdaniscodelab.lapitchatapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar=findViewById(R.id.alluser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Users Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserList=findViewById(R.id.recyclerview_allusers);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));

    }


    public class UserViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }


    }



}
