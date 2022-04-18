package com.mxbqr.app.bottomnavigation;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.mxbqr.app.R;
import com.google.zxing.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class FQrscanner extends Fragment {
    private CodeScanner mCodeScanner;
    private CodeScannerView mCodeScannerView;
    TextView tv;
    public FQrscanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_qrscanner, container, false);

        //tv=view.findViewById(R.id.textView13);
        //tv.setText("KAREKOD TARAYICI");

        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_f_qrscanner, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);


        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //Kameraya erişim izni kontrolü
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            Toast.makeText(getContext(), "İzin kabul edildi.", Toast.LENGTH_LONG).show();
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
