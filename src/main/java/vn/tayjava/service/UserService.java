package vn.tayjava.service;

import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.tayjava.dto.request.UserRequestDTO;
import vn.tayjava.dto.response.PageResponse;
import vn.tayjava.dto.response.UserDetailResponse;
import vn.tayjava.model.User;
import vn.tayjava.util.UserStatus;

import java.io.UnsupportedEncodingException;

public interface UserService {

    UserDetailsService userDetailsService();

    long saveUser(UserRequestDTO request) throws MessagingException, UnsupportedEncodingException;

    long saveUser(User user);

    void updateUser(long userId, UserRequestDTO request);

    void changeStatus(long userId, UserStatus status);

    String confirmUser(int userId, String verifyCode);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search);
}
