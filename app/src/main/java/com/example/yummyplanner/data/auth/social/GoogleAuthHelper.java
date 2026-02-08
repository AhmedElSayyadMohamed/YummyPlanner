package com.example.yummyplanner.data.auth.social;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.credentials.CredentialManager;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.yummyplanner.R;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import androidx.credentials.GetCredentialRequest;
import java.util.concurrent.Executors;
import androidx.credentials.CredentialManagerCallback;
import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CustomCredential;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

public class GoogleAuthHelper {

    private final Activity activity;
    private final CredentialManager credentialManager;
    private final SocialAuthCallback callback;

    public GoogleAuthHelper(Activity activity, SocialAuthCallback callback) {
        this.activity = activity;
        this.callback = callback;
        this.credentialManager = CredentialManager.create(activity);
    }

    public void launchSignIn() {

        Log.d("LOGIN", "launchSignIn started");

        GetGoogleIdOption googleIdOption =
                new GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(
                                activity.getString(R.string.default_web_client_id)
                        )
                        .setAutoSelectEnabled(false)
                        .build();

        GetCredentialRequest request =
                new GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build();

        credentialManager.getCredentialAsync(
                activity,
                request,
                null,
                activity.getMainExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {
                        Log.d("LOGIN", "Google Sign-In SUCCESS");
                        handleSignInResult(result);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e("LOGIN", "Google Sign-In ERROR", e);
                        callback.onError(e.getMessage());
                    }
                }
        );
    }

    private void handleSignInResult(GetCredentialResponse result) {
        Credential credential = result.getCredential();
        if (credential instanceof CustomCredential) {
            CustomCredential customCredential = (CustomCredential) credential;
            if (customCredential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                try {
                    GoogleIdTokenCredential googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(customCredential.getData());
                    Log.d("LOGIN", " Success We are and get googl token");

                    callback.onSuccess(googleIdTokenCredential.getIdToken());
                } catch (Exception e) {
                    Log.d("LOGIN", " ERROR We are and can't get googl token");

                    callback.onError("Invalid Google credentials");
                }
            } else {
                Log.d("LOGIN", " ERROR We are and can't get googl token");

                callback.onError("Unexpected credential type");
            }
        } else {
            Log.d("LOGIN", " ERROR We are and can't get googl token");

            callback.onError("Unexpected credential type");
        }
    }
}