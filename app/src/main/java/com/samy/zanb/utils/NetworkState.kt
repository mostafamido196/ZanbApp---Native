package com.samy.zanb.utils

import android.content.Context
import android.widget.Toast


sealed class NetworkState {

    //idle
    object Idle : NetworkState()

    //loading
    object Loading : NetworkState()

    //result
    data class Result<T>(var response: T) : NetworkState()

    //error
    data class Error(var errorCode: Int, var msg: String? = null) : NetworkState() {

//        fun handleErrors(
//            @ApplicationContext
//            mContext: Context,
//            mDialogsListener: DialogsListener? = null
//        ) {
//
//            Log.e(TAG, "handleErrors: msg $msg")
//            Log.e(TAG, "handleErrors: error code $errorCode")
//
//            when (errorCode) {
//
//                Constants.Codes.EXCEPTIONS_CODE -> {
//                    showHelperDialog(
//                        msg = if (msg.isNullOrEmpty()) mContext.getString(R.string.internet_connection) else msg!!,
//                        mContext = mContext,
//                        mDialogsListener = mDialogsListener
//                    )
//                }
//                Constants.Codes.API_KEY_CODE -> {
//                    showHelperDialog(
//                        msg = if (msg.isNullOrEmpty()) mContext.getString(R.string.x_api_key_error) else msg!!,
//                        mContext = mContext,
//                        mDialogsListener = {
//                            BaseViewModel.signOut(mContext)
//                            BaseViewModel.startAuth(mContext)
//                        }
//                    )
//                }
//                Constants.Codes.AUTH_CODE -> {
//                    showHelperDialog(
//                        msg = mContext.getString(R.string.auth_error),
//                        mContext = mContext,
//                        mDialogsListener = {
//                            BaseViewModel.signOut(mContext)
//                            BaseViewModel.startAuth(mContext)
//                        }
//                    )
//                }
//                else -> {
//                    showHelperDialog(
//                        msg = if (msg.isNullOrEmpty()) mContext.getString(R.string.known_error) else msg!!,
//                        mContext = mContext,
//                        mDialogsListener = mDialogsListener
//                    )
//                }
//            }
//
//        }

        private fun showHelperDialog(
            msg: String,
            mContext: Context,
            mDialogsListener: DialogsListener?
        ) {

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
            mDialogsListener?.onDismiss()
        }

        companion object {
            private val TAG = this::class.java.name
        }

    }

}
