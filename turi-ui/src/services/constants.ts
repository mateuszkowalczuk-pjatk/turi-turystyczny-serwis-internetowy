export const API = {
    AUTH: {
        REGISTER: '/auth/register',
        LOGIN: '/auth/login',
        LOGOUT: '/auth/logout'
    },
    USER: {
        IS_USERNAME_EXISTS: '/user/isUsernameExists',
        IS_EMAIL_EXISTS: '/user/isEmailExists',
        CHANGE_USERNAME: '/user/changeUsername/{userId}',
        CHANGE_EMAIL: '/user/changeEmail/{userId}',
        CHANGE_PASSWORD: '/user/changePassword/{userId}'
    },
    ACCOUNT: {
        IS_ADDRESS_EXISTS: '/account/isAddressExists',
        IS_PHONE_NUMBER_EXISTS: '/account/isPhoneNumberExists',
        UPDATE_ACCOUNT: '/account/updateAccount/{accountId}'
    },
    ADDRESS: {
        CREATE_ADDRESS: '/address/createAddress'
    }
}
