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
        GET_EMAIL_BY_ID: '/api/user/getEmailById',
        IS_USERNAME_EXISTS: '/api/user/isUsernameExists',
        IS_EMAIL_EXISTS: '/api/user/isEmailExists',
        SEND_RESET_PASSWORD_CODE: '/api/user/sendResetPasswordCode',
        RESET_PASSWORD: '/api/user/resetPassword',
        CHANGE_USERNAME: '/api/user/changeUsername',
        CHANGE_EMAIL: '/api/user/changeEmail',
        CHANGE_PASSWORD: '/api/user/changePassword'
    },
    ACCOUNT: {
        GET: '/api/account/get',
        GET_BY_ID: '/api/account/getById',
        IS_ADDRESS_EXISTS: '/api/account/isAddressExists',
        IS_PHONE_NUMBER_EXISTS: '/api/account/isPhoneNumberExists',
        IS_PREMIUM: '/api/account/isPremium',
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
        GET_ACCOUNT_ID: '/api/premium/getAccountId',
        IS_EXISTS_FOR_ACCOUNT: '/api/premium/isExistsForAccount',
        CHECK_PAYMENT: '/api/premium/checkPayment',
        VERIFY: '/api/premium/verify',
        PAY: '/api/premium/pay',
        RENEW: '/api/premium/premium/renew',
        CANCEL: '/api/premium/premium/cancel',
        UPDATE_COMPANY_DETAILS: '/api/premium/updateCompanyDetails'
    },
    TOURISTIC_PLACE: {
        GET_BY_PREMIUM_ID: '/api/touristicplace/getByPremiumId',
        IS_ADDRESS_EXISTS: '/api/touristicplace/isAddressExists',
        GET_ALL_GUARANTEED_SERVICES: '/api/touristicplace/getAllGuaranteedServices',
        CREATE: '/api/touristicplace/create',
        CREATE_GUARANTEED_SERVICE: '/api/touristicplace/createGuaranteedService',
        UPDATE_DETAILS: '/api/touristicplace/updateDetails',
        DELETE_GUARANTEED_SERVICE: '/api/touristicplace/deleteGuaranteedService'
    },
    STAY: {
        GET_ALL_BY_TOURISTIC_PLACE_ID: '/api/stay/getAllByTouristicPlaceId',
        GET_BY_ID: '/api/stay/getById',
        CREATE: '/api/stay/create',
        CREATE_STAY_INFORMATION: '/api/stay/createStayInformation',
        UPDATE: '/api/stay/update',
        DELETE: '/api/stay/delete',
        DELETE_STAY_INFORMATION: '/api/stay/deleteStayInformation'
    },
    ATTRACTION: {
        GET_ALL_BY_TOURISTIC_PLACE_ID: '/api/attraction/getAllByTouristicPlaceId',
        GET_BY_ID: '/api/attraction/getById',
        CREATE: '/api/attraction/create',
        UPDATE: '/api/attraction/update',
        DELETE: '/api/attraction/delete'
    },
    IMAGE: {
        GET_BY_ACCOUNT: '/api/image/getByAccount',
        GET_BY_ACCOUNT_ID: '/api/image/getByAccountId',
        GET_ALL_BY_TOURISTIC_PLACE_ID: '/api/image/getAllByTouristicPlaceId',
        GET_ALL_BY_STAY_ID: '/api/image/getAllByStayId',
        GET_ALL_BY_ATTRACTION_ID: '/api/image/getAllByAttractionId',
        UPLOAD: '/api/image/upload',
        DELETE_BY_ID: '/api/image/deleteById'
    },
    OFFER: {
        SEARCH: '/api/offer/search',
        AUTOCOMPLETE: '/api/offer/autocomplete',
        GET_ALL_FAVOURITE_BY_ACCOUNT: '/api/offer/getAllFavouriteByAccount',
        IS_FAVOURITE_FOR_ACCOUNT: '/api/offer/isFavouriteForAccount',
        ADD_FAVOURITE_FOR_ACCOUNT: '/api/offer/addFavouriteForAccount',
        DELETE_FAVOURITE_FOR_ACCOUNT: '/api/offer/deleteFavouriteForAccount'
    }
}
