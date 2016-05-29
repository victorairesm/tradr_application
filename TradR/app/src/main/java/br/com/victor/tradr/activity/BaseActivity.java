package br.com.victor.tradr.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.victor.tradr.R;
import br.com.victor.tradr.activity.prefs.ConfiguracoesActivivity;
import br.com.victor.tradr.activity.prefs.ConfiguracoesV11Activivity;
import br.com.victor.tradr.fragments.AjudaFragment;
import br.com.victor.tradr.fragments.FeedFragment;
import br.com.victor.tradr.fragments.PerfilFragment;
import br.com.victor.tradr.fragments.ProdutosFragment;
import br.com.victor.tradr.fragments.TrocasFragment;
import victor.lib.utils.AndroidUtils;

public class BaseActivity extends victor.lib.activity.BaseActivity {
    protected DrawerLayout drawerLayout;

    // Configura a Toolbar
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    // Configura o Nav Drawer
    protected void setupNavDrawer() {
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Ícone do menu do nav drawer
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null && drawerLayout != null) {
                // Atualiza os dados do header do Navigation View
                setNavViewValues(navigationView, R.string.nav_drawer_username,
                        R.string.nav_drawer_email, R.mipmap.ic_logo_user);
                //NavDrawerUtil.setHeaderValues(navigationView, R.id.containerNavDrawerListViewHeader, R.drawable.nav_drawer_header, R.id.img, R.id.tNome, R.id.tEmail);

                // Trata o evento de clique no menu.
                navigationView.setNavigationItemSelectedListener(
                        new NavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(MenuItem menuItem) {
                                // Seleciona a linha
                                menuItem.setChecked(true);
                                // Fecha o menu
                                drawerLayout.closeDrawers();
                                // Trata o evento do menu
                                onNavDrawerItemSelected(menuItem);
                                return true;
                            }
                        });
            }
        }
    }

    // Atualiza os dados do header do Navigation View
    public static void setNavViewValues(NavigationView navView, int nome, int email, int img) {
        View headerView = navView.getHeaderView(0);
        //NavDrawerUtil.setHeaderValues(navView, R.id.containerNavDrawerListViewHeader, R.drawable.nav_drawer_header, img, nome, email);
        TextView tNome = (TextView) headerView.findViewById(R.id.tUserName);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tUserEmail);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.imgUserPhoto);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }

    // Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_feed:
                replaceFragment(new FeedFragment());
                break;
            case R.id.nav_item_produtos:
                replaceFragment(new ProdutosFragment());
                break;
            case R.id.nav_item_trocas:
                replaceFragment(new TrocasFragment());
                break;
            case R.id.nav_item_perfil:
                replaceFragment(new PerfilFragment());
                break;
            case R.id.nav_item_ajuda:
                replaceFragment(new AjudaFragment());
                break;
            case R.id.nav_item_settings:
                if (AndroidUtils.isAndroid3Honeycomb()) {
                    startActivity(new Intent(this, ConfiguracoesV11Activivity.class));
                } else {
                    startActivity(new Intent(this, ConfiguracoesActivivity.class));
                }

                break;
        }
    }


    // Adiciona o fragment no centro da tela
    protected void replaceFragment(Fragment frag) {
        //toast("Trocou de fragmento");
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, frag, "TAG").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trata o clique no botão que abre o menu.
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
