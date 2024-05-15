package com.ohgiraffers.library.dao;

import com.ohgiraffers.library.dto.BookDTO;
import com.ohgiraffers.library.dto.MemberDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;


public class MainRepository {
    private final ArrayList<MemberDTO> memberList = new ArrayList<>();
    private final ArrayList<BookDTO> bookList = new ArrayList<>();

    private Properties props = new Properties();
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public MainRepository() {
        try {
            this.props.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/library/mapper/project01-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 도서 대출 메서드 --------------------------------------------------------------------------------------------------
    public int memberNameValid(String memberName) {
        String query = props.getProperty("memberNameValid");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberName);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                MemberDTO member = new MemberDTO();
                member.setMemberNum(rset.getInt("memberNum"));
                memberList.add(member);
                return member.getMemberNum();
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public int bookNameValid(String bookName) {
        String query = props.getProperty("bookNameValid");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, bookName);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                BookDTO book = new BookDTO();
                book.setBookNum(rset.getInt("bookNum"));
                bookList.add(book);
                return book.getBookNum();
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public String rentABook(String memberName, int memberNum, int[] rentBooks) {
        MemberDTO member = memberList.get(memberNum);
        BookDTO book = bookList.get(0);
        // memberDTO 대출시 입력해야하는것 memberName, memberNum
        // 변경되야 하는것 memberRentalList
        // bookDTO 대출시 입력해야하는것 bookNum, bookName
        // 변경되야 하는것
        for (int i = 0; i < rentBooks.length; i++) {
            if (book.getBookStatus() != null) {
                return book.getBookStatus() + " 님이 대여중인 도서입니다.";
            }
            book.setBookStatus(member.getMemberName());
            if (member.getMemberRentalList() == null) {
                member.setMemberRentalList(book.getBookName());
            } else {
                member.setMemberRentalList(member.getMemberRentalList() + ", " + book.getBookName());
            }
        }
        return member.getMemberName() + " 님에게 " + book.getBookName() + " 이 대여되었습니다.";
    }

    // 도서 관리 메서드 --------------------------------------------------------------------------------------------------
    /*
    public int bookListSize() {
        int bookNum1;
        int bookNum2;
        int bookNumberReturn = 0;
        for (int i = 0; i < bookList.size(); i++) {
            BookDTO book1 = bookList.get(i);
            if (bookList.isEmpty()) {
                return 0;
            }
            if (bookList.size() == 1) {
                return 1;
            }
            if ( i+1 < bookList.size()) {
                BookDTO book2 = bookList.get(i+1);
                bookNum1 = book1.getBookNum();
                bookNum2 = book2.getBookNum();
                bookNumberReturn = (bookNum1 > bookNum2)? bookNum1 : bookNum2;
            } else {
                break;
            }
        }
        return bookNumberReturn;
    }
    */

    public ArrayList bookRegistration(int bookQuantity, String[] bookName) {
        String result = "";
        ArrayList bookList = new ArrayList();
        String query = props.getProperty("bookRegistration");
        con = getConnection();
        try {
            for (int i = 0; i < bookQuantity; i++) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, bookName[i]);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    result = "도서가 성공적으로 등록되었습니다.";
                } else {
                    result = "도서등록에 실패 하였습니다.";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(result);
            close(rset);
            close(pstmt);
            close(con);
        }
        return bookList;
    }

    public ArrayList bookNameSearch(String bookName) {
        ArrayList bookList = new ArrayList();
        String query = props.getProperty("bookNameSearch");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, bookName);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                BookDTO book = new BookDTO();
                book.setBookNum(rset.getInt("bookNum"));
                book.setBookName(rset.getString("bookName"));
                book.setBookStatus(rset.getString("bookStatus"));
                bookList.add(book);
                System.out.println(book.toString());
            }
            if (bookList.isEmpty()) {
                System.out.println("조회결과 없는 도서입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return bookList;
    }

    public ArrayList bookModify(String bookName, String newBookName) {
        ArrayList bookList = new ArrayList();
        int result = 0;
        String query = props.getProperty("bookModify");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newBookName);
            pstmt.setString(2, bookName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("도서 이름이 성공적으로 수정되었습니다.");
            } else {
                System.out.println("도서 이름을 수정하는데 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return bookList;
    }

    public ArrayList bookDelete(String bookName) {
        ArrayList bookList = new ArrayList();
        String query = props.getProperty("bookDelete");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, bookName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("도서의 상태가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("도서의 상태를 수정하는데 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return bookList;
    }

    public ArrayList registeredBookList() {
        ArrayList bookList = new ArrayList();
        String query = props.getProperty("registeredBookList");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                BookDTO book = new BookDTO();
                book.setBookNum(rset.getInt("bookNum"));
                book.setBookName(rset.getString("bookName"));
                book.setBookStatus(rset.getString("bookStatus"));
                bookList.add(book);
                System.out.println(book.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return bookList;
    }

    // 회원 관리 메서드 --------------------------------------------------------------------------------------------------
    /*
    public int memberListSize() {
        int memberNum1;
        int memberNum2;
        int memberNumberReturn = 0;
        for (int i = 0; i < memberList.size(); i++) {
            MemberDTO member1 = memberList.get(i);
            if (memberList.isEmpty()) {
                return 0;
            }
            if (memberList.size() == 1) {
                return 1;
            }
            if ( i+1 < memberList.size()) {
                MemberDTO member2 = memberList.get(i+1);
                memberNum1 = member1.getMemberNum();
                memberNum2 = member2.getMemberNum();
                memberNumberReturn = (memberNum1 > memberNum2)? memberNum1 : memberNum2;
            } else {
                break;
            }
        }
        return memberNumberReturn;
    }
    */

    public ArrayList memberRegistration(int memberQuantity, String[] memberName) {
        String result = "";
        ArrayList memberList = new ArrayList();
        String query = props.getProperty("memberRegistration");
        con = getConnection();
        try {
            for (int i = 0; i < memberQuantity; i++) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, memberName[i]);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    result = "회원이 성공적으로 등록되었습니다.";
                } else {
                    result = "회원등록에 실패 하였습니다.";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println(result);
            close(rset);
            close(pstmt);
            close(con);
        }
        return memberList;
    }

    public ArrayList memberNameSearch(String memberName) {
        ArrayList memberList = new ArrayList();
        String query = props.getProperty("memberNameSearch");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberName);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                MemberDTO member = new MemberDTO();
                member.setMemberNum(rset.getInt("memberNum"));
                member.setMemberName(rset.getString("memberName"));
                member.setMemberRentalList(rset.getString("memberRentalList"));
                member.setMemberStatus(rset.getString("memberStatus"));
                memberList.add(member);
                System.out.println(member.toString());
            }
            if (memberList.isEmpty()) {
                System.out.println("조회결과 없는 회원입니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return memberList;
    }

    public ArrayList memberModify(String memberName, String newMemberName) {
        ArrayList memberList = new ArrayList();
        int result = 0;
        String query = props.getProperty("memberModify");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newMemberName);
            pstmt.setString(2, memberName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("회원 이름이 성공적으로 수정되었습니다.");
            } else {
                System.out.println("회원 이름을 수정하는데 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return memberList;
    }

    public ArrayList memberDelete(String memberName) {
        ArrayList memberList = new ArrayList();
        String query = props.getProperty("memberDelete");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberName);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("회원의 상태가 성공적으로 변경되었습니다.");
            } else {
                System.out.println("회원의 상태를 수정하는데 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return memberList;
    }

    public ArrayList registeredMemberList() {
        ArrayList memberList = new ArrayList();
        String query = props.getProperty("registeredMemberList");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                MemberDTO member = new MemberDTO();
                member.setMemberNum(rset.getInt("memberNum"));
                member.setMemberName(rset.getString("memberName"));
                member.setMemberRentalList(rset.getString("memberRentalList"));
                member.setMemberStatus(rset.getString("memberStatus"));
                memberList.add(member);
                System.out.println(member.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return memberList;
    }

    // 도서 반납 메서드 --------------------------------------------------------------------------------------------------
    public String returnABook(int bookNum) {
        if (bookNum >= bookList.size()) {
            return "해당하는 도서가 없습니다.";
        }
        BookDTO book = bookList.get(bookNum);

        if (book.getBookStatus() == null) {
            return "해당 도서는 대여되지 않았습니다.";
        }
        book.setBookStatus(null);
        return book.getBookName() + " 이 반납되었습니다.";
    }
}