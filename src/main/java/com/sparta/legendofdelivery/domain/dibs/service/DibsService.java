package com.sparta.legendofdelivery.domain.dibs.service;

import com.sparta.legendofdelivery.domain.dibs.entity.Dibs;
import com.sparta.legendofdelivery.domain.dibs.repository.DibsRepository;
import com.sparta.legendofdelivery.domain.store.entity.Store;
import com.sparta.legendofdelivery.domain.store.repository.StoreRepository;
import com.sparta.legendofdelivery.domain.user.entity.User;
import com.sparta.legendofdelivery.global.dto.MessageResponse;
import com.sparta.legendofdelivery.global.exception.BadRequestException;
import com.sparta.legendofdelivery.global.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DibsService {

    private final DibsRepository dibsRepository;
    private final StoreRepository storeRepository;

    public DibsService(DibsRepository dibsRepository, StoreRepository storeRepository) {
        this.dibsRepository = dibsRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public MessageResponse addDibs(Long storeId, User user){
        Store store = findStoreById(storeId);
        Dibs checkIsDibs = findDibsByStoreIdAndUserId(storeId, user.getId());
        if (checkIsDibs == null){
            Dibs dibs = new Dibs(user, store);
            dibsRepository.save(dibs);
            store.updateDibsCount(1L);
            return new MessageResponse(200, "가게 찜에 성공했습니다.");
        } else {
            throw new BadRequestException("이미 찜한 가게입니다.");
        }
    }

    @Transactional
    public MessageResponse deleteDibs(Long storeId, User user){
        Store store = findStoreById(storeId);
        Dibs checkIsDibs = findDibsByStoreIdAndUserId(storeId, user.getId());
        if (checkIsDibs != null){
            dibsRepository.delete(checkIsDibs);
            store.updateDibsCount(-1L);
            return new MessageResponse(200, "찜 삭제를 성공했습니다.");
        } else {
            throw new NotFoundException("해당 가게는 찜한 가게가 아닙니다.");
        }
    }

    private Store findStoreById(Long storeId){
        return storeRepository.findById(storeId).orElseThrow(
                () -> new NotFoundException("해당 id를 가진 가게가 없습니다.")
        );
    }

    private Dibs findDibsByStoreIdAndUserId(Long storeId, Long userId){
        return dibsRepository.findByStoreIdAndUserId(storeId, userId).orElse(null);
    }
}
