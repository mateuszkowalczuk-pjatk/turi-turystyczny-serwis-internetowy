export const API = {
    AUTH: {
        REGISTER: '/api/auth/register',
        LOGIN: '/api/auth/login',
        LOGIN_PREMIUM: '/api/auth/loginPremium',
        AUTHORIZE: '/api/auth/authorize',
        REFRESH: '/api/auth/refresh',
        LOGOUT: '/api/auth/logout'
    },
    USER: {
        GET_USERNAME: '/api/user/getUsername',
        GET_EMAIL: '/api/user/getEmail',
        IS_USERNAME_EXISTS: '/api/user/isUsernameExists',
        IS_EMAIL_EXISTS: '/api/user/isEmailExists',
        SEND_RESET_PASSWORD_CODE: '/api/user/sendResetPasswordCode',
        RESET_PASSWORD: '/api/user/resetPassword',
        CHANGE_USERNAME: '/api/user/changeUsername',
        CHANGE_EMAIL: '/api/user/changeEmail',
        CHANGE_PASSWORD: '/api/user/changePassword'
    },
    ACCOUNT: {
        GET_BY_ID: '/api/account/getById',
        IS_ADDRESS_EXISTS: '/api/account/isAddressExists',
        IS_PHONE_NUMBER_EXISTS: '/api/account/isPhoneNumberExists',
        ACTIVATE: '/api/account/activate',
        UPDATE: '/api/account/update'
    },
    ADDRESS: {
        GET_BY_ID: '/api/address/getById',
        CREATE: '/api/address/create'
    },
    PREMIUM: {
        GET_OFFER: '/api/premium/getOffer',
        GET_BY_ACCOUNT: '/api/premium/getByAccount',
        IS_EXISTS_FOR_ACCOUNT: '/api/premium/isExistsForAccount',
        CHECK_PAYMENT: '/api/premium/checkPayment',
        VERIFY: '/api/premium/verify',
        PAY: '/api/premium/pay',
        RENEW: '/api/premium/premium/renew',
        CANCEL: '/api/premium/premium/cancel',
        UPDATE_COMPANY_DETAILS: '/api/premium/updateCompanyDetails'
    }
}
