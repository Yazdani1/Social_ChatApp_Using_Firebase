package yazdaniscodelab.lapitchatapps;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Toolbar toolbar;

    private ViewPager mpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar_xml);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lapit Chat apps");

        mAuth=FirebaseAuth.getInstance();

        mpager=findViewById(R.id.view_pager_xml);

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser==null){
            settomain();
        }

    }

    public void settomain(){

        startActivity(new Intent(MainActivity.this,StartActivity.class));
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         switch (item.getItemId()){
             case R.id.logout_id:
                 FirebaseAuth.getInstance().signOut();
                 settomain();
                 break;

             case R.id.alluser_id:

                 break;

             case R.id.setting_id:

                 break;
         }

         return true;
    }







}
