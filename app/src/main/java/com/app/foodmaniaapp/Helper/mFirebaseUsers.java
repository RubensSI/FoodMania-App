package com.app.foodmaniaapp.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class mFirebaseUsers {

    public static String getUserId() {
        FirebaseAuth autentication = FirebaseConfig.getFirefebaseAutentication();
        return autentication.getCurrentUser().getUid();
    }

    public static FirebaseUser getCurrentUser() {
        FirebaseAuth user = FirebaseConfig.getFirefebaseAutentication();
        return user.getCurrentUser();
    }

    public static boolean atualizarTipoUsuario(String tipo) {

        try {
            FirebaseUser user = getCurrentUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(tipo)
                    .build();
            user.updateProfile(profile);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
