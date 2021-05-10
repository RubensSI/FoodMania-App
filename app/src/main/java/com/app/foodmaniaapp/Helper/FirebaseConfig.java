package com.app.foodmaniaapp.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by rubenspinto
 */

public class FirebaseConfig {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth referenceAutentication;
    private static StorageReference referenceStorage;

    public static String getUserId() {
        FirebaseAuth autentication = getFirefebaseAutentication();
        return autentication.getCurrentUser().getUid();
    }

    // retorna a referência do database
    public static DatabaseReference getReferenceFirebase() {
        if (referenceFirebase == null) {
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFirebase;
    }

    // retorna a instância do FirebaseAuth
    public static FirebaseAuth getFirefebaseAutentication() {
        if (referenceAutentication == null) {
            referenceAutentication = FirebaseAuth.getInstance();
        }
        return referenceAutentication;
    }

    public static StorageReference getFirebaseStorage() {
        if (referenceStorage == null) {
            referenceStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenceStorage;
    }
}
