package test.andranik.filterableadapter.filterable_adapter;

import android.widget.Filterable;

import java.util.List;

/**
 * Created by andranik on 4/25/17.
 */

public interface FilterableAdapterInterface<T> extends Filterable{
    void publishSearchResults(List<T> searchResults);
}
