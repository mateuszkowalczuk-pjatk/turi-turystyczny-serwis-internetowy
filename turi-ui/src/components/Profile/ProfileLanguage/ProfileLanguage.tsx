import { useState, useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import ProfileLabel from '../ProfileLabel'
import Checkbox from '../../Controls/Checkbox'
import styles from './ProfileLanguage.module.css'

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

    useEffect(() => {
        setSelectedLanguage(i18n.language)
    }, [i18n.language])

    const changeLanguage = (language: string) => {
        i18n.changeLanguage(language)
            .then(() => setSelectedLanguage(language))
            .catch((error) => {
                console.error(error)
            })
    }

    return (
        <div className={styles.language}>
            <ProfileLabel text={t('profile.language')} />
            <Checkbox
                checked={selectedLanguage === LANGUAGE.PL}
                onChange={() => changeLanguage(LANGUAGE.PL)}
                text={LABEL.PL}
            />
            <Checkbox
                checked={selectedLanguage === LANGUAGE.EN}
                onChange={() => changeLanguage(LANGUAGE.EN)}
                text={LABEL.EN}
            />
        </div>
    )
}

export default ProfileLanguage
