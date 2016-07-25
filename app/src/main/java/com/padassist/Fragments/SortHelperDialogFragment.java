package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import com.padassist.R;

public class SortHelperDialogFragment extends DialogFragment {

    public interface SortBy {
        public void sortElement();

        public void sortType();

        public void sortStats();

        public void sortPlus();

        public void sortRarity();
    }

    private Button element, type, stats, plus, rarity;
    private SortBy sortBy;

    public static SortHelperDialogFragment newInstance(SortBy sortBy) {
        SortHelperDialogFragment dialogFragment = new SortHelperDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_leader_dialog, null);
        element = (Button) rootView.findViewById(R.id.element);
        type = (Button) rootView.findViewById(R.id.type);
        stats = (Button) rootView.findViewById(R.id.stats);
        plus = (Button) rootView.findViewById(R.id.plus);
        rarity = (Button) rootView.findViewById(R.id.rarity);
        builder.setTitle("Sort by Helper attributes");
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        element.setOnClickListener(elementOnClickListener);
        type.setOnClickListener(typeOnClickListener);
        stats.setOnClickListener(statsOnClickListener);
        plus.setOnClickListener(plusOnClickListener);
        rarity.setOnClickListener(rarityOnClickListener);
    }

    private Button.OnClickListener elementOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            sortBy.sortElement();
        }
    };

    private Button.OnClickListener typeOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            sortBy.sortType();
        }
    };

    private Button.OnClickListener statsOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            sortBy.sortStats();
        }
    };

    private Button.OnClickListener plusOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            sortBy.sortPlus();
        }
    };

    private Button.OnClickListener rarityOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            sortBy.sortRarity();
        }
    };

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }
}
