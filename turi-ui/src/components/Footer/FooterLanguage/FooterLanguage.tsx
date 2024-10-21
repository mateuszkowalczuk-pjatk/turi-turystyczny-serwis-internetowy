import { useTranslation } from 'react-i18next'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './FooterLanguage.module.css'

const LANGUAGE = {
    PL: 'pl',
    EN: 'en',
}

const LABEL = {
    PL: 'Polski',
    EN: 'English (UK)',
    DIVIDE: ' | ',
    ENTER: "Enter"
}

const FooterLanguage = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (language: string) => {
        i18n.changeLanguage(language)
            .catch(error => { console.error(error) })
    }

    return (
        <div className={styles.language}>
            <span
                role="button"
                tabIndex={0}
                className={i18n.language === LANGUAGE.PL ? styles.active : ''}
                onClick={() => changeLanguage(LANGUAGE.PL)}
                onKeyDown={(e) => e.key === LABEL.ENTER && changeLanguage(LANGUAGE.PL)}
            >
                { LABEL.PL }
            </span>
            <TextRegular
                text={ LABEL.DIVIDE }
            />
            <span
                role="button"
                tabIndex={0}
                className={i18n.language === LANGUAGE.EN ? styles.active : ''}
                onClick={() => changeLanguage(LANGUAGE.EN)}
                onKeyDown={(e) => e.key === LABEL.ENTER && changeLanguage(LANGUAGE.EN)}
            >
                { LABEL.EN }
            </span>
        </div>
    )
}

export default FooterLanguage