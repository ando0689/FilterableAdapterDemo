package test.andranik.filterableadapter.filterable_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by andranikh on 5/9/17.
 */

public class FilterableBaseViewHolder<T> extends RecyclerView.ViewHolder {
    // Define elements of a row here
    public FilterableBaseViewHolder(View itemView) {
        super(itemView);
        // Find view by ID and initialize here
    }

    public void bind(T item) {
        // bindView() method to implement actions
    }
}
