package sg.edu.np.week7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private String TAG = "WeatherApp";
    private String FILENAME = "Main2Activity.java";

    public String GLOBAL_PREFS = "MyPrefs"; // name of my shared preferences file
    public String MY_USERNAME = "MyUserName"; // Key in Shared preferences file for username
    public String MY_PASSWORD = "MyPassword"; // Key in shared preferences file for password

    private Button createButton;
    private Button cancelButton;
    //SharedPreferences sharedPreferences;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText etUsername = findViewById(R.id.editText_CreateUser);
        EditText etPassword = findViewById(R.id.editText_CreatePassword);
        createButton = findViewById(R.id.button2);
        cancelButton = findViewById(R.id.button3);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               /* sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MY_USERNAME, etUsername.getText().toString());
                editor.putString(MY_PASSWORD, etPassword.getText().toString());
                editor.apply();
                */
                UserData userData = dbHandler.findUser(etUsername.getText().toString());

                if(userData == null)
                {
                    UserData dbUserData = new UserData();
                    dbUserData.setUsername(etUsername.getText().toString());
                    dbUserData.setPassword(etPassword.getText().toString());
                    dbHandler.addUser(dbUserData);
                    Toast.makeText(MainActivity2.this, "New User Created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity2.this, "User Already Exist!", Toast.LENGTH_SHORT).show();
                }

                //Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                //startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }

    protected void onStop(){
        super.onStop();
        finish();
    }
}