import { useTranslation } from 'react-i18next'
import FooterLanguage from '../FooterLanguage'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import styles from './AuthFooter.module.css'

const AuthFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.footer}>
            <FooterLanguage />
            <TextRegular text={t('footer.auth-description')} />
            <FooterCopyright />
        </div>
    )
}

export default AuthFooter
