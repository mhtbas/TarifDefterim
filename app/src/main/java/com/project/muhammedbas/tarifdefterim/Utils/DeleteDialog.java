package com.project.muhammedbas.tarifdefterim.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.project.muhammedbas.tarifdefterim.R;

public class DeleteDialog extends Dialog {

    public Activity mActivity;
    public Dialog d;
    public Button delete, cancel;

    public DeleteDialog(Activity a) {
        super(a);
        this.mActivity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_deleterecipe);
        delete =findViewById(R.id.deleteButton);
        cancel =findViewById(R.id.cancelButton);
    }

}