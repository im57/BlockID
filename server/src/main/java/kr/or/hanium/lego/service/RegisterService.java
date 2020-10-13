package kr.or.hanium.lego.service;

import kr.or.hanium.lego.domain.Holder;
import kr.or.hanium.lego.domain.StudentIdCard;
import kr.or.hanium.lego.domain.enumeration.CardStatus;
import kr.or.hanium.lego.repository.HolderRepository;
import kr.or.hanium.lego.repository.StudentIdCardRepository;
import kr.or.hanium.lego.vm.SignupVM;
import lombok.RequiredArgsConstructor;
import org.bitcoinj.core.Base58;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final HolderRepository holderRepository;
    private final StudentIdCardRepository studentIdCardRepository;

    public Long saveHolder(SignupVM signupVM) {
        Holder newHolder = new Holder();
        newHolder.setName(signupVM.getName());
        newHolder.setStudent_id(signupVM.getStudent_id());
        newHolder.setUniversity(signupVM.getUniversity());
        newHolder.setDepartment(signupVM.getDepartment());
        newHolder.setHolder_did(createDID(signupVM.getName(), signupVM.getStudent_id()));

        Holder savedHolder = holderRepository.save(newHolder);

        return savedHolder.getId();
    }

    public Long saveStudentIdCard(Long holder_id, SignupVM signupVM) {
        StudentIdCard newStCard = new StudentIdCard();
        LocalDateTime today = LocalDateTime.now();

        newStCard.setCard_did(createDID(signupVM.getUniversity(), signupVM.getStudent_id()));
        newStCard.setVerified_date(today);
        newStCard.setExpire_date(getExpireDate(today));
        newStCard.setStatus(CardStatus.ACTIVATED);
        newStCard.setHolder_id(holder_id);
        newStCard.setIssuer_id(0L);

        StudentIdCard savedStCard = studentIdCardRepository.save(newStCard);

        return savedStCard.getId();
    }

    public LocalDateTime getExpireDate(LocalDateTime today) {
        int thisYear = today.getYear();

        LocalDateTime thisFirstSemester = LocalDateTime.of(thisYear, 3, 1, 23, 59, 59);
        LocalDateTime thisSecondSemester = LocalDateTime.of(thisYear, 9, 1, 23, 59, 59);
        LocalDateTime nextFirstSemester = LocalDateTime.of(thisYear+1, 3, 1, 23, 59, 59);

        // 1~2월 신청 = 올해 3월 1일까지
       if (today.isBefore(today.withMonth(3))) {
           return thisFirstSemester;
       }
       // 3~8월 신청 = 올해 9월 1일까지
       else if (today.isBefore(today.withMonth(9))) {
           return thisSecondSemester;
       }
       // 9~12월 신청 = 내년 3월 1일까지
       else {
           return nextFirstSemester;
       }
    }

    private String createDID(String first, String second) {
        byte[] idByte = (first + second).getBytes();

        String did = "did:sov:";
        did += Base58.encode(idByte);

        return did;
    }
}
