package com.sasajankovic.domain.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@Getter
public class SearchCityParams {
    private String name;
    private Optional<Integer> numberOfComments;

    public SearchCityParams(@NonNull String name, Optional<Integer> numberOfComments) {
        this.name = name;
        this.numberOfComments = numberOfComments;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result =
                101 * result
                        + (numberOfComments.isPresent() ? numberOfComments.get().hashCode() : 201);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof SearchCityParams)) return false;
        SearchCityParams searchCityParams = (SearchCityParams) obj;
        return this.name.equals(searchCityParams.getName())
                && this.numberOfComments.equals(searchCityParams.getNumberOfComments());
    }
}
