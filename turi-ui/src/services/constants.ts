export const API = {
    AUTH: {
        REGISTER: '/auth/register',
        LOGIN: '/auth/login',
        REFRESH: '/auth/refresh',
        LOGOUT: '/auth/logout'
    },
    USER: {
        IS_USERNAME_EXISTS: '/user/isUsernameExists',
        IS_EMAIL_EXISTS: '/user/isEmailExists',
        SEND_RESET_PASSWORD_CODE: '/user/sendResetPasswordCode',
        RESET_PASSWORD: '/user/resetPassword',
        CHANGE_USERNAME: '/user/changeUsername/',
        CHANGE_EMAIL: '/user/changeEmail/',
        CHANGE_PASSWORD: '/user/changePassword/'
    },
    ACCOUNT: {
        GET_BY_ID: '/account/getById',
        IS_ADDRESS_EXISTS: '/account/isAddressExists',
        IS_PHONE_NUMBER_EXISTS: '/account/isPhoneNumberExists',
        ACTIVATE: '/account/activate',
        RESEND_ACTIVATE_CODE: '/account/resendActivateCode',
        UPDATE_ACCOUNT: '/account/updateAccount/'
    },
    ADDRESS: {
        GET_BY_ID: '/address/getById',
        CREATE_ADDRESS: '/address/createAddress'
    }
}
