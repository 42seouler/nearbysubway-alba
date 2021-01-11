package chocolate.chocho.service.employer.store.query;

import chocolate.chocho.dto.StoreQueryDto;
import chocolate.chocho.entity.Store;
import chocolate.chocho.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    @Override
    public StoreQueryDto findById(UUID storeId) {
        //todo 오류 검증 추가하기
        Store store = storeRepository.findById(storeId).orElseThrow();
        return entityToDto(store);
    }

    @Override
    public Page<StoreQueryDto> findAll(int page, int size) {
        Page<Store> stores = storeRepository.findAll(PageRequest.of(page, size));
        return stores.map(this::entityToDto);
    }

    private StoreQueryDto entityToDto(Store store) {
        return new StoreQueryDto(store.getName(), store.getAddress(), store.getEmployer());
    }
}