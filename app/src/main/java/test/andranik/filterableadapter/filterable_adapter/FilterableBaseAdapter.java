package test.andranik.filterableadapter.filterable_adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import java.util.List;

import test.andranik.filterableadapter.R;
import test.andranik.filterableadapter.utils.ExceptionTracker;


/**
 * Created by andranik on 4/25/17.
 */

public abstract class FilterableBaseAdapter<M extends FilterableModel, VH extends FilterableBaseViewHolder<M>>
        extends RecyclerView.Adapter<VH> implements FilterableAdapterInterface<M> {

    protected static final int FOOTER_VIEW = 1;

    public interface FilteringCompleteListener {
        void onFilteringComplete(int resultsCount);
    }

    protected List<M> items;
    protected List<M> filteredItems;

    private BaseAdapterFilter filter;

    protected FilteringCompleteListener filteringCompleteListener;

    private FilteringStrategy<M> filteringStrategy = new LocalFilteringStrategy<>();

    private View footerLoadingView;

    @Override
    public void publishSearchResults(List<M> searchResults) {
        filteredItems.clear();
        filteredItems.addAll(searchResults);
        notifyDataSetChanged();

        if (filteringCompleteListener != null) {
            filteringCompleteListener.onFilteringComplete(searchResults.size());
        }
    }

    public void clearAndSetItems(List<M> items) {
        this.items.clear();
        filteredItems.clear();
        setItems(items);
    }

    public void cleanItems() {
        items.clear();
        filteredItems.clear();
        notifyDataSetChanged();
    }

    public void setItems(List<M> items) {
        this.items.addAll(items);
        filteredItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (filteredItems == null) return 0;
        if (filteredItems.size() == 0) return 1;
        return filteredItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == filteredItems.size()) {
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(getFooterLayoutRes(), parent, false);
            return (VH) new FooterViewHolder(itemView);
        }

        itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutRes(), parent, false);

        return getItemViewHolder(itemView);
    }

    protected abstract VH getItemViewHolder(View itemView);

    protected abstract
    @LayoutRes
    int getItemLayoutRes();

    protected abstract
    @LayoutRes
    int getFooterLayoutRes();

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position) != FOOTER_VIEW) {
            holder.bind(filteredItems.get(position));
        }
    }

    public List<M> getFilteredItems() {
        return filteredItems;
    }

    public void notifyItemChanged(M item) {
        try {
            int index = filteredItems.indexOf(item);
            notifyItemChanged(index);
        } catch (Exception e) {
            ExceptionTracker.trackException(e);
            notifyDataSetChanged();
        }
    }

    public void updateItem(M item) {
        try {
            int filteredIndex = filteredItems.indexOf(item);
            filteredItems.set(filteredIndex, item);

            int index = items.indexOf(item);
            items.set(index, item);

            notifyItemChanged(filteredIndex);
        } catch (Exception e) {
            ExceptionTracker.trackException(e);
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new BaseAdapterFilter(this, items, filteringStrategy);
        } else {
            filter.setFilteringStrategy(filteringStrategy);
        }
        return filter;
    }

    public void deleteItem(M item) {
        if(items == null || filteredItems == null) return;

        int indexOf = items.indexOf(item);
        if (items.remove(item) && filteredItems.remove(item)) {
            notifyItemRemoved(indexOf);
        }
    }

    public View getFooterLoadingView() {
        return footerLoadingView;
    }

    public void setFilteringStrategy(FilteringStrategy<M> filteringStrategy) {
        this.filteringStrategy = filteringStrategy;
    }

    public void setFilteringCompleteListener(FilteringCompleteListener filteringCompleteListener) {
        this.filteringCompleteListener = filteringCompleteListener;
    }

    private class BaseAdapterFilter extends BaseFilter<M, FilterableBaseAdapter<M, VH>> {
        private BaseAdapterFilter(FilterableBaseAdapter<M, VH> adapter, List<M> originalList, FilteringStrategy<M> filteringStrategy) {
            super(adapter, originalList, filteringStrategy);
        }
    }

    public class FooterViewHolder extends FilterableBaseViewHolder<M> {
        public FooterViewHolder(View itemView) {
            super(itemView);

            footerLoadingView = itemView.findViewById(R.id.list_footer_loader_root);
        }
    }
}
