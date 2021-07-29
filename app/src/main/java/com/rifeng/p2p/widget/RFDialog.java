package com.rifeng.p2p.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * Created by Kegh on 2018/3/27.
 */

public class RFDialog {

    public static class Builder {
        private Context mContext;
        private Dialog dialog;
        Params params = new Params();
        IDialog iDialog = null;

        public Builder(Context context) {
            mContext = context;
            getIDialog();
        }

        public Builder setTitle(String str) {
            params.titleStr = str;
            return this;
        }


        public Builder setMessage(String message) {
            params.message = message;
            return this;
        }

        public Builder setPositiveButton(String str, View.OnClickListener listener) {
            params.btnPositiveStr = str;
            params.btnPositiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(String str, View.OnClickListener listener) {
            params.btnNegativeStr = str;
            params.btnNegativeListener = listener;
            return this;
        }

        public Builder setContentView(View view) {
            params.contentView = view;
            return this;
        }

        public Builder setCustomView(View view) {
            params.customView = view;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            params.isCancelable = isCancelable;
            return this;
        }

        public IDialog getIDialog() {
            if (iDialog == null) {
                iDialog = new BaseDialog();
            }
            return iDialog;
        }

        public void setLeftTitle(){
          LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) iDialog.getTitleView().getLayoutParams();
          params.gravity= Gravity.LEFT;
        }

        public Dialog create() {
            if (params.customView == null) {
                dialog = iDialog.getDialog((Activity) mContext);
                if (params.btnPositiveStr != null && params.btnNegativeStr != null) {

                }
                if (params.btnPositiveStr != null) {
                    iDialog.getPositiveButton().setVisibility(View.VISIBLE);
                    ((Button) iDialog.getPositiveButton()).setText(params.btnPositiveStr);
                    iDialog.getPositiveButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (params.btnPositiveListener != null) {
                                params.btnPositiveListener.onClick(view);
                            }
                            dialog.dismiss();
                        }
                    });
                }
                if (params.btnNegativeStr != null) {
                    iDialog.getNegativeButton().setVisibility(View.VISIBLE);
                    ((Button) iDialog.getNegativeButton()).setText(params.btnNegativeStr);
                    iDialog.getNegativeButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (params.btnNegativeListener != null) {
                                params.btnNegativeListener.onClick(view);
                            }
                            dialog.dismiss();
                        }
                    });
                }
                if (params.btnPositiveStr != null && params.btnNegativeStr == null) {
                    ViewGroup.LayoutParams lp =  iDialog.getPositiveButton().getLayoutParams();
                    lp.width = (int)(dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.48);
                    iDialog.getPositiveButton().setLayoutParams(lp);
                } else if (params.btnPositiveStr == null && params.btnNegativeStr != null) {
                    ViewGroup.LayoutParams lp =  iDialog.getNegativeButton().getLayoutParams();
                    lp.width = (int)(dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.48);
                    iDialog.getNegativeButton().setLayoutParams(lp);
                } else if (params.btnPositiveStr != null && params.btnNegativeStr != null) {
                    iDialog.getPositiveButton().getLayoutParams().width = (int)(dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.324);
                    iDialog.getNegativeButton().getLayoutParams().width = (int)(dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.324);
                }
                if (params.titleStr != null) {
                    iDialog.getTitleView().setVisibility(View.VISIBLE);
                    iDialog.getTitleView().setText(params.titleStr);
                } else {
                    iDialog.getTitleView().setVisibility(View.GONE);
                }
                iDialog.setCancelable(params.isCancelable);
                if (params.contentView != null) {
                    iDialog.setContentView(params.contentView);
                } else {
                    if (params.message != null) {
                        iDialog.getMessageView().setVisibility(View.VISIBLE);
                        iDialog.getMessageView().setText(params.message);
                    } else {
                        iDialog.getMessageView().setVisibility(View.GONE);
                    }
                }
            } else {
                dialog = iDialog.getDialog((Activity) mContext, params.customView);
            }

            return dialog;
        }

        public Dialog show() {
            final Dialog dialog = create();
            dialog.show();
            return dialog;
        }

        public class Params {
            public String btnPositiveStr;
            public View.OnClickListener btnPositiveListener;
            public String btnNegativeStr;
            public View.OnClickListener btnNegativeListener;
            public String message;
            public String titleStr;
            public View contentView;
            public View customView;
            public boolean isCancelable = true;
        }

    }
}
