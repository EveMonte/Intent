package by.bstu.fit.savelev.busyday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class DialogF extends DialogFragment implements DialogInterface.OnClickListener{


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Удалить элемент?")
                .setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.no, this);
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                i = R.string.yes;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if(fragments != null){
                    for(Fragment fragment : fragments){
                        if(fragment != null && fragment.isVisible())
                            if(fragment instanceof FirstFragment){
                                ((FirstFragment)fragment).delete();
                            }
                    }
                }

                getDialog().dismiss();
                break;
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                getDialog().dismiss();
                break;

        }
    }
}
