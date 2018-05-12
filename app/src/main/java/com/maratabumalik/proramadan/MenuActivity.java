package com.maratabumalik.proramadan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.maratabumalik.ramadancheatsheet.BuildConfig;
import com.maratabumalik.ramadancheatsheet.R;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.ThemeDark);
        }
        else{
            setTheme(R.style.ThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    private boolean isActivityStarted(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i=0; i<menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            String title = mi.getTitle().toString();
            Spannable newTitle = new SpannableString(title);
            newTitle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorText)), 0, newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(newTitle);
        }
        MenuItem nightModeItem = optionsMenu.findItem(R.id.night_mode);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            nightModeItem.setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        // Операции для выбранного пункта меню
        switch (id) {

            case R.id.donate_settings:
                intent = new Intent(getApplication().getApplicationContext(), BillingActivity.class);
                startActivity(intent);
                return true;

            case R.id.rate_app:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                rateIntent.setData(Uri.parse("market://details?id=com.maratabumalik.proramadan"));
                if (!isActivityStarted(rateIntent)) {
                    rateIntent.setData(Uri.parse(getString(R.string.play_market_url)));
                    if (!isActivityStarted(rateIntent)) {
                        Toast.makeText(
                                this, getString(R.string.no_play_market), Toast.LENGTH_SHORT).show();
                    }
                }
                startActivity(rateIntent);
                return true;

            case R.id.share_settings:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + "\n\n" + getString(R.string.play_market_url));
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                return true;

            case R.id.feedback_settings:
                Intent feedbackIntent = new Intent(Intent.ACTION_VIEW);
                Uri feedbackData = Uri.parse("mailto:MaratAbuMalik@gmail.com?subject=" +
                        "Обратная связь по Ramadan" +
                        "&body=" + "Ramadan version: " + BuildConfig.VERSION_NAME + "\n" +
                        "OS version: " + Build.VERSION.SDK_INT + "\n" +
                        "Device: " + Build.BRAND + " " + Build.MODEL + "\n" +
                        "Locale: " + Locale.getDefault() + "\n\n\n");
                feedbackIntent.setData(feedbackData);
                startActivity(feedbackIntent);
                return true;

            case R.id.other_apps:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.developer_url))));
                return true;

            case R.id.night_mode:
                item.setChecked(!item.isChecked());
                if(item.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                restartApp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void restartApp(){
        Intent i = getIntent();
        finish();
        startActivity(i);
    }
}
