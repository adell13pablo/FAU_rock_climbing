package com.example.pablo.fau_rock_climbing;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Pablo on 4/4/2018.
 */

public class PaymentDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstance){
        //Using Builder class for a convenient dialog alert construction in the activity it is called
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Set title of the dialog
        builder.setMessage("Membership type");

        //Set the two buttons to show

        builder.setPositiveButton("Monthly Membership", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("payment", "monthly");
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Daily Membership", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("payment", "daily");
                startActivity(intent);
            }
        });

        return builder.create();

    }
}
