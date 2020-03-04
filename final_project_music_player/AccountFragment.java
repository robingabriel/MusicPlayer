package uph.android.final_project_music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public class AccountFragment extends Fragment {

    private TextView name;
    private Global global = new Global();
    private Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.acc_text_name);
        logout = view.findViewById(R.id.logout);

        for (UserModel x : global.userArray ) {
            if(x.getLogin() == true) {
                name.setText(x.getUsername());
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (UserModel x : global.userArray ) {
                    if(x.getUsername().equals( name.getText().toString() )) {
                        x.setLogin(false);
                    }
                }
                Intent intent = new Intent(getActivity(), Login.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
