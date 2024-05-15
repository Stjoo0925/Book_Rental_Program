package com.ohgiraffers.library.dto;

public class BookDTO {

    private int bookNum;
    private String bookName;
    private String bookStatus;

    // BookDTO --------------------------------------------------------------------------------------------------------

    public BookDTO(int bookNum, String bookName, String bookRent) {
        this.bookNum = bookNum;
        this.bookName = bookName;
        this.bookStatus = bookRent;
    }

    public BookDTO() {
    }

    public BookDTO(String bookName, int bookNum) {
        this.bookName = bookName;
        this.bookNum = bookNum;
    }

    // Getter & Setter ------------------------------------------------------------------------------------------------

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    // toString -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return  '[' + "도서번호 : " + bookNum +
                ", 도서명 : '" + bookName + '\'' +
                ", 도서보유상태 : '" + bookStatus + '\'' +
                "]\n";
    }
}
