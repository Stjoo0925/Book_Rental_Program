package com.ohgiraffers.library.dto;

public class MemberDTO {

    private int memberNum;
    private String memberName;
    private String memberRentalList;
    private String memberStatus;

   // MemberDTO -------------------------------------------------------------------------------------------------------

    public MemberDTO(int memberNum, String memberName, String memberRentalList, String memberStatus) {
        this.memberNum = memberNum;
        this.memberName = memberName;
        this.memberRentalList = memberRentalList;
        this.memberStatus = memberStatus;
    }

    public MemberDTO() {

    }

    public MemberDTO(String memberName, int memberNum) {
        this.memberName = memberName;
        this.memberNum = memberNum;
    }

    // Getter & Setter ------------------------------------------------------------------------------------------------

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberRentalList() {
        return memberRentalList;
    }

    public void setMemberRentalList(String memberRentalList) {
        this.memberRentalList = memberRentalList;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    // toString -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return  '[' + "회원번호 : " + memberNum +
                ", 회원명 : '" + memberName + '\'' +
                ", 회원이 대출중인 도서 : '" + memberRentalList + '\'' +
                ", 회원등록상태 : '" + memberStatus + '\'' +
                "]\n";
    }
}
