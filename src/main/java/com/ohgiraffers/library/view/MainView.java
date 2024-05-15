package com.ohgiraffers.library.view;

import com.ohgiraffers.library.service.MainService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private static final MainService mainService = new MainService();
    private static Boolean isOn = true;

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (isOn) {
            // 메뉴를 출력한다.
            System.out.println("================================= 도서 대여 프로그램 ================================");
            System.out.println("1. 대출메뉴");
            System.out.println("2. 도서관리메뉴");
            System.out.println("3. 회원관리메뉴");
            System.out.println("4. 반납메뉴");
            System.out.println("9. 프로그램 종료");
            System.out.print("원하는 메뉴를 입력해주세요: ");
            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1 -> rentABook();
                case 2 -> bookManagement();
                case 3 -> memberManagement();
                case 4 -> returnABook();
                case 9 -> {
                    // 프로그램 종료
                    System.out.println("프로그램을 종료합니다.");
                    isOn = false;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
        scanner.close();
    }

    // 대출관련 메서드 작성 ------------------------------------------------------------------------------------------------
    public static void rentABook() {
        Scanner scanner = new Scanner(System.in);
        String result = "";
        String bookName = "";
        int bookNum = 0;
        System.out.println("================================= 도서 대출 메뉴 =================================");
        // 대출받을 회원을 먼저 검증한다.
        System.out.print("회원의 이름을 입력해 주세요: ");
        String memberName = scanner.nextLine();
        int memberNum = mainService.memberNameValid(memberName);
        if (memberNum == -1) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }
        System.out.print("대출할 책의 수량을 입력해 주세요: ");
        int rentQuantity = scanner.nextInt();
        scanner.nextLine();
        int[] rentBooks = new int[rentQuantity];
        for (int i = 0; i < rentQuantity; i++) {
            System.out.print("등록할 도서명을 입력해주세요: ");
            bookName = scanner.nextLine();
            bookNum = mainService.bookNameValid(bookName);
            if (bookNum == -1) {
                System.out.println("등록된 도서가 없습니다.");
                return;
            }
            rentBooks[i] = bookNum;
        }

        mainService.rentABook(memberName, memberNum, rentBooks);
    }

    // 도서관리 메서드 작성 ------------------------------------------------------------------------------------------------
    /*
     *   1. 도서 등록, 2. 도서명 검색, 3. 도서 수정, 4. 도서 삭제, 5. 전체 도서 검색, 9. 이전메뉴
     */
    public static void bookManagement() {
        Scanner scanner = new Scanner(System.in);
        while (isOn) {
            System.out.println("================================== 도서 관리 메뉴 ==================================");
            System.out.println("1. 도서 등록");
            System.out.println("2. 도서명 검색");
            System.out.println("3. 도서 수정");
            System.out.println("4. 도서 삭제");
            System.out.println("5. 전체 도서 검색");
            System.out.println("9. 이전 메뉴");
            System.out.print("메뉴를 선택해 주세요: ");

            try {
                int menuChoice = scanner.nextInt();
                scanner.nextLine();

                switch (menuChoice) {
                    case 1 -> bookRegistration();
                    case 2 -> bookNameSearch();
                    case 3 -> bookModify();
                    case 4 -> bookDelete();
                    case 5 -> mainService.registeredBookList();
                    case 9 -> {
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 1. 도서등록
    public static void bookRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("등록할 도서의 수량을 입력하세요: ");
        int bookQuantity = scanner.nextInt();
        scanner.nextLine();
        String[] books = new String[bookQuantity];
        String result = "";
        for (int i = 0; i < bookQuantity; i++) {
            System.out.print("등록할 도서명을 입력해주세요: ");
            String bookName = scanner.nextLine();
            books[i] = bookName;
        }
        mainService.bookRegistration(bookQuantity, books);
    }

    // 2. 도서명 검색
    public static void bookNameSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 도서명을 입력해주세요: ");
        String bookName = scanner.nextLine();
        mainService.bookNameSearch(bookName);

    }

    // 3. 도서 수정
    public static void bookModify() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 도서명을 입력해주세요: ");
        String bookName = scanner.nextLine();
        System.out.print("새로운 도서명을 입력해주세요: ");
        String newBookName = scanner.nextLine();
        mainService.bookModify(bookName, newBookName);
    }

    // 4. 도서 삭제
    public static void bookDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 도서명을 입력해주세요: ");
        String bookName = scanner.nextLine();
        mainService.bookDelete(bookName);
    }

    // 회원관리 메서드 작성 ------------------------------------------------------------------------------------------------
    /*
     *   1. 회원 등록, 2. 회원명 검색, 3. 회원 수정, 4. 회원 삭제, 5. 전체 회원 검색, 9. 이전메뉴
     */
    public static void memberManagement() {
        Scanner scanner = new Scanner(System.in);
        while (isOn) {
            System.out.println("================================== 회원 관리 메뉴 ==================================");
            System.out.println("1. 회원 등록");
            System.out.println("2. 회원명 검색");
            System.out.println("3. 회원 수정");
            System.out.println("4. 회원 삭제");
            System.out.println("5. 전체 회원 검색");
            System.out.println("9. 이전 메뉴");
            System.out.print("메뉴를 선택해 주세요: ");

            try {
                int menuChoice = scanner.nextInt();
                scanner.nextLine();

                switch (menuChoice) {
                    case 1 -> memberRegistration();
                    case 2 -> memberNameSearch();
                    case 3 -> memberModify();
                    case 4 -> memberDelete();
                    case 5 -> mainService.registeredMemberList();
                    case 9 -> {
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.nextLine();
            }
        }
    }

    // 1. 회원 등록
    public static void memberRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("등록할 회원의 인원을 입력하세요: ");
        int memberQuantity = scanner.nextInt();
        scanner.nextLine();
        String[] members = new String[memberQuantity];
        String result = "";
        for (int i = 0; i < memberQuantity; i++) {
            System.out.print("등록할 회원명을 입력해주세요: ");
            String memberName = scanner.nextLine();
            members[i] = memberName;
        }
        mainService.memberRegistration(memberQuantity, members);
    }

    // 2. 회원명 검색
    public static void memberNameSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 회원명을 입력해주세요: ");
        String memberName = scanner.nextLine();
        mainService.memberNameSearch(memberName);
    }

    // 3. 회원 수정
    public static void memberModify() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("수정할 회원명을 입력해주세요: ");
        String memberName = scanner.nextLine();
        System.out.print("새로운 회원명을 입력해주세요: ");
        String newMemberName = scanner.nextLine();
        mainService.memberModify(memberName, newMemberName);
    }

    // 4. 회원 삭제
    public static void memberDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 회원명을 입력해주세요: ");
        String memberName = scanner.nextLine();
        mainService.memberDelete(memberName);
    }

    // 반납관련 메서드 작성 ------------------------------------------------------------------------------------------------
    public static void returnABook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("================================= 도서 반납 메뉴 =================================");
        try {
            System.out.print("반납할 책의 번호를 입력해 주세요: ");
            int bookNum = scanner.nextInt();
            scanner.nextLine();
            mainService.returnABook(bookNum);
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();
        }
    }
}
