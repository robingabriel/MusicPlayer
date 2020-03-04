package uph.android.final_project_music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private TextView btnTxtLgn;
    public EditText name;
    public EditText pass;
    public EditText repass;
    public EditText email;
    private Global global = new Global();
    private Button regis;
    private View regis_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnTxtLgn = findViewById(R.id.regis_login_text);
        name = findViewById(R.id.regis_usr_edit);
        pass = findViewById(R.id.regis_pass_edit);
        repass = findViewById(R.id.regis_repass_edit);
        email = findViewById(R.id.regis_email_edit);
        regis = findViewById(R.id.regis_regis_btn);
        regis_view = findViewById(R.id.regis_view);

        btnTxtLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                Register.this.startActivity(intent);
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = name.getText().toString().trim();
                String up = pass.getText().toString().trim();
                String ue = email.getText().toString().trim();

                if(un.length() < 8) {
                    Snackbar.make(regis_view, "Usernama / Password is to short", Snackbar.LENGTH_SHORT).show();
                }else if(up.length() < 8) {
                    Snackbar.make(regis_view, "Usernama / Password is to short", Snackbar.LENGTH_SHORT).show();
                }else if(!up.equals(repass.getText().toString().trim())) {
                    Snackbar.make(regis_view, "Retype password is incorrect", Snackbar.LENGTH_SHORT).show();
                }else if(!isEmailValid(ue)){
                    Snackbar.make(regis_view, "Email is incorrect", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    global.userArray.add(new UserModel(un,up,ue, false));
                    Intent intent = new Intent(Register.this, Login.class);
                    Register.this.startActivity(intent);
                }
            }
        });
    }


    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
