package com.example.hospitalsystem_abdelrahmantarek.AnalysisEmp;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.AddRecordRequest;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.AddRecordViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentAnalysisAddMedRBinding;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AnalysisAddMedRFragment extends Fragment {
    FragmentAnalysisAddMedRBinding binding;
    NavController navController;
    CaseDetailsViewModel caseDetailsViewModel;
    AddRecordViewModel addRecordViewModel;
    String imagePath, note;
    int caseId;
    private static final String TAG = "AnalysisAddMedRFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_analysis_add_med_r, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        addRecordViewModel = new ViewModelProvider(this).get(AddRecordViewModel.class);
        addRecordObserver();

        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);
        caseDetailsObserver();

        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri uri = result.getData().getData();
                        binding.ivAAMUploadedImage.setImageURI(uri);
                        imagePath = uri.getPath();
                        Log.i(TAG, "onViewCreated: uri = "+ uri.getPath());
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });

        binding.btnAAMUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(requireActivity())
                        .crop()
                        .maxResultSize(512,512,false)
                        .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                        .createIntentFromDialog((Function1)(new Function1(){
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcher.launch(it);
                            }
                        }));
            }
        });

        binding.btnAAMAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File image = new File(imagePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
                MultipartBody.Part parts = MultipartBody.Part.createFormData("image", image.getName(), requestBody);
                AddRecordRequest request = new AddRecordRequest(caseId, note, "pending");
                addRecordViewModel.addRecord(requireContext(), request, parts);
            }
        });
    }

    public void addRecordObserver(){
        addRecordViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                caseDetailsViewModel.getCaseDetails(requireContext(), caseId);
                navController.navigate(R.id.action_analysisAddMedRFragment_to_analysisCDFragment);
            }
        });

        addRecordViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void caseDetailsObserver(){
        caseDetailsViewModel.getCaseMLiveData().observe(getViewLifecycleOwner(), new Observer<CaseData>() {
            @Override
            public void onChanged(CaseData caseData) {
                caseId = caseData.getId();
                note = caseData.getMedicalRecordNote();
                binding.tvAAMDocName.setText(caseData.getDoctorId());
                binding.tvAAMNote.setText(caseData.getMedicalRecordNote());
            }
        });

        caseDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}