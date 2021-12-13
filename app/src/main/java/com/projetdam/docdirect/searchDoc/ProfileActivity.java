package com.projetdam.docdirect.searchDoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.projetdam.docdirect.MainActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.fragments.FragmentCompte;
import com.projetdam.docdirect.fragments.FragmentDocuments;
import com.projetdam.docdirect.fragments.FragmentRendezVous;
import com.projetdam.docdirect.fragments.FragmentUrgence;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FilterFragment.NoticeDialogListener {

    /**
     * Variables globales fragment
     **/
    Toolbar toolbar;
    DrawerLayout drawer_layout;

    // La gestion des fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentAccueil fa;
    // Gestion de la NavigationView
    private NavigationView navigationView;

    // Variable emplacement
    private static final String emplacement
            = MainActivity.class.getSimpleName();



    public void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_navigationView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fa=new FragmentAccueil();
        // Appel de la méthode d'initialisation de l'UI
        initUI();
        // Ajout du support pour la gestio nde la Toolbar
        setSupportActionBar(toolbar);

        // Ajout de la gestion des options d'accessibilité
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, // Le context de l'activité
                drawer_layout, // Le layout du MainActivity
                toolbar, // La toolbar
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        // Ajout d'un listener sur le bouton hamburger
        drawer_layout.addDrawerListener(toggle);
        // Synchro le bouton hamburger et le menu
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            addFragment();
            // Force l'affichage du 1er fragment au démarrage
            navigationView.setCheckedItem(R.id.nav_fragmentAccueil);
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,fa).
                    commit();

        }
    }// create

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_fragmentAccueil:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,fa).
                        commit();
                break;
            case R.id.nav_fragmentDocuments:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new FragmentDocuments()).
                        commit();
                break;
            case R.id.nav_fragmentRendezVous:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new FragmentRendezVous()).
                        commit();
                break;
            case R.id.nav_fragmentUrgence:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new FragmentUrgence()).
                        commit();
                break;
            case R.id.nav_fragmentCompte:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container, new FragmentCompte()).
                        commit();
                break;
            case R.id.nav_fragmentLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
//                getSupportFragmentManager().
//                        beginTransaction().
//                        replace(R.id.fragment_container, new FragmentDeconnexion()).
//                        commit();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void addFragment() {
        fragmentManager = getSupportFragmentManager();
        // Commencer la discussion
        fragmentTransaction = fragmentManager.beginTransaction();
        // Appel du nouveau fragment
        FragmentAccueil fragmentAccueil = new FragmentAccueil();
        // Ajouter au container de fragment
        fragmentTransaction.add(R.id.fragment_container, fragmentAccueil);
        // Finalisation de la création du fragment
        fragmentTransaction.commit();

//        getSupportFragmentManager().
//                beginTransaction().
//                add(R.id.fragment_container, new FragmentAccueil()).
//                commit();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        fa.onDialogPositiveClick(dialog);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        fa.onDialogNegativeClick(dialog);
    }
}