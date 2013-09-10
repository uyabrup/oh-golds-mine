package vn.luis.goldsmine.util;

import vn.luis.goldsmine.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {
	/* AlertBuider 
	 * Use
	 * Function.messageAlert(this, "title", "message");
         
        Function.confirmationAlert(this, "title", "message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something important, user confirmed the alert
            }
        });
         
        Function.decisionAlert(this, "title", "message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something because user clicked the positixve button
            }
        });
	 * 
	 * */
	
	private static final int MESSAGE_ALERT = 1;
    private static final int CONFIRM_ALERT = 2;
    private static final int DECISION_ALERT = 3;
    private static String TITLE_POS = null;
    
    public static void messageAlert(Context context, String title, String message, int icon) {
        showAlertDialog(MESSAGE_ALERT, context, title, message, null, icon, context.getResources().getString(R.string.close));
    }
 
    public static void confirmationAlert(Context context, String title, String message, DialogInterface.OnClickListener callBack, int icon) {
        showAlertDialog(CONFIRM_ALERT, context, title, message, callBack, icon, context.getResources().getString(R.string.ok));
    }
 
    public static void decisionAlert(Context context, String title, String message, DialogInterface.OnClickListener posCallback,  int icon, String... buttonNames) {
        showAlertDialog(DECISION_ALERT, context, title, message, posCallback, icon, buttonNames);
    }
 
    public static void showAlertDialog(int alertType, Context context, String title, String message, 
    		DialogInterface.OnClickListener posCallback, int icon, String... buttonNames) {
    	
        if ( title == null ) title = context.getResources().getString(R.string.app_name);
        if ( message == null ) message = "";
        if ( icon == 0 ) icon = android.R.drawable.ic_dialog_alert;
 
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
 
                // false = pressing back button won't dismiss this alert
                .setCancelable(true)
 
                // icon on the left of title
                .setIcon(icon);
 
        switch (alertType) {
            case MESSAGE_ALERT:
            	TITLE_POS = context.getResources().getString(R.string.close);
                break;
            case CONFIRM_ALERT:
            	TITLE_POS = context.getResources().getString(R.string.cancel);
                builder.setPositiveButton(buttonNames[0], posCallback);
                break;
            case DECISION_ALERT:
            	TITLE_POS = context.getResources().getString(R.string.cancel);
                break;
        }
 
        builder.setNegativeButton(TITLE_POS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
  	
}
