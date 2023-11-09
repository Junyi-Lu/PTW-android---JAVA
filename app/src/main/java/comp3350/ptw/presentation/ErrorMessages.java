package comp3350.ptw.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import comp3350.ptw.R;


/**
 * ErrorMessages - class for different possible errors
 */
public class ErrorMessages {
    public static void fatalError(final Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.fatalError));
        alertDialog.setMessage(message);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        alertDialog.show();
    }

    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.warning));
        alertDialog.setMessage(message);

        alertDialog.show();
    }
}
