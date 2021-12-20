package com.projetdam.docdirect.commons;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projetdam.docdirect.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSampleDatasToFirebase extends Application { //extend application pour le context

    /**
     * Les variables à changer pour une utilisation future
     **/

    // Le nom du package
//    private static final String packName = "com.example.firebasegestionrecyclerview";

    // Les clés pour l'association des colonnes dans la base de données **/
    private static final String KEY_TITRE = "titre";
    private static final String KEY_ANNEE = "annee";
    private static final String KEY_ACTEURS = "acteurs";
    private static final String KEY_AFFICHE = "affiche";
    private static final String KEY_SYNOPSIS = "synopsis";

    // Les varibles lièes aux emplacements de stockage de Firebase
    private static final String collection = "products";
    private static final String imageFolder = "productsImages";

    /**
     * Variables Globales des clés de bases
     **/
    private static final String TAG = "ADD DATAS";
    private static final String filePrefs = R.class.getPackage().getName() + ".prefs";

    private static String urlStorageAffiche;

    public static CollectionReference productsRef = FirebaseFirestore.getInstance().collection(collection);
    public static StorageReference storageRef = FirebaseStorage.getInstance().getReference(imageFolder);

    /**
     * Méthode pour parser les données du fichier texte puis les envoyer vers FireBase
     **/

    public static ArrayList<Uri> addDatasToFireBase(Context context) {

        ArrayList<Uri> uris = new ArrayList<Uri>();

        int[] imageToUpload = {

                R.drawable.generated_photos_5e685ad56d3b380006e7a37f,
                R.drawable.generated_photos_5e6805ff6d3b380006d4c485,
                R.drawable.generated_photos_5e6849c86d3b380006e3cb11,
                R.drawable.generated_photos_5e6863f86d3b380006e9b1d5,
                R.drawable.generated_photos_5e6882f56d3b380006f0c197,
                R.drawable.generated_photos_5e68416e6d3b380006e1e68b,
                R.drawable.generated_photos_5e68884d6d3b380006f1f9eb,
                R.drawable.generated_photos_5e6884746d3b380006f11835,
                R.drawable.generated_photos_5e6883996d3b380006f0e6cb,
                R.drawable.generated_photos_5e68893e6d3b380006f23027,
                R.drawable.generated_photos_5e6886546d3b380006f1860f

        };

        //storageRef.delete();
        for (int j : imageToUpload) {

            // Gestion des images
            String uriToParse = "android.resource://" + R.class.getPackage().getName() + "/" + j;
            Uri imageUri = Uri.parse(uriToParse);
            Log.i(TAG, "addDatasToFireBase: " + imageUri);
            uris.add(imageUri);
        }
        return uris;
    }
    // Sinon on affiche les erreurs

}
