package kr.or.hanium.lego.service;

import kr.or.hanium.lego.domain.StudentIdCard;
import kr.or.hanium.lego.domain.enumeration.CardStatus;
import kr.or.hanium.lego.repository.StudentIdCardRepository;
import kr.or.hanium.lego.vm.FetchIdcardResultVM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentIdCardService {
    private final StudentIdCardRepository studentIdCardRepository;

    public FetchIdcardResultVM fetchStudentIdCardWithHolderId(Long holder_id) {
        StudentIdCard idCard = studentIdCardRepository.findByHolder_id(holder_id);

        if (LocalDateTime.now().isAfter(idCard.getExpire_date())) {
            idCard.setStatus(CardStatus.EXPIRED);
            studentIdCardRepository.save(idCard);
        }

        return setIdCardToIdCardResultVM(idCard);
    }

    public FetchIdcardResultVM activateStudentIdCardWithHolderId(Long holder_id, LocalDateTime expireDate) {
        StudentIdCard idCard = studentIdCardRepository.findByHolder_id(holder_id);
        idCard.setExpire_date(expireDate);
        idCard.setStatus(CardStatus.ACTIVATED);

        studentIdCardRepository.save(idCard);

        return setIdCardToIdCardResultVM(idCard);
    }

    private FetchIdcardResultVM setIdCardToIdCardResultVM(StudentIdCard idCard) {
        FetchIdcardResultVM idCardVM = new FetchIdcardResultVM();
        idCardVM.setStudentId(idCard.getHolder().getStudent_id());
        idCardVM.setDepartment(idCard.getHolder().getDepartment());
        idCardVM.setExpireDate(idCard.getExpire_date());
        idCardVM.setStatus(idCard.getStatus());
        idCardVM.setUniversity(idCard.getHolder().getUniversity());
        idCardVM.setHolder_id(idCard.getHolder_id());

        return idCardVM;
    }
}
