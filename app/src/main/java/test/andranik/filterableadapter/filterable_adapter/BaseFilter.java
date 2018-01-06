package test.andranik.filterableadapter.filterable_adapter;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by andranik on 4/25/17.
 */

public class BaseFilter<T extends FilterableModel, A extends FilterableAdapterInterface<T>> extends Filter {

    private A adapter;
    private List<T> originalList;
    private List<T> filteredList;

    private FilteringStrategy<T> filteringStrategy;

    protected BaseFilter(A adapter, List<T> originalList, FilteringStrategy<T> filteringStrategy) {
        super();
        this.adapter = adapter;
        this.originalList = new LinkedList<>(originalList);
        this.filteredList = new ArrayList<>();
        this.filteringStrategy = filteringStrategy;
    }

    public void setFilteringStrategy(FilteringStrategy<T> filteringStrategy) {
        this.filteringStrategy = filteringStrategy;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        final FilterResults results = new FilterResults();
        filteredList = filteringStrategy.performFiltering(charSequence, originalList, filteredList);
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.publishSearchResults((List<T>) filterResults.values);
    }

}
