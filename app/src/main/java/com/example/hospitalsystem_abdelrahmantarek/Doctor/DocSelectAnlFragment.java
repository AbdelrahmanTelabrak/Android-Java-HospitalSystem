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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.DocListAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ItemClickListener;
import com.example.hospitalsystem_abdelrahmantarek.Models.DocNameId;
import com.example.hospitalsystem_abdelrahmantarek.Models.Employees.DNAData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Employees.SelectEmpViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentDocSelectAnlBinding;

import java.util.ArrayList;

public class DocSelectAnlFragment extends Fragment {
    FragmentDocSelectAnlBinding binding;
    NavController navController;
    SelectEmpViewModel selectEmpViewModel;
    ItemClickListener itemClickListener;
    DocListAdaptor adaptor;
    DocNameId anlNameId;
    int caseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doc_select_anl, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        selectEmpViewModel = new ViewModelProvider(this).get(SelectEmpViewModel.class);

        caseId = DocSelectAnlFragmentArgs.fromBundle(getArguments()).getCaseId();

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String name, int id) {
                // Notify adapter
                binding.rvDSAAnalysis.post(new Runnable() {
                    @Override
                    public void run() {
                        adaptor.notifyDataSetChanged();
                    }
                });
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                anlNameId = new DocNameId(name, id);
            }
        };
        selectEmpViewModel.getTypeEmp(requireContext(), "analysis");
        typeEmpObserver();

        binding.etiDSASearch.addTextChangedListener(new TextWatcher() {
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

        binding.docSABtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocSelectAnlFragmentDirections.ActionDocSelectAnlFragmentToDocRequestRecordFragment action =
                        DocSelectAnlFragmentDirections.actionDocSelectAnlFragmentToDocRequestRecordFragment().setAnlNameId(anlNameId);
                navController.navigate(action);
            }
        });
    }

    public void typeEmpObserver(){
        selectEmpViewModel.getListMLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<DNAData>>() {
            @Override
            public void onChanged(ArrayList<DNAData> data) {
                adaptor = new DocListAdaptor(data, itemClickListener);
                binding.rvDSAAnalysis.setAdapter(adaptor);
            }
        });
        selectEmpViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}