package test.andranik.filterableadapter.filterable_adapter;

import java.util.List;

/**
 * Created by andranikh on 4/25/17.
 */

public class LocalFilteringStrategy<T extends FilterableModel> implements FilteringStrategy<T>{

    public static final String IGNORE_FILTER = "filters.IgnoreFilter";

    @Override
    public List<T> performFiltering(CharSequence charSequence, List<T> originalList, List<T> filteredList) {
        filteredList.clear();

        if (charSequence.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String[] filterPattern = charSequence.toString().toLowerCase().split(" ");

            for (final T object : originalList) {
                for (String filterableString : object.getFilterableFields()) {

                    if (filterableString != null) {
                        String[] splittedString = filterableString.toLowerCase().split(" ");

                        if (IGNORE_FILTER.equals(filterableString) || findMatch(filterPattern, splittedString)) {
                            filteredList.add(object);
                        }
                    }
                }
            }
        }

        return filteredList;
    }


    private boolean findMatch(String[] filterPattern, String[] splittedName){
        if(filterPattern.length == 1 && splittedName.length == 1){
            return splittedName[0].startsWith(filterPattern[0]);
        }
        else if (filterPattern.length == 1){
            for(String name : splittedName){
                if(name.startsWith(filterPattern[0])){
                    return true;
                }
            }
        }
        else if(filterPattern.length > splittedName.length){
            return false;
        }
        else if(filterPattern.length <= splittedName.length){
            boolean match = true;
            for (int i = 0; i < filterPattern.length; i++){
                if(!splittedName[i].startsWith(filterPattern[i])){
                    match = false;
                }
            }

            return match;
        }

        return false;
    }
}
