package com.example.hospitalsystem_abdelrahmantarek.Reports;

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

import com.example.hospitalsystem_abdelrahmantarek.Adaptors.ManagerReplyAdaptor;
import com.example.hospitalsystem_abdelrahmantarek.Models.Reports.ReportDetailsData;
import com.example.hospitalsystem_abdelrahmantarek.R;
import com.example.hospitalsystem_abdelrahmantarek.ViewModels.Reports.ReportDetailsViewModel;
import com.example.hospitalsystem_abdelrahmantarek.databinding.FragmentReportDetailsBinding;

import java.util.ArrayList;

public class ReportDetailsFragment extends Fragment {
    FragmentReportDetailsBinding binding;
    NavController navController;
    ReportDetailsViewModel reportDetailsViewModel;
    ManagerReplyAdaptor adaptor;
    ArrayList<ReportDetailsData> reportReplies;
    private static final String TAG = "ReportDetailsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        reportDetailsViewModel = new ViewModelProvider(this).get(ReportDetailsViewModel.class);
        reportReplies = new ArrayList<>();

        int id = ReportDetailsFragmentArgs.fromBundle(getArguments()).getReportId();
        reportDetailsViewModel.showReport(id, binding.getRoot().getContext());
        reportObserver();

        binding.btnRDEndReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportDetailsViewModel.deleteReport(id);
            }
        });

        binding.btnRDSendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etlRDManagerReply.setErrorEnabled(false);
                if(reportDetailsViewModel.getEmployeeModel().getType().toLowerCase().equals("manger")){
                    int verify =0;
                    if(binding.etiRDManagerReply.getText().toString().isEmpty()){
                        Toast.makeText(requireContext(), "Type your reply", Toast.LENGTH_SHORT).show();
                        verify+=1;
                    }
                    if(verify==0){
                        reportDetailsViewModel.sendReply(id, binding.etiRDManagerReply.getText().toString());
                    }
                }
                else
                    Toast.makeText(requireContext(), "You can't reply to your own report", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reportObserver(){
        reportDetailsViewModel.getReportDetailsMLiveData().observe(getViewLifecycleOwner(), new Observer<ReportDetailsData>() {
            @Override
            public void onChanged(ReportDetailsData reportDetailsData) {
                binding.tvRDReportName.setText(reportDetailsData.getReportName());
                handleEmpPp(reportDetailsData.getUser().getSpecialist().toLowerCase());
                String empFullName = reportDetailsData.getUser().getFirstName()+" "+reportDetailsData.getUser().getLastName();
                String specialist = "Specialist, "+reportDetailsData.getUser().getSpecialist();
                binding.tvRDEmpName.setText(empFullName);
                binding.tvRDEmpType.setText(specialist);
                binding.tvRDReportDate.setText(reportDetailsData.getCreatedAt());
                binding.tvRDReportDescription.setText(reportDetailsData.getDescription());
                Log.i(TAG, "onChanged: manager id = "+ reportDetailsData.getManger().getId());
                if(!reportDetailsData.getManger().getId().equals("")){
                    reportReplies.add(reportDetailsData);
                    adaptor = new ManagerReplyAdaptor(reportReplies);
                    binding.rvRDReplies.setAdapter(adaptor);
                }
            }
        });

        reportDetailsViewModel.getErrorMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        reportDetailsViewModel.getSuccessMLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                navController.navigate(R.id.action_reportDetailsFragment_to_reportsListsFragment);

            }
        });
    }

    public void handleEmpPp(String empType){
        switch (empType) {
            case "doctor":
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_doctor);
                break;
            case "nurse":
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_nurse);
                break;
            case "receptionist":
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_receptionist);
                break;
            case "analysis":
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_analysis);
                break;
            case "hr":
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_hr);
                break;
            default:
                binding.ivRDEmpPp.setImageResource(R.drawable.iv_dpp_manager);
                break;
        }
    }
}