package uph.android.final_project_music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {

    public EditText usrId;
    public EditText usrPass;

    private Button btnLogin;
    private TextView btnTxtRegis;
    private Global global = new Global();
    private View login_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = findViewById(R.id.login_login_btn);
        btnTxtRegis = findViewById(R.id.login_regis_text);
        usrId = findViewById(R.id.login_usr_edit);
        usrPass = findViewById(R.id.login_pass_edit);
        login_view = findViewById(R.id.login_view);

        global.userArray.add(new UserModel("a","a","a",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(global.userArray.size() != 0) {
                    for (UserModel x: global.userArray) {
                        if(usrId.getText().toString().trim().equals(x.getUsername()) && usrPass.getText().toString().trim().equals(x.getPass())) {
                            x.setLogin(true);
                            Intent intent = new Intent(Login.this, Homepage_Activity.class);
                            Login.this.startActivity(intent);
                            Login.this.finish();
                        }else{
                            Snackbar.make(login_view, "user/pass is wrong", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnTxtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Login.this, Register.class);
            Login.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
