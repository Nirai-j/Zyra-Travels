package com.zyra.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginationUtils {

    public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        
        return PageRequest.of(page, size, sort);
    }
    
    public static <T> Map<String, Object> createPageResponse(Page<T> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("size", page.getSize());
        response.put("hasNext", page.hasNext());
        response.put("hasPrevious", page.hasPrevious());
        response.put("isFirst", page.isFirst());
        response.put("isLast", page.isLast());
        
        return response;
    }
    
    public static <T> Map<String, Object> createListResponse(List<T> items, int page, int size, 
                                                             long totalItems, int totalPages) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", items);
        response.put("currentPage", page);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);
        response.put("size", size);
        response.put("hasNext", page < totalPages - 1);
        response.put("hasPrevious", page > 0);
        response.put("isFirst", page == 0);
        response.put("isLast", page == totalPages - 1);
        
        return response;
    }
}
