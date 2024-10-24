import Cookies from 'js-cookie'

export const getCookie = (name: string): string | undefined => {
    return Cookies.get(name)
}

export const setCookie = (name: string, value: string, options?: Cookies.CookieAttributes) => {
    Cookies.set(name, value, { expires: 365, secure: true, sameSite: 'strict', ...options })
}
