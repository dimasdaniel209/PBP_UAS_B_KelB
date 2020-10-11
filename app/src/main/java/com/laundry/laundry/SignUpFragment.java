package com.laundry.laundry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private FirebaseAuth.AuthStateListener authStateListener;
    Button signUpBtn;
    EditText edtNama, edtEmail, edtPassword;
    FirebaseAuth firebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpBtn = root.findViewById(R.id.btnSignUp);

        edtNama = root.findViewById(R.id.signUp_nama);
        edtEmail = root.findViewById(R.id.signUp_email);
        edtPassword = root.findViewById(R.id.signUp_password);
        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEmail.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Email Invalid",Toast.LENGTH_SHORT).show();
                }else if(edtPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }else if(!isValidEmailId(edtEmail.getText().toString().trim())){
                    Toast.makeText(getActivity().getApplicationContext(), "Email Invalid", Toast.LENGTH_SHORT).show();
                }else if(edtPassword.getText().toString().length()<6){
                    Toast.makeText(getActivity().getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(getActivity().getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), "Authentication Successful", Toast.LENGTH_SHORT).show();
                                edtEmail.setText("");
                                edtPassword.setText("");
                            }
                        }
                    });
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.signUp_layout, new SignInFragment()).addToBackStack(null).commit();
                }
            }
        });
        return root;
    }

    private boolean isValidEmailId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}