package yazdaniscodelab.lapitchatapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import yazdaniscodelab.lapitchatapps.Model.Users;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView mUserList;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar=findViewById(R.id.alluser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Users Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        mUserList=findViewById(R.id.recyclerview_allusers);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users,UserViewHolder>firebasaddpter=new FirebaseRecyclerAdapter<Users, UserViewHolder>
                (
                        Users.class,
                        R.layout.user_list,
                        UserViewHolder.class,
                        mUserDatabase

                ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, Users model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());


            }
        };

        mUserList.setAdapter(firebasaddpter);

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name){
            TextView mUserName=mView.findViewById(R.id.alluser_displayname);
            mUserName.setText(name);
        }

        public void setStatus(String status){

            TextView mStatus=mView.findViewById(R.id.defaultstatus_alluser);
            mStatus.setText(status);
        }

    }


}
