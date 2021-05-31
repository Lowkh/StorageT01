package sg.edu.np.week7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String TAG ="WeatherApp";
    private String FILENAME = "MainActivity.java";

    public String GLOBAL_PREFS = "MyPrefs"; // name of my shared preferences file
    public String MY_USERNAME = "MyUserName"; // Key in Shared preferences file for username
    public String MY_PASSWORD = "MyPassword"; // Key in shared preferences file for password

    private TextView newUser;
    private Button loginButton;

    //SharedPreferences sharedPreferences;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUser = findViewById(R.id.textView_NewUser);
        newUser.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return false;
            }
        });

        loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                EditText etUsername = findViewById(R.id.editTest_UserName);
                EditText etPassword = findViewById(R.id.editText_Password);

                if(isValidCredentials(etUsername.getText().toString(), etPassword.getText().toString()))
                {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class );
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Valid Acct", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid Username/ Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /* This function does some form of validation */
    public boolean isValidCredentials(String username, String password){
        /*sharedPreferences = getSharedPreferences(GLOBAL_PREFS , MODE_PRIVATE);
        String sharedUsername = sharedPreferences.getString(MY_USERNAME, "");
        String sharedPassword = sharedPreferences.getString(MY_PASSWORD, "");

        if(sharedUsername.equals(username) && sharedPassword.equals(password)){
            return true;
        }*/

        UserData dbData = dbHandler.findUser(username);
        if (dbData != null) {
            if (dbData.getUsername().equals(username) && dbData.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}