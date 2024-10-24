import { getCookie } from '../utils/cookie'
import { initReactI18next } from 'react-i18next'
import i18n from 'i18next'
import plTranslation from '../locales/pl/translation.json'
import enTranslation from '../locales/en/translation.json'

const language = getCookie('language') || 'pl'

i18n.use(initReactI18next).init({
    resources: {
        pl: { translation: plTranslation },
        en: { translation: enTranslation }
    },
    lng: language,
    fallbackLng: 'en'
})
