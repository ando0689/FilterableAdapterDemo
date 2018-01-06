package test.andranik.filterableadapter.filterable_adapter;

import java.util.List;

/**
 * Created by andranikh on 4/25/17.
 */

public interface FilteringStrategy<T extends FilterableModel> {
    List<T> performFiltering(CharSequence charSequence, List<T> originalList, List<T> filteredList);
}
