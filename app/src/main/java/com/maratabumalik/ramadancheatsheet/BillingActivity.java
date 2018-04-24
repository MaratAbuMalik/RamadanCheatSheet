package com.maratabumalik.ramadancheatsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;

public class BillingActivity extends MenuActivity {
    // Экземпляр класса для работы с магазином
    IabHelper mHelper;

    // Код для обратного вызова
    static final int REQUEST_CODE = 505;

    static int billSums[] = {49, 99, 199, 399};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArNycbnBYT+xbcSf4eFfD5iLXjTgoUESqr/NR+HgoqVdYy/IqPHwvucFJBpJPelIlEMk2HiyC4aeoOxmrRYg3kb6eksJyX8xQuUmLz5Vvq8+RUcnm7P9Q+aX8O1Hnmw0hohj5KNQeUQK5tSagqtS3mQaid41LyVYJb7uTTSqnd3rYzmnlmUjgduB64Ycln/d6XlZ7i4wMlnzjHcIPjF7Z4gA5u5Xask5QMYFMoDrk4x8e6mdKbpuQfP2HURtiyhplLzfl6H7v36hf2YwQ5JXErj30x1wz4/XBwDZ512TF6CxpCNLYJbLvhY8Z+YaDIijUTMRTcy3lrMHc8RO3RSjR2QIDAQAB";
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(false);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }
            }
        });

        ListView billSumsListView = (ListView) findViewById(R.id.billSums);

        String billSumsText[] = new String[billSums.length];

        for(int i = 0; i <billSums.length; i++){
            billSumsText[i] = Integer.toString(billSums[i]) + " руб";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_billing_sum, billSumsText);

        billSumsListView.setAdapter(adapter);

        billSumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Индефикатор товара
                final String SKU_DONATE = "donate_" + Integer.toString(billSums[position]) + "_rubbles";
                IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            // Обработка произошедшей ошибки покупки
                            return;
                        }
                        if (purchase.getSku().equals(SKU_DONATE)) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    R.string.thanks, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };
                mHelper.launchPurchaseFlow(BillingActivity.this, SKU_DONATE, REQUEST_CODE, mPurchaseFinishedListener);
            }
        });
    }

    @Override
    protected void onDestroy (){
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
