package com.example.hospitalsystem_abdelrahmantarek.Doctor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ItemClickListener;
import com.example.hospitalsystem_abdelrahmantarek.Models.Cases.CaseData;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees.AddEmpViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Cases.CaseDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees.SelectEmpViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocSelectNurseBinding;

import java.util.ArrayList;


public class DocSelectNurseFragment extends Fragment {

    FragmentDocSelectNurseBinding binding;
    NavController navController;
    SelectEmpViewModel selectEmpViewModel;
    AddEmpViewModel addEmpViewModel;
    CaseDetailsViewModel caseDetailsViewModel;
    ItemClickListener itemClickListener;
    DocListAdaptor adaptor;
    int nurseId;
    int caseId;
    private static final String TAG = "DocSelectNurseFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_select_nurse, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        navController = Navigation.findNavController(view);
        selectEmpViewModel = new ViewModelProvider(this).get(SelectEmpViewModel.class);
        addEmpViewModel = new ViewModelProvider(this).get(AddEmpViewModel.class);
        caseDetailsViewModel = new ViewModelProvider(requireActivity()).get(CaseDetailsViewModel.class);

        caseId = DocSelectNurseFragmentArgs.fromBundle(getArguments()).getCaseId();

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String name, int id) {
                // Notify adapter
                binding.rvDSNNurses.post(new Runnable() {
                    @Override
                    public void run() {
                        adaptor.notifyDataSetChanged();
                    }
                });
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                nurseId = id;
            }
        };

        selectEmpViewModel.getTypeEmp(requireContext(), "nurse");
        typeEmpObserver();
        addEmpObserver();


        binding.etiDSNSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptor.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.docSNBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmpViewModel.addEmp(caseId, nurseId, requireContext());
            }
        });
    }

    public void typeEmpObserver(){
        selectEmpViewModel.getListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<DNAData>>() {
            @Override
            public void onChanged(ArrayList<DNAData> data) {
                adaptor = new DocListAdaptor(data, itemClickListener);
                binding.rvDSNNurses.setAdapter(adaptor);
            }
        });
        selectEmpViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addEmpObserver(){
        addEmpViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                caseDetailsViewModel.getCaseDetails(requireContext(), caseId);
                caseDetailsObserver();
            }
        });

        addEmpViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
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
                navController.navigate(R.id.action_docSelectNurseFragment_to_docCaseDFragment);
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