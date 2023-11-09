package comp3350.ptw.presentation;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.Show;

/**
 * this class is an adapter for the objects that go in the plan to watch list, it extends ListItemAdapter
 */
public class PTWListItemAdapter extends ListItemAdapter {
    private UIShowAccess showAccess;


    public PTWListItemAdapter(Context context, int textViewResourceId, List<Show> objects, UIShowAccess showAccess) {
        super(context, textViewResourceId, objects);
        this.showAccess = showAccess;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent, true);
        Show movie = super.getMovie();
        final ShowSubSet[] subSet = {null};

        getViewHolder().showAdd.setBackgroundResource(R.drawable.ic_playlist_add_black_24dp);
        getViewHolder().ratingBar.setVisibility(View.GONE);

        super.addViewHolderDisplayInfo();

        Spinner dropdown = view.findViewById(R.id.priorityDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, fillList());
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && movie instanceof PrioritizedShow) {
                    PrioritizedShow prioritizedShow = (PrioritizedShow) movie;
                    prioritizedShow.setPriority(position);
                    showAccess.updatePriority(movie);
                    ListUpdate.refreshPTWList();
                    System.out.println(position + " " + movie.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //it is required for .OnItemSelectedListener()
            }
        });

        getViewHolder().showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccess.add(movie, ShowSubSet.WATCHINGSHOWS);
                showAccess.remove(movie, ShowSubSet.PTWSHOWS);
                ListUpdate.refreshWatchingList();
                ListUpdate.refreshPTWList();
            }
        });

        getViewHolder().showDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccess.remove(movie, ShowSubSet.PTWSHOWS);
                ListUpdate.refreshPTWList();
            }
        });

        getViewHolder().showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsClick(movie);
            }
        });

        return view;
    }


    private String[] fillList() {
        ArrayList<Show> shows = new ArrayList<>();
        showAccess.getAll(shows, ShowSubSet.PTWSHOWS);

        String[] list = new String[shows.size() + 1];
        list[0] = "";
        for (int i = 1; i < list.length; i++) {
            list[i] = Integer.toString(i);
        }
        return list;
    }

}
