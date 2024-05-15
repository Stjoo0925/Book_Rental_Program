package com.ohgiraffers.library.service;

import com.ohgiraffers.library.dao.MainRepository;

import java.util.ArrayList;

public class MainService {
    private MainRepository mainRepository;

    public MainService() {
        this.mainRepository = new MainRepository();
    }

    // 도서 대출 메서드 --------------------------------------------------------------------------------------------------
    public int memberNameValid(String memberName) {
        return mainRepository.memberNameValid(memberName);
    }

    public int bookNameValid(String bookName) {
        return mainRepository.bookNameValid(bookName);
    }

    public String rentABook(String memberName, int memberNum, int[] rentBooks) {
        return mainRepository.rentABook(memberName, memberNum, rentBooks);
    }

    // 도서 관리 메서드 --------------------------------------------------------------------------------------------------
    public ArrayList bookRegistration(int bookQuantity, String[] bookName) {
        return mainRepository.bookRegistration(bookQuantity, bookName);
    }

    public ArrayList bookNameSearch(String bookName) {
        return mainRepository.bookNameSearch(bookName);
    }

    public ArrayList bookModify(String bookName, String newBookName) {
        return mainRepository.bookModify(bookName, newBookName);
    }

    public ArrayList bookDelete(String bookName) {
        return mainRepository.bookDelete(bookName);
    }

    public ArrayList registeredBookList() throws Exception {
        ArrayList bookList = mainRepository.registeredBookList();
        if (bookList.isEmpty()) {
            throw new Exception("도서 조회실패");
        }
        return bookList;
    }

    /*
    public int bookListSize() {
        return mainRepository.bookListSize();
    }
    */

    // 회원 관리 메서드 --------------------------------------------------------------------------------------------------
    public ArrayList memberRegistration(int memberQuantity, String[] memberName) {
        return mainRepository.memberRegistration(memberQuantity, memberName);
    }

    public ArrayList memberNameSearch(String memberName) {
        return mainRepository.memberNameSearch(memberName);
    }

    public ArrayList memberModify(String memberName, String newMemberName) {
        return mainRepository.memberModify(memberName, newMemberName);
    }

    public ArrayList memberDelete(String memberName) {
        return mainRepository.memberDelete(memberName);
    }

    public void registeredMemberList() {
        mainRepository.registeredMemberList();
    }

    /*
    public int memberListSize() {
        return mainRepository.memberListSize();
    }
    */

    // 도서 반납 메서드 --------------------------------------------------------------------------------------------------
    public void returnABook(int bookNum) {
        System.out.println(mainRepository.returnABook(bookNum));
    }
}
