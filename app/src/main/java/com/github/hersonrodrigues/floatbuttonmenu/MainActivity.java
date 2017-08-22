package com.github.hersonrodrigues.floatbuttonmenu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatButtonMenu menu = (FloatButtonMenu) findViewById(R.id.fbm);

        menu.setIconMenuClosed(android.R.drawable.ic_menu_add);
        menu.setIconMenuOpened(android.R.drawable.ic_menu_close_clear_cancel);
        menu.setColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAccent)));

        FloatButtonMenu.MenuItem menu1 = new FloatButtonMenu.MenuItem(
                ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                getString(R.string.title_example_1),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), getString(R.string.title_example_1), Toast.LENGTH_LONG).show();
                    }
                }
        );

        FloatButtonMenu.MenuItem menu2 = new FloatButtonMenu.MenuItem(
                ContextCompat.getDrawable(this, R.mipmap.ic_launcher),
                getString(R.string.title_example_2),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), getString(R.string.title_example_2), Toast.LENGTH_LONG).show();
                    }
                }
        );

        menu.addMenuItem(menu1);
        menu.addMenuItem(menu2);
    }
}
