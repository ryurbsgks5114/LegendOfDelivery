package com.sparta.legendofdelivery.domain.store.entity;

import com.sparta.legendofdelivery.domain.store.dto.StoreRequestDto;
import com.sparta.legendofdelivery.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store extends Timestamped {

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
        this.dibsCount = 0L;
        this.reviewCount = 0L;
        this.storeState = StoreState.CLOSE;
    }

    public void updateStore (StoreRequestDto requestDto) {
        this.name = requestDto.getName();
        this.category = requestDto.getCategory();
        this.intro = requestDto.getIntro();
    }

    public void openStore () {
        this.storeState = StoreState.OPEN;
    }

    public void closeStore () {
        this.storeState = StoreState.CLOSE;
    }

    public void updateDibsCount(Long count){
        this.dibsCount += count;
    }

}
