package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

public interface IMenuItemService {
    public List<MenuItem> getTop10BestSellingMenuItems();
    public List<MenuItem> getTop10LatestCreatedMenuItems(LocalDate daysAgo);
    public List<MenuItem> getMenuItemsByCategoryId(int categoryId);
    public List<MenuItem> getMenuItemsWithPagination(LocalDate fromDate, int page, int pageSize);
    <S extends MenuItem> S save(S entity);
}
