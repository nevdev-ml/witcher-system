package com.nevdev.witcher.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DealListViewModel {
    private List<DealViewModel> active;
    private List<DealViewModel> success;
    private List<DealViewModel> bookmarked;

    public DealListViewModel(List<DealViewModel> active, List<DealViewModel> success,
                             List<DealViewModel> bookmarked){
        this.active = active;
        this.success = success;
        this.bookmarked = bookmarked;
    }
}
