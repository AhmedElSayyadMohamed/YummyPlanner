package com.example.yummyplanner.data.auth.social;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.yummyplanner.R;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.security.MessageDigest;
import java.util.UUID;

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

        // Generate a nonce for security and to help resolve BAD_AUTHENTICATION issues
        String rawNonce = UUID.randomUUID().toString();
        String nonce = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(rawNonce.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            nonce = sb.toString();
        } catch (Exception e) {
            Log.e("LOGIN", "Error creating nonce", e);
        }

        GetGoogleIdOption googleIdOption =
                new GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(activity.getString(R.string.default_web_client_id))
                        .setAutoSelectEnabled(false)
                        .setNonce(nonce) // Adding nonce
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
                    Log.d("LOGIN", "Success: Received Google ID Token");
                    callback.onSuccess(googleIdTokenCredential.getIdToken());
                } catch (Exception e) {
                    Log.e("LOGIN", "Error parsing Google credentials", e);
                    callback.onError("Invalid Google credentials");
                }
            } else {
                Log.e("LOGIN", "Unexpected custom credential type: " + customCredential.getType());
                callback.onError("Unexpected credential type");
            }
        } else {
            Log.e("LOGIN", "Unexpected credential type: " + credential.getClass().getName());
            callback.onError("Unexpected credential type");
        }
    }
}
