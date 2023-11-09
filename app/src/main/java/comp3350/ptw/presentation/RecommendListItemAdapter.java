package comp3350.ptw.presentation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.Show;

/**
 * this class is an adapter for the objects that go in the suggested list, it extends ListItemAdapter
 */
public class RecommendListItemAdapter extends ListItemAdapter {

    private UIShowAccess showAccess;


    public RecommendListItemAdapter(Context context, int textViewResourceId, List<Show> objects, UIShowAccess showAccess) {
        super(context, textViewResourceId, objects);
        this.showAccess = showAccess;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Show movie = super.getMovie();

        getViewHolder().showAdd.setVisibility(View.GONE);
        getViewHolder().ratingBar.setVisibility(View.GONE);
        getViewHolder().showDelete.setVisibility(View.GONE);
        super.addViewHolderDisplayInfo();

        getViewHolder().showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsClick(movie);
            }
        });

        return view;
    }


}