package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ChoicesAdapter;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.RequestAnlRequest;

import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.MakeRequestViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.DialogChoicesBinding;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocRequestRecordBinding;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class DocRequestRecordFragment extends Fragment {
    FragmentDocRequestRecordBinding binding;
    NavController navController;
    MakeRequestViewModel makeRequestViewModel;
    CaseDetailsViewModel caseDetailsViewModel;
    ArrayList<String> list;
    DialogChoicesBinding dialogBinding;
    ChoicesAdapter adapter;
    int caseId;
    private static final String TAG = "DocRequestRecordFragmen";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_request_record, container, false);
        //dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_choices, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        DocNameId anlNameId = DocRequestRecordFragmentArgs.fromBundle(getArguments()).getAnlNameId();

        if(DocRequestRecordFragmentArgs.fromBundle(getArguments()).getAnlNameId() != null){
            assert anlNameId != null;
            Log.i(TAG, "onViewCreated: "+ anlNameId.getName());
            binding.etiDRRSelectAnl.setText(anlNameId.getName());
        }
        makeRequestViewModel = new ViewModelProvider(this).get(MakeRequestViewModel.class);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);

        caseDetailsObserver();
        requestObserver();


        list = new ArrayList<>();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvRecordChoices.setLayoutManager(layoutManager);
        adapter = new ChoicesAdapter(list);
        binding.rvRecordChoices.setAdapter(adapter);

        binding.bntDRRAddChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Choose Measurements");
                dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_choices, null, false);
                builder.setView(dialogBinding.getRoot());

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.clear();
                        handleChoices(list);
                        adapter = new ChoicesAdapter(list);
                        binding.rvRecordChoices.setAdapter(adapter);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.etiDRRSelectAnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocRequestRecordFragmentDirections.ActionDocRequestRecordFragmentToDocSelectAnlFragment action =
                        DocRequestRecordFragmentDirections.actionDocRequestRecordFragmentToDocSelectAnlFragment(caseId);
                navController.navigate(action);
            }
        });

        binding.btnDRRSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestAnlRequest request = new RequestAnlRequest(caseId, anlNameId.getId(), binding.etiDRRNote.getText().toString(), list);
                makeRequestViewModel.makeRequest(requireContext(), request);
            }
        });
    }

    public void requestObserver(){
        makeRequestViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                caseDetailsViewModel.getCaseDetails(requireContext(), caseId);
                navController.navigate(R.id.action_docRequestRecordFragment_to_docCaseDFragment);
            }
        });

        makeRequestViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
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
            }
        });

        caseDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleChoices(ArrayList<String> list){
        String choice;
        if (dialogBinding.chipBloodP.isChecked()){
            choice = dialogBinding.chipBloodP.getText().toString();
            list.add(choice);
        }
        if (dialogBinding.chipSugarA.isChecked()){
            choice = dialogBinding.chipSugarA.getText().toString();
            list.add(choice);
        }
        if (dialogBinding.chipTemperature.isChecked()){
            choice = dialogBinding.chipTemperature.getText().toString();
            list.add(choice);
        }
        if (dialogBinding.chipFluidB.isChecked()){
            choice = dialogBinding.chipFluidB.getText().toString();
            list.add(choice);
        }
        if (dialogBinding.chipRespiratoryR.isChecked()){
            choice = dialogBinding.chipRespiratoryR.getText().toString();
            list.add(choice);
        }
        if (dialogBinding.chipHeartR.isChecked()){
            choice = dialogBinding.chipHeartR.getText().toString();
            list.add(choice);
        }
    }
}