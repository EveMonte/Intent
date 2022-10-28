package by.bstu.fit.savelev.busyday;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.bstu.fit.savelev.busyday.databinding.FragmentFirstBinding;
import by.bstu.fit.savelev.busyday.databinding.FragmentSecondBinding;
import by.bstu.fit.savelev.busyday.databinding.FragmentViewInfoBinding;
import by.bstu.fit.savelev.busyday.models.Item;

public class ViewInfo extends Fragment {

    private FragmentViewInfoBinding binding;
    private Item receivedItem;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Bundle bundle = this.getArguments();
        binding = FragmentViewInfoBinding.inflate(inflater, container, false);

        if (bundle != null) {
            receivedItem = bundle.getParcelable("CurrentItem"); // Key
            binding.activityCategory.setText(receivedItem.getActivityCategory().getValue());
            binding.activityDuration.setText(Integer.toString(receivedItem.getDurationInMinutes()));
            binding.activityName.setText(receivedItem.getActivityName());
            binding.activityDescription.setText(receivedItem.getActivityDescription());
            try {
                //binding.activityImage.setImageURI(Uri.parse(receivedItem.getPhoto()));
            }
            catch (Exception ex){

            }

        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}