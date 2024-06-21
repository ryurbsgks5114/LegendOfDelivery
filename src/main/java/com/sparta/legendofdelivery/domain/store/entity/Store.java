package com.sparta.legendofdelivery.domain.store.entity;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String intro;

    @Column(name = "dibs_count")
    private Long dibsCount;

    @Column(name = "review_count")
    private Long reviewCount;

    @Column
    private StoreState storeState;

    public Store(StoreRequestDto requestDto) {
        this.name = requestDto.getName();
        this.category = requestDto.getCategory();
        this.intro = requestDto.getIntro();
    }

}
