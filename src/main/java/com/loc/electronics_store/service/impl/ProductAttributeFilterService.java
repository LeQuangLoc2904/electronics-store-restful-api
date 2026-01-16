package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service để lấy danh sách các giá trị thuộc tính có thể lọc
 *
 * Mục đích:
 * - Cung cấp cho frontend danh sách các options lọc
 * - Ví dụ: RAM có các giá trị nào? Chip có các giá trị nào?
 *
 * Ví dụ response:
 * {
 *   "RAM": ["4GB", "6GB", "8GB", "12GB"],
 *   "Chip": ["A15 Bionic", "A16 Bionic", "A17 Pro"],
 *   "Màn hình": ["6.1 inch", "6.7 inch"]
 * }
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeFilterService {

    ProductAttributeRepository attributeRepository;

    /**
     * Lấy tất cả các giá trị thuộc tính để hiển thị trên frontend
     * Dùng để tạo filter options
     *
     * @return Map<Tên thuộc tính, Danh sách giá trị>
     */
    public Map<String, List<String>> getAllAttributeValues() {
        return attributeRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                    attr -> attr.getName(),
                    Collectors.mapping(attr -> attr.getValue(), Collectors.toList())
                ));
    }

    /**
     * Lấy các giá trị thuộc tính của một category cụ thể
     *
     * @param categoryId ID của category
     * @return Map<Tên thuộc tính, Danh sách giá trị>
     */
    public Map<String, List<String>> getAttributeValuesByCategory(Long categoryId) {
        return attributeRepository.findByProduct_Category_Id(categoryId)
                .stream()
                .collect(Collectors.groupingBy(
                    attr -> attr.getName(),
                    Collectors.mapping(attr -> attr.getValue(), Collectors.toList())
                ));
    }
}

