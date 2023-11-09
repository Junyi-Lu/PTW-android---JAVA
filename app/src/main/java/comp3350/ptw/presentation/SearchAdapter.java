package comp3350.ptw.presentation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.Show;

/**
 * this class is an adapter for the objects that go in the search list, it extends ListItemAdapter
 */
public class SearchAdapter extends ListItemAdapter {
    private UIShowAccess UIShowAccess = new UIShowAccess();
    private int resourceId;
    private PrioritizedShow prioritizedShow;
    private Map<Integer, View> map = new HashMap<Integer, View>();
    private SearchActivity searchActivity;

    public SearchAdapter(Context context, int textViewResourceId, List<Show> objects, SearchActivity searchActivity) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.searchActivity = searchActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Show movie = super.setMovie(position);
        ViewHolder viewHolderLocal;
        final ShowSubSet[] subSet = {ShowSubSet.PTWSHOWS};

        if (map.get(position) == null) {
            viewHolderLocal = super.createViewHolder();
            super.fillViewHolder(parent);
            viewHolderLocal.showDelete.setVisibility(View.GONE);
            viewHolderLocal.ratingBar.setVisibility(View.GONE);
            map.put(position, getViewObject());

        } else {
            super.setView(map.get(position));
            super.setViewHolder((ViewHolder) getViewObject().getTag());
            viewHolderLocal = super.getViewHolder();
        }

        super.addViewHolderDisplayInfo();

        getViewHolder().showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsClick(movie);
            }
        });

        getViewHolder().showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(searchActivity, UIShowAccess.add(movie, ShowSubSet.PTWSHOWS), Toast.LENGTH_SHORT).show();
                ListUpdate.refreshPTWList();
            }
        });


        return getViewObject();
    }
}