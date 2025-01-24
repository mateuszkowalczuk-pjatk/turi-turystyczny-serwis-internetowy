import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterLanguage from '../FooterLanguage'
import FooterCopyright from '../FooterCopyright'
import styles from './AuthFooter.module.css'

const AuthFooter = () => {
    const { t } = useHooks()

    return (
        <div className={styles.footer}>
            <FooterLanguage />
            <TextRegular text={t('footer.auth-description')} />
            <FooterCopyright />
        </div>
    )
}

export default AuthFooter
