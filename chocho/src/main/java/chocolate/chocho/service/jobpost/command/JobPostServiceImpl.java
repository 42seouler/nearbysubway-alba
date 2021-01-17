package chocolate.chocho.service.jobpost.command;


import chocolate.chocho.dto.JobPostCmdDto;
import chocolate.chocho.entity.JobOpening;
import chocolate.chocho.entity.JobPost;
import chocolate.chocho.entity.Store;
import chocolate.chocho.entity.StoreMgmt;
import chocolate.chocho.repository.JobPostRepository;
import chocolate.chocho.repository.StoreMgmtRepository;
import chocolate.chocho.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    private final StoreRepository       storeRepository;
    private final JobPostRepository     jobPostRepository;
    private final StoreMgmtRepository   storeMgmtRepository;

    @Override
    public Long create(Long storeId, JobPostCmdDto dto) {
        Store findStore = storeRepository.findById(storeId).orElseThrow();
        JobPost newJobPost = new JobPost(findStore, dto.getTitle(), dto.getBody());
        jobPostRepository.save(newJobPost);
        StoreMgmt storeMgmt = storeMgmtRepository.findByStore(findStore).orElseThrow();
        storeMgmt.jobOpeningChangeState(newJobPost.getJobOpening());
        return newJobPost.getId();
    }

    @Override
    public JobPostCmdDto update(Long postId, Long storeId, JobPostCmdDto dto) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        JobPost jobPost = jobPostRepository.findById(postId).orElseThrow();
        //todo 유저 불일치로 에러
        jobPost.update(dto.getTitle(), dto.getBody(), dto.getJobOpening());
        StoreMgmt storeMgmt = storeMgmtRepository.findByStore(store).orElseThrow();
        storeMgmt.jobOpeningChangeState(jobPost.getJobOpening());
        return entityToDto(jobPost);
    }

    @Override
    public void delete(Long postId, Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        JobPost jobPost = jobPostRepository.findById(postId).orElseThrow();
        //todo 유저 불일치 에러 추가
        jobPostRepository.delete(jobPost);
        StoreMgmt storeMgmt = storeMgmtRepository.findByStore(store).orElseThrow();
        storeMgmt.jobOpeningChangeState(JobOpening.CLOSE);
    }

    public JobPostCmdDto entityToDto(JobPost jobPost) {

        return new JobPostCmdDto(jobPost.getTitle(), jobPost.getBody(), jobPost.getJobOpening());
    }
}
