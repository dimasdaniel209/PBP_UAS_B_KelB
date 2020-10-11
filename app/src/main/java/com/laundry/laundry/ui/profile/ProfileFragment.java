package com.laundry.laundry.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.laundry.laundry.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private  static final String TAG = "ProfileFragment";

    CircleImageView profileImageView;
    private Button btnEditProfile;
    TextView displayNameText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = root.findViewById(R.id.profileImageView);
        displayNameText = root.findViewById(R.id.displayNameEditText);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(TAG, "onCreate" + user.getDisplayName());
            if(user.getDisplayName() != null){
                displayNameText.setText(user.getDisplayName());
            }
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profileImageView);
            }
        }

        btnEditProfile = root.findViewById(R.id.goUpdateProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(),EditProfileActivity.class);
                startActivity(i);
            }
        });

        return root;
    }
}