package application.samoht.br.cardfit.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import application.samoht.br.cardfit.R;

/**
 * Created by Thomas on 03/08/16.
 */
public class BaseFragment extends Fragment {

    ProgressDialog mProgressDialog;
    private Handler closeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                showSimpleAlertDialog(getContext(), getString(R.string.error_timout));
            }
        }
    };

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void showSimpleAlertDialog(Context context, CharSequence message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNeutralButton(getString(R.string.ok),null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showConfirmDialog(Context context, CharSequence message, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.yes), onClickListener);
        builder.setNegativeButton(getString(R.string.no), null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
