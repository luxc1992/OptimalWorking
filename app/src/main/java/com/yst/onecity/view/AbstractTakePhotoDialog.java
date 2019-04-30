package com.yst.onecity.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.yst.onecity.R;



/**
 * 拍照弹出框
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */

public abstract class AbstractTakePhotoDialog {
    protected Dialog dialog;
    private TextView cancle;
    private TextView takephoto;
    private TextView takepicter;
    public AbstractTakePhotoDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_takephoto, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view);
        takephoto = view.findViewById(R.id.txt_takephoto);
        takepicter=view.findViewById(R.id.txt_picter);
        cancle =view.findViewById(R.id.cancel);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (lp != null) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            //设置dialog 在布局中的位置
            lp.gravity = Gravity.BOTTOM;

            dialogWindow.setAttributes(lp);
        }
        takephoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takePhoto();
            }
        });
        takepicter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                takePicture();

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        return dialog;
    }

    /**
     * 拍照
     */
    public abstract void takePhoto();

    /**
     * 选择相册
     */
    public abstract void takePicture();

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}
