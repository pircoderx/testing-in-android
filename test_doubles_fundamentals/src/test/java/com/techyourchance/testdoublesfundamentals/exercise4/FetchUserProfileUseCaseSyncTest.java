package com.techyourchance.testdoublesfundamentals.exercise4;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FetchUserProfileUseCaseSyncTest {
    public static final String USER_ID = "userId";
    public static final String FULL_NAME = "fullName";
    public static final String IMAGE_URL = "imageUrl";

    TdUsrProfHttpEndpointSync td_usr_prof_http_endpoint_sync;
    TdUsersCache td_users_cache;

    FetchUserProfileUseCaseSync SUT;

    @Before
    public void setup() throws Exception {
        td_usr_prof_http_endpoint_sync = new TdUsrProfHttpEndpointSync();
        td_users_cache = new TdUsersCache();
        SUT = new FetchUserProfileUseCaseSync(td_usr_prof_http_endpoint_sync, td_users_cache);
    }

    @Test
    public void fetchUserProfileSync_success_userIdPassedToEndpoint() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(td_usr_prof_http_endpoint_sync.user_id, CoreMatchers.is(USER_ID));
    }

    @Test
    public void fetchUserProfileSync_success_userCached() {
        SUT.fetchUserProfileSync(USER_ID);
        User cachedUser = td_users_cache.getUser(USER_ID);
        assertThat(cachedUser.getUserId(), CoreMatchers.is(USER_ID));
        assertThat(cachedUser.getFullName(), CoreMatchers.is(FULL_NAME));
        assertThat(cachedUser.getImageUrl(), CoreMatchers.is(IMAGE_URL));
    }

    @Test
    public void fetchUserProfileSync_generalError_userNotCached() {
        td_usr_prof_http_endpoint_sync.is_err_general = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(td_users_cache.getUser(USER_ID), CoreMatchers.is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_authError_userNotCached() {
        td_usr_prof_http_endpoint_sync.is_err_auth = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(td_users_cache.getUser(USER_ID), CoreMatchers.is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_serverError_userNotCached() {
        td_usr_prof_http_endpoint_sync.is_err_server = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(td_users_cache.getUser(USER_ID), CoreMatchers.is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_success_successReturned() {
        assertThat(SUT.fetchUserProfileSync(USER_ID), CoreMatchers.is(FetchUserProfileUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchUserProfileSync_serverError_failureReturned() {
        td_usr_prof_http_endpoint_sync.is_err_server = true;
        assertThat(SUT.fetchUserProfileSync(USER_ID), CoreMatchers.is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_authError_failureReturned() {
        td_usr_prof_http_endpoint_sync.is_err_auth = true;
        assertThat(SUT.fetchUserProfileSync(USER_ID), CoreMatchers.is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_generalError_failureReturned() {
        td_usr_prof_http_endpoint_sync.is_err_general = true;
        assertThat(SUT.fetchUserProfileSync(USER_ID), CoreMatchers.is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_networkError_networkErrorReturned() {
        td_usr_prof_http_endpoint_sync.is_err_network = true;
        assertThat(SUT.fetchUserProfileSync(USER_ID), CoreMatchers.is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    // ---------------------------------------------------------------------------------------------
    // Helper classes

    private static class TdUsrProfHttpEndpointSync implements UserProfileHttpEndpointSync {
        public String user_id = "";
        public boolean is_err_general;
        public boolean is_err_auth;
        public boolean is_err_server;
        public boolean is_err_network;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            user_id = userId;
            if (is_err_general) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if (is_err_auth) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            }  else if (is_err_server) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (is_err_network) {
                throw new NetworkErrorException();
            } else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, FULL_NAME, IMAGE_URL);
            }
        }

    }

    private static class TdUsersCache implements UsersCache {

        private List<User> users = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existingUser = getUser(user.getUserId());
            if (existingUser != null) {
                users.remove(existingUser);
            }
            users.add(user);
        }

        @Override
        @Nullable
        public User getUser(String userId) {
            for (User user : users) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        }
    }
}