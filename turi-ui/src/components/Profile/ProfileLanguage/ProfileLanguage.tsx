import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { setCookie } from '../../../utils/cookie'
import ProfileLabel from '../ProfileLabel'
import Checkbox from '../../Shared/Controls/Checkbox'
import ProfileButton from '../ProfileButton'
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
    const [tempLanguage, setTempLanguage] = useState<string>(i18n.language)

    useEffect(() => {
        setSelectedLanguage(i18n.language)
        setTempLanguage(i18n.language)
    }, [i18n.language])

    const handleSave = () => {
        if (tempLanguage !== selectedLanguage) {
            i18n.changeLanguage(tempLanguage)
                .then(() => {
                    setCookie('language', tempLanguage)
                    setSelectedLanguage(tempLanguage)
                })
                .catch((error) => error)
        }
    }

    return (
        <div className={styles.language}>
            <ProfileLabel text={t('profile.language')} />
            <Checkbox
                checked={tempLanguage === LANGUAGE.PL}
                onChange={() => setTempLanguage(LANGUAGE.PL)}
                text={LABEL.PL}
            />
            <Checkbox
                checked={tempLanguage === LANGUAGE.EN}
                onChange={() => setTempLanguage(LANGUAGE.EN)}
                text={LABEL.EN}
            />
            <ProfileButton handleSave={handleSave} />
        </div>
    )
}

export default ProfileLanguage
