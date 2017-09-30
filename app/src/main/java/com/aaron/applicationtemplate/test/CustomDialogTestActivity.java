package com.aaron.applicationtemplate.test;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.aaron.applicationtemplate.R;
import com.customdialoglibrary.CustomDialog;
import com.customdialoglibrary.Effectstype;
import com.customdialoglibrary.test.MyDialogFragment;
import com.customdialoglibrary.ViewHolder;

public class CustomDialogTestActivity extends AppCompatActivity {
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicedialog);
    }

    public void showDialog0(View view) {
        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
                .setLayoutId(R.layout.share_layout)
                .setConvertListener(new CustomDialog.ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final CustomDialog customDialog) {
                        holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(CustomDialogTestActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showDialog1(View view) {
        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
                .setLayoutId(R.layout.friend_set_layout)
                .setConvertListener(new CustomDialog.ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final CustomDialog dialog) {

                    }
                })
                .setShowBottom(true)
                .setHeight(310)
                .show(getSupportFragmentManager());
    }

    public void showDialog2(View view) {
        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
                .setLayoutId(R.layout.commit_layout)
                .setConvertListener(new CustomDialog.ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final CustomDialog dialog) {
                        final EditText editText = holder.getView(R.id.edit_input);
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editText, 0);
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    public void showDialog3(View view) {
        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
                .setLayoutId(R.layout.ad_layout)
                .setConvertListener(new CustomDialog.ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final CustomDialog dialog) {
                        holder.setOnClickListener(R.id.close, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(210)
                .setOutCancel(false)
                .setAnimation(R.style.EnterExitAnimation)
                .show(getSupportFragmentManager());
    }


    public void showDialog4(View view) {
        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
                .setLayoutId(R.layout.loading_layout)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0)
                .show(getSupportFragmentManager());
    }

    public void showDialog5(View view) {
//        CustomDialog.init(CustomDialog.DialogType.CUSTOM_VIEW_TYPE)
//                .setLayoutId(R.layout.confirm_layout)
//                .setDimAmount(0.3f)
//                .setWidth(210)
//                .setOutCancel(false)
//                .setConvertListener(new CustomDialog.ViewConvertListener() {
//                    @Override
//                    public void convertView(com.customdialoglibrary.ViewHolder holder, final CustomDialog customDialog) {
//                        holder.setText(R.id.title, "提示");
//                        holder.setText(R.id.message, "简单提示框");
//                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                customDialog.dismiss();
//                            }
//                        });
//
//                        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                customDialog.dismiss();
//                            }
//                        });
//                    }
//                })
////                .setAnimation(Effectstype.SlideBottom)
//                .show(getSupportFragmentManager());
    }

    public void showDialog6(View view) {
        CustomDialog.init()
                .setDimAmount(0.3f)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .setAnimation(Effectstype.Fall)
                .show(getSupportFragmentManager());
    }

    public void showDialog7(View view) {
        CustomDialog.init()
                .setDimAmount(0.3f)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .setContentText("It's pretty, isn't it?")
                .setAnimation(Effectstype.Fliph)
                .show(getSupportFragmentManager());
    }

    public void showDialog8(View view) {
        CustomDialog.init(CustomDialog.DialogType.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .setDimAmount(0.3f)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutCancel(true)
                .setAnimation(Effectstype.Flipv)
                .show(getSupportFragmentManager());
    }

    public void showDialog9(View view) {
        CustomDialog.init(CustomDialog.DialogType.SUCCESS_TYPE)
                .setDimAmount(0.3f)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .setOutCancel(true)
                .setAnimation(Effectstype.Newspager)
                .show(getSupportFragmentManager());
    }

    public void showDialog10(View view) {
        CustomDialog.init(CustomDialog.DialogType.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setAnimation(Effectstype.RotateBottom)
                .setConfirmClickListener(new CustomDialog.OnDefaultDialogButtonClickListener() {
                    @Override
                    public void onClick(CustomDialog customDialog) {
                        customDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(CustomDialog.DialogType.SUCCESS_TYPE);
                    }
                })
                .show(getSupportFragmentManager());
    }

    public void showDialog11(View view) {
        CustomDialog.init(CustomDialog.DialogType.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel!")
                .setConfirmText("Yes!")
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setAnimation(Effectstype.RotateLeft)
                .setCancelClickListener(new CustomDialog.OnDefaultDialogButtonClickListener() {
                    @Override
                    public void onClick(CustomDialog customDialog) {
                        customDialog.setTitleText("Cancelled!")
                                .setContentText("Your imaginary file is safe :)")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(CustomDialog.DialogType.ERROR_TYPE);
                        // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                    }
                })
                .setConfirmClickListener(new CustomDialog.OnDefaultDialogButtonClickListener() {
                    @Override
                    public void onClick(CustomDialog sDialog) {
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(CustomDialog.DialogType.SUCCESS_TYPE);
                    }
                })
                .show(getSupportFragmentManager());
    }

    public void showDialog12(View view) {
//        CustomDialog.init(CustomDialog.DialogType.CUSTOM_IMAGE_TYPE)
//                .setDimAmount(0.3f)
//                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
//                .setOutCancel(true)
//                .setTitleText("Sweet!")
//                .setContentText("Here's a custom image.")
//                .setCustomImage(R.drawable.ic_launcher)
//                .show(getSupportFragmentManager());

        MyDialogFragment.init()
                .show(getSupportFragmentManager());
    }
    public void showDialog13(View view) {
        final CustomDialog pDialog = CustomDialog.init(CustomDialog.DialogType.PROGRESS_TYPE)
                .setDimAmount(0.3f)
                .setWidth(210)
                .setOutCancel(false)
                .setTitleText("Loading")
                .show(getSupportFragmentManager());
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialog.setProgressColor(R.color.blue_btn_bg_color);
                        break;
                    case 1:
                        pDialog.setProgressColor(R.color.material_deep_teal_50);
                        break;
                    case 2:
                        pDialog.setProgressColor(R.color.success_stroke_color);
                        break;
                    case 3:
                        pDialog.setProgressColor(R.color.material_deep_teal_20);
                        break;
                    case 4:
                        pDialog.setProgressColor(R.color.material_blue_grey_80);
                        break;
                    case 5:
                        pDialog.setProgressColor(R.color.warning_stroke_color);
                        break;
                    case 6:
                        pDialog.setProgressColor(R.color.success_stroke_color);
                        break;
                }
            }
            public void onFinish() {
                i = -1;
                pDialog.setTitleText("Success!")
                        .setConfirmText("OK")
                        .changeAlertType(CustomDialog.DialogType.SUCCESS_TYPE);
            }
        }.start();
    }
}
