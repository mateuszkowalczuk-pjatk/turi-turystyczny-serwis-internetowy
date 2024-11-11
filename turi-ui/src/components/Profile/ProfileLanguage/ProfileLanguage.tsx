import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { setCookie } from '../../../utils/cookie'
import ProfileLabel from '../ProfileLabel'
import Checkbox from '../../Controls/Checkbox'
import styles from './ProfileLanguage.module.css'
import ProfileButton from '../ProfileButton'

const LANGUAGE = {
    PL: 'pl',
    EN: 'en'
}

const LABEL = {
    PL: 'Polski',
    EN: 'English (UK)'
}

const ProfileLanguage = () => {
    const { t, i18n } = useTranslation()
    const [selectedLanguage, setSelectedLanguage] = useState<string>(i18n.language)
    const [tempLanguage, setTempLanguage] = useState<string>(i18n.language)

    useEffect(() => {
        setSelectedLanguage(i18n.language)
        setTempLanguage(i18n.language)
    }, [i18n.language])

    const handleLanguageChange = (language: string) => {
        setTempLanguage(language)
    }

    const handleSave = () => {
        if (tempLanguage !== selectedLanguage) {
            i18n.changeLanguage(tempLanguage)
                .then(() => {
                    setCookie('language', tempLanguage)
                    setSelectedLanguage(tempLanguage)
                })
                .catch((error) => {
                    console.error('Error changing language:', error)
                })
        }
    }

    return (
        <div className={styles.language}>
            <ProfileLabel text={t('profile.language')} />
            <Checkbox
                checked={tempLanguage === LANGUAGE.PL}
                onChange={() => handleLanguageChange(LANGUAGE.PL)}
                text={LABEL.PL}
            />
            <Checkbox
                checked={tempLanguage === LANGUAGE.EN}
                onChange={() => handleLanguageChange(LANGUAGE.EN)}
                text={LABEL.EN}
            />
            <ProfileButton handleSave={handleSave} />
        </div>
    )
}

export default ProfileLanguage
