package application.samoht.br.cardfit.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import application.samoht.br.cardfit.R;


/**
 * Created by Thomas on 6/26/16.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private Handler closeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                showSimpleAlertDialog(getString(R.string.error_timout));
            }
        }
    };

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
        closeHandler.sendEmptyMessageDelayed(0, 15000);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

    protected void showToast(String message){
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }

    protected void showSimpleAlertDialog(CharSequence message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setNeutralButton(getString(R.string.ok),null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showConfirmDialog(String string, DialogInterface.OnClickListener onClickListenerOk){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.message_confirm_logout))
                .setPositiveButton(getString(R.string.yes), onClickListenerOk)
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }})
                .create()
                .show();
    }
}
