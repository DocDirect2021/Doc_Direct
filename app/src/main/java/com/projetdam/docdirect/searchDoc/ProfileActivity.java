package com.projetdam.docdirect.searchDoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.projetdam.docdirect.MainActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.fragments.FragmentAccueil;
import com.projetdam.docdirect.fragments.FragmentCompte;
import com.projetdam.docdirect.fragments.FragmentDocuments;
import com.projetdam.docdirect.fragments.FragmentFilter;
import com.projetdam.docdirect.fragments.FragmentRdvList;
import com.projetdam.docdirect.fragments.FragmentUrgence;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentFilter.NoticeDialogListener {
    /**
     * Variables globales fragment
     **/
    Toolbar toolbar;
    DrawerLayout drawer_layout;

    // La gestion des fragments
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentAccueil fragmentAccueil;

    // Gestion de la NavigationView
    private NavigationView navigationView;

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_navigationView);
    }

    private void addFragmentAccueil() {
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragmentAccueil)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fragmentAccueil = new FragmentAccueil();
        // Appel de la méthode d'initialisation de l'UI
        initUI();
        // Ajout du support pour la gestion de la Toolbar
        setSupportActionBar(toolbar);

        // Ajout de la gestion des options d'accessibilité
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, // L'activité hôte du drawer
                drawer_layout, // Le layout dans l'activité
                toolbar, // La toolbar
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        // Ajout d'un listener sur le bouton hamburger
        drawer_layout.addDrawerListener(toggle);
        // Synchro du bouton hamburger avec le menu
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            addFragmentAccueil();
            // Force l'affichage du 1er fragment au démarrage
            navigationView.setCheckedItem(R.id.nav_fragmentAccueil);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentAccueil)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment targetFragment = fragmentAccueil;
        switch (item.getItemId()) {
            case R.id.nav_fragmentAccueil:
                targetFragment = fragmentAccueil;
                break;
            case R.id.nav_fragmentDocuments:
                targetFragment = new FragmentDocuments();
                break;
            case R.id.nav_fragmentRendezVous:
                targetFragment = new FragmentRdvList();
                break;
            case R.id.nav_fragmentUrgence:
                targetFragment = new FragmentUrgence();
                break;
            case R.id.nav_fragmentCompte:
                targetFragment = new FragmentCompte();
                break;
            case R.id.nav_fragmentLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                break;
        }

        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, targetFragment)
                .commit();

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else if (currentFragment != fragmentAccueil) {
            navigationView.setCheckedItem(R.id.nav_fragmentAccueil);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentAccueil)
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        fragmentAccueil.onDialogPositiveClick(dialog);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        fragmentAccueil.onDialogNegativeClick(dialog);
    }
}
